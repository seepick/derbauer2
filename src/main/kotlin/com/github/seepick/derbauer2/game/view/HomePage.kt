package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.building.BuildingsPage
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.trading.TradingPage
import com.github.seepick.derbauer2.ifDo
import com.github.seepick.derbauer2.viewer.CurrentPage
import com.github.seepick.derbauer2.viewer.KeyListener
import com.github.seepick.derbauer2.viewer.KeyPressed
import com.github.seepick.derbauer2.viewer.Page
import com.github.seepick.derbauer2.viewer.Prompt
import com.github.seepick.derbauer2.viewer.SelectOption
import com.github.seepick.derbauer2.viewer.Textmap

class HomePage(
    private val game: Game,
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
) : Page {

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

    override fun onKeyPressed(e: KeyPressed) =
        listOf(prompt, NextTurn(game)).any {
            it.onKeyPressed(e)
        }
}

private class NextTurn(private val game: Game) : KeyListener {
    override fun onKeyPressed(key: KeyPressed) =
        ifDo(key == KeyPressed.Command.Space) {
            game.nextTurn()
        }
}
