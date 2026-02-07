package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.Texts
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.hasFeature
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.trading.TradeOperation.Buy
import com.github.seepick.derbauer2.game.trading.TradeOperation.Sell
import com.github.seepick.derbauer2.game.view.BackButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.game.view.TxResultHandler
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.OptionLabel
import com.github.seepick.derbauer2.textengine.prompt.Options
import com.github.seepick.derbauer2.textengine.prompt.PromptProvider
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt
import kotlin.reflect.KClass

class TradingPage(
    currentPage: CurrentPage,
    gameRenderer: GameRenderer,
    tradePromptBuilder: TradePromptBuilder,
) : PromptGamePage(
    buttons = listOf(BackButton {
        currentPage.pageClass = HomePage::class
    }),
    gameRenderer = gameRenderer,
    promptBuilder = tradePromptBuilder,
    contentRenderer = { textmap ->
        textmap.line(Texts.tradingPage)
    }
)

class TradePromptBuilder(
    private val user: User,
    private val resultHandler: TxResultHandler,
    private val tradingService: TradingService,
) : PromptProvider {
    override fun buildPrompt() = SelectPrompt(
        options = Options.Singled(buildList {
            add(buildTradeOption(Buy, Food::class to 1.z, Gold::class to Mechanics.buyFoodCostGold))
            add(buildTradeOption(Sell, Food::class to 1.z, Gold::class to Mechanics.sellFoodGainGold))
            if (user.hasFeature(TradeLandFeature::class)) {
                add(buildTradeOption(Buy, Land::class to 1.z, Gold::class to Mechanics.buyLandCostGold))
            }
        })
    )

    @Suppress("SpreadOperator")
    private fun buildTradeOption(
        operation: TradeOperation,
        target: Pair<KClass<out Resource>, Z>,
        vararg counters: Pair<KClass<out Resource>, Z>
    ) = SelectOption(
        label = OptionLabel.Single.Dynamic {
            val targetResource = user.findResource(target.first)
            "${operation.label} ${target.second} ${targetResource.emojiSpaceOrEmpty}${targetResource.labelSingular} for " +
                    counters.joinToString(" and ") { (counterResource, counterAmount) ->
                        val counterResource = user.findResource(counterResource)
                        "$counterAmount ${counterResource.emojiSpaceOrEmpty}${
                            counterResource.labelFor(counterAmount)
                        }"
                    }
        },
        onSelected = {
            resultHandler.handle(
                tradingService.trade(
                    TradeRequest(target.first, operation, target.second),
                    *counters.map { (costResource, costAmount) ->
                        TradeRequest(costResource, operation.inverse, costAmount)
                    }.toTypedArray()
                )
            )
        }
    )
}
