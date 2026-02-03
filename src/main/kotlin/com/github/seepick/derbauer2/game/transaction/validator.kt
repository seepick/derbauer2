package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.core.User

fun interface TxValidator {
    fun validateTx(user: User): TxResult
}
