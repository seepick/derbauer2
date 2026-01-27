package com.github.seepick.derbauer2.textengine

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type

fun interface KeyListener {
    fun onKeyPressed(key: KeyPressed): Boolean
}

sealed interface KeyPressed {
    data class Symbol(val char: PrintChar) : KeyPressed
    sealed class Command(val label: String) : KeyPressed {
        object Enter : Command("ENTER")
        object Escape : Command("ESCAPE")
        object Space : Command("SPACE")
    }
    companion object {
        // via extension functions
    }
}

val KeyPressed.Companion.one get() = KeyPressed.Symbol(PrintChar.Numeric.One)

private val keyMap = buildMap {
    put(Key.Escape, KeyPressed.Command.Escape)
    put(Key.Escape, KeyPressed.Command.Escape)
    put(Key.Enter, KeyPressed.Command.Enter)
    put(Key.Spacebar, KeyPressed.Command.Space)

    put(Key.Zero, KeyPressed.Symbol(PrintChar.Numeric.Zero))
    put(Key.One, KeyPressed.Symbol(PrintChar.Numeric.One))
    put(Key.Two, KeyPressed.Symbol(PrintChar.Numeric.Two))
    put(Key.Three, KeyPressed.Symbol(PrintChar.Numeric.Three))
    put(Key.Four, KeyPressed.Symbol(PrintChar.Numeric.Four))
    put(Key.Five, KeyPressed.Symbol(PrintChar.Numeric.Five))
    put(Key.Six, KeyPressed.Symbol(PrintChar.Numeric.Six))
    put(Key.Seven, KeyPressed.Symbol(PrintChar.Numeric.Seven))
    put(Key.Eight, KeyPressed.Symbol(PrintChar.Numeric.Eight))
    put(Key.Nine, KeyPressed.Symbol(PrintChar.Numeric.Nine))
}

fun KeyEvent.toKeyPressed(): KeyPressed? =
    if (type == KeyEventType.KeyDown) {
        keyMap[key]
    } else {
        null
    }
