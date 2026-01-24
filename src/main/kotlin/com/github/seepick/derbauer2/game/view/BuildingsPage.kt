package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.engine.CurrentPage
import com.github.seepick.derbauer2.engine.KeyPressed
import com.github.seepick.derbauer2.engine.Page
import com.github.seepick.derbauer2.game.logic.User

class BuildingsPage(
    private val user: User,
    private val currentPage: CurrentPage,
) : Page {
    override fun renderText(): String {
        return "You are in buildings...\n\n" +
                "Money: ${user.money}"
    }

    override fun onKeyPressed(key: KeyPressed): Boolean {
        if(key == KeyPressed.Escape) {
            currentPage.page = HomePage::class
            return true
        }
        return false
    }
}