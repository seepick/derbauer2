package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.core.Warning
import com.github.seepick.derbauer2.game.core.WarningBus
import com.github.seepick.derbauer2.game.transaction.TxResult

/** Glue-code between view (UI/Beeper) and logic (InteractionResult). */
class InteractionResultHandler(
    private val warningBus: WarningBus,
) {
    fun handle(result: TxResult) {
        when (result) {
            TxResult.Success -> { /* no-op */
            }

            is TxResult.Fail -> warningBus.dispatch(Warning("Transaction failed: ${result.message}"))
        }
    }
}
