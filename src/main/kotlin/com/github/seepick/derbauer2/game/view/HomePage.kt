package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.engine.CurrentPage
import com.github.seepick.derbauer2.engine.KeyPressed
import com.github.seepick.derbauer2.engine.Page
import com.github.seepick.derbauer2.engine.Prompt
import com.github.seepick.derbauer2.engine.SelectOption
import com.github.seepick.derbauer2.engine.Textmap

class HomePage(
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
) : Page {

    private val selectStart = SelectOption("Buildings") {
        currentPage.page = BuildingsPage::class
    }
    private val prompt = Prompt.Select(title = "What shall we do next?", listOf(selectStart))

    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, footer = prompt.footer) {
            textmap.printLine("You are home... 🏠")// FIXME emoji v-space issue!
            textmap.printEmptyLine()
            prompt.render(textmap)
        }
    }

    override fun onKeyPressed(e: KeyPressed) = prompt.onKeyPressed(e)
}
