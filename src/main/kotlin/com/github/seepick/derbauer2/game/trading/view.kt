package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
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
import com.github.seepick.derbauer2.game.view.InteractionResultHandler
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.Options
import com.github.seepick.derbauer2.textengine.prompt.PromptProvider
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectOptionLabel
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
        textmap.line("The merchant is here to do business with you, my lord.")
    }
)

class TradePromptBuilder(
    private val user: User,
    private val resultHandler: InteractionResultHandler,
) : PromptProvider {
    override fun buildPrompt() = SelectPrompt(
        title = "What is it your greed desires?",
        options = Options.Singled(buildList {
            // or Gold(Mechanics.buyFoodCostGold.z)?
            add(buildTradeOption(Buy, Food::class, Gold::class to Mechanics.buyFoodCostGold.z))
            add(buildTradeOption(Sell, Food::class, Gold::class to Mechanics.sellFoodGainGold.z))
            if (user.hasFeature(TradeLandFeature::class)) {
                add(buildTradeOption(Buy, Land::class, Gold::class to Mechanics.buyLandCostGold.z))
            }
        })
    )

    @Suppress("SpreadOperator")
    private fun buildTradeOption(
        operation: TradeOperation,
        targetResourceClass: KClass<out Resource>,
        vararg counters: Pair<KClass<out Resource>, Z>
    ) = SelectOption(
        label = SelectOptionLabel.Single.Dynamic {
            val targetResource = user.findResource(targetResourceClass)
            "${operation.label} 1 ${targetResource.emojiSpaceOrEmpty}${targetResource.labelSingular} for " +
                    counters.joinToString(" and ") { (counterResource, counterAmount) ->
                        val counterResource = user.findResource(counterResource)
                        "$counterAmount ${counterResource.emojiSpaceOrEmpty}${
                            counterResource.labelFor(counterAmount)
                        }"
                    }
        },
        onSelected = {
            resultHandler.handle(
                user.trade(
                    TradeRequest(targetResourceClass, operation, 1.z),
                    *counters.map { (costResource, costAmount) ->
                        TradeRequest(costResource, operation.inverse, costAmount)
                    }.toTypedArray()
                )
            )
        }
    )
}
