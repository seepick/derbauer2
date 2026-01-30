package com.github.seepick.derbauer2.textengine.prompt

import com.github.seepick.derbauer2.textengine.Textmap
import com.github.seepick.derbauer2.textengine.keyboard.KeyListener
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.keyboard.PrintChar
import io.github.oshai.kotlinlogging.KotlinLogging

data class SelectOption(
    val label: () -> String,
    val onSelected: () -> Unit,
) {
    constructor(label: String, onSelected: () -> Unit) : this({ label }, onSelected)

    override fun toString() = "SelectOption(label=${label()})"
}

fun interface PromptProvider {
    fun buildPrompt(): Prompt
}

// TODO make it NOT sealed...
sealed interface Prompt : KeyListener {

    val inputIndicator: String
    fun render(textmap: Textmap)

    class Select(
        val title: String,
        val options: List<SelectOption>,
    ) : Prompt {
        private val log = KotlinLogging.logger {}

        init {
            @Suppress("MagicNumber")
            require(options.size in 1..9)
        }

        override val inputIndicator = "1-${options.size}"

        override fun onKeyPressed(key: KeyPressed) =
            if (
                key is KeyPressed.Symbol &&
                key.char is PrintChar.Numeric &&
                key.char.char in '1'..options.size.toString().first()
            ) {
                val option = options[key.char.int - 1]
                log.debug { "Selected: $option" }
                option.onSelected()
                true
            } else {
                false
            }

        override fun render(textmap: Textmap) {
            textmap.line(title)
            textmap.emptyLine()
            options.mapIndexed { idx, opt ->
                textmap.line("[${idx + 1}] ${opt.label()}")
            }
        }
    }
}
