package com.github.seepick.derbauer2.textengine

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

interface KeyListener {
    fun onKeyPressed(key: KeyPressed): Boolean
}

sealed interface KeyPressed {
    data class Symbol(val char: PrintChar) : KeyPressed
    sealed class Command(val label: String) : KeyPressed {
        object Enter : Command("ENTER")
        object Escape : Command("ESCAPE")
        object Space : Command("SPACE")
    }

    companion object {}
}

val KeyPressed.Companion.one get() = KeyPressed.Symbol(PrintChar.Numeric.One)

fun KeyEvent.toKeyPressed(): KeyPressed? =
    if (type == KeyEventType.KeyDown) {
        when (key) {
            Key.Escape -> KeyPressed.Command.Escape
            Key.Enter -> KeyPressed.Command.Enter
            Key.Spacebar -> KeyPressed.Command.Space
            Key.Zero -> KeyPressed.Symbol(PrintChar.Numeric.Zero)
            Key.One -> KeyPressed.Symbol(PrintChar.Numeric.One)
            Key.Two -> KeyPressed.Symbol(PrintChar.Numeric.Two)
            Key.Three -> KeyPressed.Symbol(PrintChar.Numeric.Three)
            Key.Four -> KeyPressed.Symbol(PrintChar.Numeric.Four)
            Key.Five -> KeyPressed.Symbol(PrintChar.Numeric.Five)
            Key.Six -> KeyPressed.Symbol(PrintChar.Numeric.Six)
            Key.Seven -> KeyPressed.Symbol(PrintChar.Numeric.Seven)
            Key.Eight -> KeyPressed.Symbol(PrintChar.Numeric.Eight)
            Key.Nine -> KeyPressed.Symbol(PrintChar.Numeric.Nine)
            else -> {
                log.debug { "Unhandled key: $key" }
                null
            }
        }
    } else {
        null
    }
