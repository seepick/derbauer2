package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.textengine.Beeper

/** Glue-code between view (UI/Beeper) and logic (InteractionResult). */
class InteractionResultHandler(
    private val beeper: Beeper,
) {
    fun handle(result: TxResult) {
        when (result) {
            TxResult.Success -> {} // do nothing
            is TxResult.Fail -> beeper.beep(result.reason)
        }
    }
}
