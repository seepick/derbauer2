package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.engine.CurrentPage
import com.github.seepick.derbauer2.engine.KeyPressed
import com.github.seepick.derbauer2.engine.Page
import com.github.seepick.derbauer2.engine.Prompt
import com.github.seepick.derbauer2.engine.SelectOption
import com.github.seepick.derbauer2.engine.Textmap
import com.github.seepick.derbauer2.game.logic.Game

class HomePage(
    private val game: Game,
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
) : Page {

    private val prompt = Prompt.Select(title = "What shall we do next?", listOf(
        SelectOption("Buildings") {
            currentPage.page = BuildingsPage::class
        },
        SelectOption("Next Turn") {
            game.nextTurn()
        },
    ))

    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, promptIndicator = prompt.inputIndicator) {
            textmap.printLine("You are home... 🏠")// FIXME emoji v-space issue!
            textmap.printEmptyLine()
            prompt.render(textmap)
        }
    }

    override fun onKeyPressed(e: KeyPressed) = prompt.onKeyPressed(e)
}

