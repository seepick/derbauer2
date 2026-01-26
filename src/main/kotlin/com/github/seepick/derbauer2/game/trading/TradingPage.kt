package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.HomePage
import com.github.seepick.derbauer2.game.feature.FeatureDescriptor
import com.github.seepick.derbauer2.game.feature.hasFeature
import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.Z
import com.github.seepick.derbauer2.game.logic.z
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.trading.TradeOperation.Buy
import com.github.seepick.derbauer2.game.trading.TradeOperation.Sell
import com.github.seepick.derbauer2.game.view.BackButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.InteractionResultHandler
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Prompt
import com.github.seepick.derbauer2.textengine.SelectOption
import com.github.seepick.derbauer2.textengine.Textmap
import kotlin.reflect.KClass

class TradingPage(
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
    private val resultHandler: InteractionResultHandler,
    private val user: User,
) : Page {

    private val back = BackButton {
        currentPage.pageClass = HomePage::class
    }

    private val prompt
        get() = Prompt.Select(
            title = "What is it your greed desires?",
            options = buildList {
                add(setupTrade(Buy, Food::class, Gold::class to Mechanics.buyFoodCostGold.z))
                add(setupTrade(Sell, Food::class, Gold::class to Mechanics.sellFoodGainGold.z))
                if (user.hasFeature(FeatureDescriptor.TradeLand)) {
                    add(setupTrade(Buy, Land::class, Gold::class to Mechanics.buyLandCostGold.z))
                }
            }
        )

    private fun setupTrade(
        operation: TradeOperation,
        targetResourceClass: KClass<out Resource>,
        vararg counters: Pair<KClass<out Resource>, Z>
    ): SelectOption {
        val targetResource = user.resource(targetResourceClass)
        return SelectOption({
            "${operation.label} 1 ${targetResource.emojiWithSpaceSuffixOrEmpty}${targetResource.labelSingular} for " +
                    counters.joinToString(" and ") { (counterResource, counterAmount) ->
                        val counterResource = user.resource(counterResource)
                        "$counterAmount ${counterResource.emojiWithSpaceSuffixOrEmpty}${
                            counterResource.labelFor(
                                counterAmount
                            )
                        }"
                    }
        }) {
            resultHandler.handle(
                user.trade(
                    TradeRequest(targetResourceClass, operation, 1.z),
                    *counters.map { (costResource, costAmount) ->
                        TradeRequest(costResource, operation.inverse, costAmount)
                    }.toTypedArray()
                )
            )
        }
    }

    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, prompt.inputIndicator, listOf(back.option)) {
            textmap.line("The merchant is here to do business with you, my lord.")
            textmap.emptyLine()
            prompt.render(textmap)
        }
    }

    override fun onKeyPressed(key: KeyPressed): Boolean =
        listOf(back, prompt).any { it.onKeyPressed(key) }
}
