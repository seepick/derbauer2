package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.engine.CurrentPage
import com.github.seepick.derbauer2.engine.KeyPressed
import com.github.seepick.derbauer2.engine.Page
import com.github.seepick.derbauer2.game.logic.User

class BuildingsPage(
    private val user: User,
    private val currentPage: CurrentPage,
    private val windowedRenderer: WindowedRenderer,

) : Page {
    private val continueKey = KeyPressed.Command.Enter
    override fun renderText() = windowedRenderer.render(listOf(FooterOption("ENTER", "Back"))) {
        "Buildings to come..."
    }

    override fun onKeyPressed(key: KeyPressed): Boolean {
        if(key == continueKey) {
            currentPage.page = HomePage::class
            return true
        }
        return false
    }
}