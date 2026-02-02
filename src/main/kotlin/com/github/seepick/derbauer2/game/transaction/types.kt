package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.WarningType
import com.github.seepick.derbauer2.game.transaction.TxResult.Fail
import com.github.seepick.derbauer2.game.transaction.TxResult.Success
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

enum class TxType(val ref: TxTypeRef) {
    OWNABLE(TxTypeRef.Ownable);
}

/** Indirection via enum to centralize type-cast here and make it exhaustive everywhere else. */
sealed interface TxTypeRef {
    object Ownable : TxTypeRef {
        fun <R> casted(tx: Tx, code: (TxOwnable) -> R) = code(tx as TxOwnable)
    }
}

interface Tx {
    val type: TxType
}

enum class TxOperation(val symbol: String) {
    INCREASE("+"),
    DECREASE("-");
}

fun List<TxResult>.merge(): TxResult {
    if (isEmpty()) return Success
    val fails = filterIsInstance<Fail>()
    return if (fails.isEmpty()) {
        Success
    } else {
        Fail.of(fails)
    }
}

sealed interface TxResult {

    object Success : TxResult

    sealed interface Fail : TxResult {
        val warningType: WarningType
        val message: String

        class LandOveruse(override val message: String = "Using more land than available") : Fail {
            override val warningType = WarningType.LAND_OVERUSE
        }

        class InsufficientResources(additionalMessage: String? = null) : Fail {
            override val warningType = WarningType.INSUFFICIENT_RESOURCES
            override val message: String = "Insufficient resources" + (additionalMessage?.let { ": $it" } ?: "")
        }

        class CompoundFail(
            val fails: List<Fail>,
            override val message: String = "Multiple failures:\n${fails.joinToString("\n") { "* ${it.message}" }}"
        ) : Fail {
            override val warningType = WarningType.COMPOUND
        }

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
