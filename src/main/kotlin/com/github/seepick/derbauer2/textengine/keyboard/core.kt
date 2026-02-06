package com.github.seepick.derbauer2.textengine.keyboard

fun interface KeyListener {
    fun onKeyPressed(key: KeyPressed): Boolean
}

sealed interface KeyPressed {
    data class Symbol(val char: PrintChar) : KeyPressed
    sealed class Command(val label: String) : KeyPressed {
        override fun toString() = "${this::class.simpleName}"

        object Enter : Command("ENTER")
        object Escape : Command("ESCAPE")
        object Space : Command("SPACE")
    }

    companion object {
        // via extension functions
    }
}

sealed class PrintChar(val char: Char) {
    override fun toString() = "${this::class.simpleName}($char)"
    @Suppress("MagicNumber")
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
        companion object {
            fun fromInt(int: Int): Numeric = when (int) {
                0 -> Zero
                1 -> One
                2 -> Two
                3 -> Three
                4 -> Four
                5 -> Five
                6 -> Six
                7 -> Seven
                8 -> Eight
                9 -> Nine
                else -> error("Not a single digit: $int")
            }
        }
    }

    override fun equals(other: Any?) = other is PrintChar && other.char == char
    override fun hashCode() = char.hashCode()
}
