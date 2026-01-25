package com.github.seepick.derbauer2.viewer

import io.github.oshai.kotlinlogging.KotlinLogging

data class SelectOption(
    val label: () -> String,
    val onSelected: () -> Unit,
) {
    constructor(label: String, onSelected: () -> Unit) : this({ label }, onSelected)
}

sealed class PrintChar(val char: Char) {
    sealed class Numeric(val int: Int) : PrintChar(int.toString().first()) {
        object Zero : Numeric(0)
        object One : Numeric(1)
        object Two : Numeric(2)
    }

    override fun equals(other: Any?) = other is PrintChar && other.char == char
    override fun hashCode() = char.hashCode()
}

sealed interface KeyPressed {
    data class Symbol(val char: PrintChar) : KeyPressed
    sealed class Command(val key: String) : KeyPressed {
        object Enter : Command("ENTER")
        object Escape : Command("ESCAPE")
        object Space : Command("SPACE")
    }

    companion object {}
}

val KeyPressed.Companion.one get() = KeyPressed.Symbol(PrintChar.Numeric.One)

sealed interface Prompt : KeyListener {

    val inputIndicator: String
    fun render(textmap: Textmap)

    class Select(
        val title: String,
        val options: List<SelectOption>,
    ) : Prompt {
        private val log = KotlinLogging.logger {}

        init {
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
            } else false

        override fun render(textmap: Textmap) {
            textmap.printLine(title)
            textmap.printEmptyLine()
            options.mapIndexed { idx, opt ->
                // TODO support arrow up/down selection
                textmap.printLine("[${idx + 1}] ${opt.label()}")
            }
        }
    }
}
