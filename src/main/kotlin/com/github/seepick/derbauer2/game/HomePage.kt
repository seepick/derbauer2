package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.BuildingsPage
import com.github.seepick.derbauer2.game.trading.TradingPage
import com.github.seepick.derbauer2.game.turn.ReportPage
import com.github.seepick.derbauer2.game.turn.Turner
import com.github.seepick.derbauer2.game.view.ContinueButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Prompt
import com.github.seepick.derbauer2.textengine.SelectOption
import com.github.seepick.derbauer2.textengine.Textmap

class HomePage(
    private val turner: Turner,
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
) : Page {

    private val nextTurnButton = ContinueButton("Next Turn") {
        turner.collectAndExecuteNextTurnReport()
        currentPage.pageClass = ReportPage::class
    }

    private val prompt = Prompt.Select(
        title = "What shall we do next?", listOf(
            SelectOption("Trade") {
                currentPage.pageClass = TradingPage::class
            },
            SelectOption("Build") {
                currentPage.pageClass = BuildingsPage::class
            },
        )
    )

    override fun renderText(textmap: Textmap) {
        gameRenderer.render(
            textmap,
            promptIndicator = prompt.inputIndicator,
            metaOptions = listOf(nextTurnButton)
        ) {
            textmap.line("You are home... 🏠")
            textmap.emptyLine()
            prompt.render(textmap)
        }
    }

    override fun onKeyPressed(key: KeyPressed) =
        listOf(prompt, nextTurnButton).any {
            it.onKeyPressed(key)
        }
}