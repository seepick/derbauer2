package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.engine.CurrentPage
import com.github.seepick.derbauer2.engine.KeyPressed
import com.github.seepick.derbauer2.engine.Page
import com.github.seepick.derbauer2.engine.Prompt
import com.github.seepick.derbauer2.engine.SelectOption

class HomePage(
    private val currentPage: CurrentPage,
) : Page {
    val selectStart = SelectOption("Buildings") {
        println("buildings selected")
        currentPage.page = BuildingsPage::class

    }
    private val prompt = Prompt.Select(title = "What shall we do next?",listOf(selectStart))


    override fun renderText(): String {
        return "You are home...\n\n" +
                prompt.renderText()
    }
    override fun onKeyPressed(e: KeyPressed) = prompt.onKeyPressed(e)

//    override fun onKeyEvent(e: KeyEvent): Boolean {
//        val consume = prompt.onKeyEvent(e)
//        if (e.key == Key.Spacebar && e.type == KeyEventType.KeyDown) {
//            println("space; increment user money by 10")
//            user.money += 10
//            return true
//        }
//        return false
//    }
}