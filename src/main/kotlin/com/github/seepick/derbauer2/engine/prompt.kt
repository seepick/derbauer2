package com.github.seepick.derbauer2.engine

import io.github.oshai.kotlinlogging.KotlinLogging

data class SelectOption(
    val label: String,
    val onSelected: () -> Unit,
)

sealed class PrintChar(val char: Char) {
    sealed class Numeric(val int: Int) : PrintChar(int.toString().first()) {
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
    }

    companion object {}
}

val KeyPressed.Companion.one get() = KeyPressed.Symbol(PrintChar.Numeric.One)

sealed interface Prompt : KeyListener {

    val footer: String
    fun render(textmap: Textmap)

    class Select(
        val title: String,
        val options: List<SelectOption>,
    ) : Prompt {
        private val log = KotlinLogging.logger {}
        init {
            require(options.size in 1..9)
        }

        override val footer = "1-${options.size}"

        override fun onKeyPressed(key: KeyPressed): Boolean {
            if (key is KeyPressed.Symbol &&
                key.char is PrintChar.Numeric &&
                key.char.char in '1'..options.size.toString().first()
            ) {
                val option = options[key.char.int - 1]
                log.debug { "Selected: $option" }
                option.onSelected()
                return true
            }
            return false
        }

        override fun render(textmap: Textmap) {
            textmap.printLine(title)
            textmap.printEmptyLine()
            options.mapIndexed { idx, opt ->
                // TODO support arrow up/down selection
                textmap.printLine("[${idx + 1}] ${opt.label}")
            }
        }
    }
}
