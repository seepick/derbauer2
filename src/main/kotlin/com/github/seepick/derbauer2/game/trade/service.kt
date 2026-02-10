package com.github.seepick.derbauer2.game.trade

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.ActionBus
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.hasFeature
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.food
import com.github.seepick.derbauer2.game.resource.freeStorageFor
import com.github.seepick.derbauer2.game.resource.gold
import com.github.seepick.derbauer2.game.trade.TradeOperation.Buy
import com.github.seepick.derbauer2.game.trade.TradeOperation.Sell
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.execTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger

class TradeService(
    private val user: User,
    private val actionBus: ActionBus,
) {
    private val log = logger {}

    fun trade(option: TradeCompoundRequest) = if (option.target.amount == 0.z) {
        TxResult.Fail.TradingZeroBlocked
    } else {
        trade(option.toSingleRequests())
    }

    fun trade(requests: List<TradeSingleRequest>): TxResult {
        log.info { "${Emoji.`trade 💸`} trading for: $requests" }
        return user.execTx(requests.map { it.toTxOwnable() }).ifIsSuccess {
            actionBus.dispatch(ResourcesTradedAction(requests))
        }
    }

    fun buildOptions() = TradeCompoundRequests(buildList {
        addAll(buildFoodOptions())
        if (user.hasFeature(TradeLandFeature::class)) {
            addAll(buildLandOptions())
        }
    })

    private fun buildFoodOptions(): List<TradeCompoundRequest> {
        val foodTradeAmount = if (user.hasFeature(FoodMerchantFeature::class)) {
            Mechanics.tradeFoodAmountWithMerchant
        } else {
            Mechanics.tradeFoodAmountBasic
        }
        val foodBuyAmount = foodTradeAmount.coerceAtMost(user.freeStorageFor<Food>()) // can store
            .coerceAtMost(user.gold divFloor Mechanics.buyFoodCostGold) // can afford
        val foodSellAmount = foodTradeAmount.coerceAtMost(user.food)
        return listOf(
            TradeCompoundRequest(
                Buy,
                TradeAmount(Food::class, foodBuyAmount),
                listOf(TradeAmount(Gold::class, Mechanics.buyFoodCostGold * foodBuyAmount)),
            ), TradeCompoundRequest(
                Sell,
                TradeAmount(Food::class, foodSellAmount),
                listOf(TradeAmount(Gold::class, Mechanics.sellFoodGainGold * foodSellAmount))
            )
        )
    }

    private fun buildLandOptions(): List<TradeCompoundRequest> = buildList {
        add(
            TradeCompoundRequest(
                Buy, TradeAmount(Land::class, 1.z), listOf(TradeAmount(Gold::class, Mechanics.buyLandCostGold * 1))
            )
        )
    }
}

private fun TradeCompoundRequest.toSingleRequests(): List<TradeSingleRequest> = buildList {
    add(TradeSingleRequest(target.resoureClass, operation, target.amount))
    addAll(counters.map { counter ->
        TradeSingleRequest(counter.resoureClass, operation.inverse, counter.amount)
    })
}

private fun TradeSingleRequest.toTxOwnable() = TxOwnable(
    ownableClass = resourceClass, amount = amount, operation = operation.asTxOperation
)

private val TradeOperation.asTxOperation
    get() = when (this) {
        Buy -> TxOperation.INCREASE
        Sell -> TxOperation.DECREASE
    }
