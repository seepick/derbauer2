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

fun List<TxResult>.merge(): TxResult {
    if(isEmpty()) return Success
    val fails = filterIsInstance<Fail>()
    return if(fails.isEmpty()) {
        Success
    } else {
        Fail.of(fails)
    }
}

sealed interface TxResult {

    object Success : TxResult

    sealed interface Fail : TxResult {
        val message: String

        class LandOveruse(override val message: String = "Using more land than available") : Fail
        class InsufficientResources(override val message: String = "Insufficient resources") : Fail

        class CompoundFail(
            val fails: List<Fail>,
            override val message: String = "Multiple failures:\n${fails.joinToString("\n") { "* ${it.message}" }}"
        ) : Fail

        companion object {
            fun of(fails: List<Fail>): Fail {
                require(fails.isNotEmpty())
                return if (fails.size == 1) {
                    fails.first()
                } else {
                    CompoundFail(fails)
                }
            }
        }
    }
}

@OptIn(ExperimentalContracts::class)
fun TxResult.errorOnFail() {
    contract { returns() implies (this@errorOnFail is Success) }
    if (this is Fail) {
        error("Transaction failed: $message")
    }
}
