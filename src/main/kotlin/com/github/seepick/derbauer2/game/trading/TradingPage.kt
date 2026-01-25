package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.logic.Food
import com.github.seepick.derbauer2.game.logic.Gold
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.trading.TradeOperation.BUY
import com.github.seepick.derbauer2.game.trading.TradeOperation.SELL
import com.github.seepick.derbauer2.game.view.Back
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Prompt
import com.github.seepick.derbauer2.textengine.SelectOption
import com.github.seepick.derbauer2.textengine.Textmap

class TradingPage(
    private val trader: Trader,
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
) : Page {

    private val back = Back {
        currentPage.page = HomePage::class
    }
    private val prompt = Prompt.Select(
        title = "Ay, what do you wanna have mate?",
        listOf(
            SelectOption({ "Buy 1 food for 10 gold" }) {
                onTradeResult(trader.handle(
                    TradeRequest(Food::class, BUY, 1.units),
                    TradeRequest(Gold::class, SELL, 10.units),
                ))
            },
            SelectOption({ "Sell 1 food for 6 gold" }) {
                onTradeResult(trader.handle(
                    TradeRequest(Food::class, SELL, 1.units),
                    TradeRequest(Gold::class, BUY, 6.units),
                ))
            },
        )
    )
    private fun onTradeResult(result: TradeResult) {
        // TODO handle result (beep on failure, etc)
        println("trade result: $result")
    }

    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, prompt.inputIndicator, listOf(back.option)) {
            textmap.printLine("trading ...")
            prompt.render(textmap)
        }
    }

    override fun onKeyPressed(key: KeyPressed): Boolean =
        listOf(back, prompt).any { it.onKeyPressed(key) }
}
