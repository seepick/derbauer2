package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.logic.UserResult
import com.github.seepick.derbauer2.textengine.Beeper

/** Glue-code between view (UI/Beeper) and logic (InteractionResult). */
class InteractionResultHandler(
    private val beeper: Beeper,
) {
    fun handle(result: UserResult) {
        when (result) {
            UserResult.Success -> {} // do nothing
            is UserResult.Fail -> beeper.beep(result.reason)
        }
    }
}
