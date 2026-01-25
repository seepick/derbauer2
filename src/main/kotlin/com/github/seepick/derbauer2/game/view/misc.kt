package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.viewer.KeyListener
import com.github.seepick.derbauer2.viewer.KeyPressed

class Back(
    description: String = "Back",
    private val onExecute: () -> Unit,
) : KeyListener {
    companion object {
        val key = KeyPressed.Command.Enter
    }
    val option = MetaOption(key, description)
    override fun onKeyPressed(key: KeyPressed): Boolean {
        if(key == Back.key) {
            onExecute()
            return true
        }
        return false
    }
}
