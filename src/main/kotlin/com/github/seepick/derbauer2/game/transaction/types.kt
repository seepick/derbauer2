package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.Ownable
import com.github.seepick.derbauer2.game.transaction.TxResult.Fail
import com.github.seepick.derbauer2.game.transaction.TxResult.Success
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.reflect.KClass

interface TxOwned<T : Ownable> : Tx {
    val targetClass: KClass<out T>
    val operation: TxOperation
    val amount: Z
}

interface Tx

enum class TxOperation {
    INCREASE,
    DECREASE,
}

sealed interface TxResult {

    object Success : TxResult

    sealed interface Fail : TxResult {
        val message: String

        data class InsufficientResources(override val message: String = "Insufficient resources") : Fail
    }
}

@OptIn(ExperimentalContracts::class)
fun TxResult.errorOnFail() {
    contract { returns() implies (this@errorOnFail is Success) }
    if (this is Fail) {
        error("Transaction failed: $message")
    }
}
