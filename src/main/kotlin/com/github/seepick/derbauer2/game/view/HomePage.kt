package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.engine.CurrentPage
import com.github.seepick.derbauer2.engine.KeyPressed
import com.github.seepick.derbauer2.engine.Page
import com.github.seepick.derbauer2.engine.Prompt
import com.github.seepick.derbauer2.engine.SelectOption

class HomePage(
    private val currentPage: CurrentPage,
    private val windowedRenderer: WindowedRenderer,
) : Page {

    private val selectStart = SelectOption("Buildings") {
        currentPage.page = BuildingsPage::class
    }
    private val prompt = Prompt.Select(title = "What shall we do next?", listOf(selectStart))

    override fun renderText() = windowedRenderer.render(footer = prompt.footer) {
        "You are home...\n\n" +
                prompt.renderText()
    }

    override fun onKeyPressed(e: KeyPressed) = prompt.onKeyPressed(e)
}
