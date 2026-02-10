package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.core.Action
import com.github.seepick.derbauer2.game.core.ActionBus
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.execTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger

// TODO enforce that each resource only occurs once, by changing List to Map with Resource as key
data class ResourcesTradedAction(val requests: List<TradeRequest>) : Action {
    companion object // for extensions
}

class TradingService(
    private val user: User,
    private val actionBus: ActionBus,
) {

    private val log = logger {}

    fun trade(requests: List<TradeRequest>): TxResult {
        log.info { "${Emoji.`trade 💸`} trading for: $requests" }
        return user.execTx(requests.map { it.toTxOwnable() })
            .ifIsSuccess {
                actionBus.dispatch(ResourcesTradedAction(requests))
            }
    }
}

fun TradingService.trade(requestsX: TradeRequest, vararg requestsXS: TradeRequest) =
    trade(listOf(requestsX, *requestsXS))

private fun TradeRequest.toTxOwnable() = TxOwnable(
    ownableClass = resourceClass,
    amount = amount,
    operation = operation.asTxOperation
)

private val TradeOperation.asTxOperation
    get() = when (this) {
        TradeOperation.Buy -> TxOperation.INCREASE
        TradeOperation.Sell -> TxOperation.DECREASE
    }
