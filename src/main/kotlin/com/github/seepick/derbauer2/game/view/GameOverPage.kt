package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import com.github.seepick.derbauer2.textengine.textmap.emptyLine

class GameOverPage : Page {
    override fun render(textmap: Textmap) {
        textmap.line("Oh noes, all your citizens are dead...")
        textmap.emptyLine()
        textmap.line("There is nothing more to do than to take your own life.")
        textmap.line("Arrvivederci, suckers 🫡🖕🏻💀")
        textmap.emptyLine()
        textmap.asciiart(AsciiArt.gameOver)
    }

    override fun onKeyPressed(key: KeyPressed) = false // dead end
}
