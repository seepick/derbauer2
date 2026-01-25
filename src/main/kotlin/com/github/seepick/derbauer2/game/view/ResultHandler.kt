package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.textengine.Beeper

interface Result {
    object Success : Result
    object Fail : Result
}

class ResultHandler(
    private val beeper: Beeper,
) {
    fun handle(result: Result) {
        when (result) {
            Result.Success -> {} // do nothing
            Result.Fail -> beeper.beep("Not enough resources to build.")
        }
        // TODO display message?
    }
}
