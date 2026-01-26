@file:Suppress("FunctionName")

package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.textengine.KeyListener
import com.github.seepick.derbauer2.textengine.KeyPressed

fun BackButton(
    label: String = "Back",
    onExecute: () -> Unit
) = Button(
    key = KeyPressed.Command.Enter,
    label = label,
    onExecute = onExecute,
)

fun ContinueButton(
    label: String = "Continue",
    onExecute: () -> Unit
) = Button(
    key = KeyPressed.Command.Enter,
    label = label,
    onExecute = onExecute,
)

class Button(
    override val key: KeyPressed.Command,
    override val label: String,
    val option: MetaOption = MetaOptionImpl(key, label),
    private val onExecute: () -> Unit,
) : MetaOption by option, KeyListener {

    override fun onKeyPressed(key: KeyPressed): Boolean {
        if (this.key == key) {
            onExecute()
            return true
        }
        return false
    }
}
