package com.github.seepick.derbauer2.textengine.prompt

import com.github.seepick.derbauer2.textengine.Renderer
import com.github.seepick.derbauer2.textengine.keyboard.KeyListener
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import com.github.seepick.derbauer2.textengine.textmap.multiLine

interface Prompt : KeyListener, Renderer {
    val inputIndicator: String
}

fun interface PromptProvider {
    fun buildPrompt(): Prompt
}

class EmptyPagePromptProvider(private val emptyMultiLineMessage: String) : Prompt {
    override val inputIndicator: String = KeyPressed.Command.Enter.label
    override fun render(textmap: Textmap) {
        textmap.multiLine(emptyMultiLineMessage)
    }

    override fun onKeyPressed(key: KeyPressed) = false
}
