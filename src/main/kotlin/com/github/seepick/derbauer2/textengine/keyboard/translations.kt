package com.github.seepick.derbauer2.textengine.keyboard

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type

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
