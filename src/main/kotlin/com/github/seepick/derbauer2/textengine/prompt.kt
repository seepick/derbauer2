package com.github.seepick.derbauer2.textengine

import io.github.oshai.kotlinlogging.KotlinLogging

data class SelectOption(
    val label: () -> String,
    val onSelected: () -> Unit,
) {
    constructor(label: String, onSelected: () -> Unit) : this({ label }, onSelected)

    override fun toString() = "SelectOption(label=${label()})"
}

sealed class PrintChar(val char: Char) {
    sealed class Numeric(val int: Int) : PrintChar(int.toString().first()) {
        object Zero : Numeric(0)
        object One : Numeric(1)
        object Two : Numeric(2)
        object Three : Numeric(3)
        object Four : Numeric(4)
        object Five : Numeric(5)
        object Six : Numeric(6)
        object Seven : Numeric(7)
        object Eight : Numeric(8)
        object Nine : Numeric(9)
    }

    override fun equals(other: Any?) = other is PrintChar && other.char == char
    override fun hashCode() = char.hashCode()
}

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
                textmap.printLine("[${idx + 1}] ${opt.label()}")
            }
        }
    }
}
