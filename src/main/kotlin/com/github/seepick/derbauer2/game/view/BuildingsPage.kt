package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.engine.CurrentPage
import com.github.seepick.derbauer2.engine.KeyPressed
import com.github.seepick.derbauer2.engine.Page
import com.github.seepick.derbauer2.engine.Textmap

class BuildingsPage(
    private val currentPage: CurrentPage,
    private val gameRenderer: GameRenderer,
) : Page {

    private val continueKey = KeyPressed.Command.Enter

    override fun renderText(textmap: Textmap) {
        gameRenderer.render(textmap, listOf(FooterOption(continueKey.key, "Continue"))) {
            textmap.printLine("Buildings to come...")
        }
    }

    override fun onKeyPressed(key: KeyPressed): Boolean {
        if(key == continueKey) {
            currentPage.page = HomePage::class
            return true
        }
        return false
    }
}