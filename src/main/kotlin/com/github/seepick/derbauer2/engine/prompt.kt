package com.github.seepick.derbauer2.engine

data class SelectOption(
    val label: String,
    val onSelected: () -> Unit,
)

sealed class PrintChar(val char: Char) {
    sealed class Numeric(val int: Int) : PrintChar(int.toString().first()) {
        object One : Numeric(1)
        object Two : Numeric(2)
    }
}

sealed interface KeyPressed {
    class Printable(val char: PrintChar) : KeyPressed
    object Enter : KeyPressed
    object Escape : KeyPressed

    companion object {}
}

val KeyPressed.Companion.one get() = KeyPressed.Printable(PrintChar.Numeric.One)

sealed interface Prompt : KeyListener {

    fun renderText(): String

    class Select(
        val title: String,
        val options: List<SelectOption>,
    ) : Prompt {
        init {
            require(options.size in 1..9)
        }

        override fun onKeyPressed(key: KeyPressed): Boolean {
            if (key is KeyPressed.Printable && key.char is PrintChar.Numeric && key.char.char in '1'..options.size.toString()
                    .first()
            ) {
                val option = options[key.char.int - 1]
                println("selected: $option")
                option.onSelected()
                return true
            }
            return false
        }

        override fun renderText(): String {
            return "$title\n\n${
                options.mapIndexed { idx, opt -> "[${idx + 1}] ${opt.label}" }.joinToString("\n")
            }\n\n>> ❓"
        }
    }
}
