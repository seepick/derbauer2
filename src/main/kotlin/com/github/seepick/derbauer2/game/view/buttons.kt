@file:Suppress("FunctionName")

package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.textengine.keyboard.KeyListener
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed

fun BackButton(
    label: String = "Back",
    onExecute: () -> Unit,
) = Button(
    key = KeyPressed.Command.Enter,
    label = label,
    onExecute = onExecute,
)

fun ContinueButton(
    label: String = "Continue",
    onExecute: () -> Unit,
) = Button(
    key = KeyPressed.Command.Enter,
    label = label,
    onExecute = onExecute,
)

fun SecondaryBackButton(
    label: String = "",
    onExecute: () -> Unit,
) = Button(
    key = KeyPressed.Command.Escape,
    label = label,
    renderHint = false,
    onExecute = onExecute,
)

@Suppress("LongParameterList")
class Button(
    override val key: KeyPressed.Command,
    override val label: String,
    renderHint: Boolean = true,
    private val option: MetaOption = MetaOptionImpl(key, label, renderHint),
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
