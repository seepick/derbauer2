package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.textengine.audio.Beeper

/** Glue-code between view (UI/Beeper) and logic (InteractionResult). */
class InteractionResultHandler(
    private val beeper: Beeper,
) {
    fun handle(result: TxResult) {
        when (result) {
            TxResult.Success -> { /* no-op */
            }

            is TxResult.Fail -> beeper.beep(result.message)
        }
    }
}
