package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.building.BuildingsPage
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.trading.TradingPage
import com.github.seepick.derbauer2.ifDo
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.KeyListener
import com.github.seepick.derbauer2.textengine.KeyPressed
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Prompt
import com.github.seepick.derbauer2.textengine.SelectOption
import com.github.seepick.derbauer2.textengine.Textmap

class HomePage(
    private val game: Game,
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
) : Page {

    private val nextTurn = NextTurn()
    private val prompt = Prompt.Select(
        title = "What shall we do next?", listOf(
            SelectOption("Trade") {
                currentPage.page = TradingPage::class
            },
            SelectOption("Build") {
                currentPage.page = BuildingsPage::class
            },
        )
    )

    override fun renderText(textmap: Textmap) {
        gameRenderer.render(
            textmap,
            promptIndicator = prompt.inputIndicator,
            listOf(MetaOption(KeyPressed.Command.Space, "Next Turn"))
        ) {
            textmap.printLine("You are home... 🏠")
            textmap.printEmptyLine()
            prompt.render(textmap)
        }
    }

    override fun onKeyPressed(key: KeyPressed) =
        listOf(prompt, nextTurn).any {
            it.onKeyPressed(key)
        }

    private inner class NextTurn : KeyListener {
        override fun onKeyPressed(key: KeyPressed) =
            ifDo(key == KeyPressed.Command.Space) {
                game.nextTurn()
                currentPage.page = ReportPage::class
            }
    }
}
