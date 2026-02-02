package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.Textmap
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed

class TechPage : Page {
    override fun render(textmap: Textmap) {
        textmap.line("technology page")
    }

    override fun onKeyPressed(key: KeyPressed): Boolean {
        return false
    }
}
