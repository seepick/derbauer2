package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.interaction.InteractionResult
import com.github.seepick.derbauer2.textengine.Beeper

/** Glue-code between view (UI/Beeper) and logic (InteractionResult). */
class InteractionResultHandler(
    private val beeper: Beeper,
) {
    fun handle(result: InteractionResult) {
        when (result) {
            InteractionResult.Success -> {} // do nothing
            is InteractionResult.Failure -> beeper.beep(result.reason)
        }
    }
}
