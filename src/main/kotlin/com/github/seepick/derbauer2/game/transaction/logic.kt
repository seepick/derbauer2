package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.TxBuilding
import com.github.seepick.derbauer2.game.building._applyBuildTx
import com.github.seepick.derbauer2.game.common.NegativeZException
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.TxResource
import com.github.seepick.derbauer2.game.resource._applyResourceTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

fun User.execTx(first: Tx, vararg other: Tx) =
    execTx(listOf(first, *other))

fun User.execTx(txs: List<Tx>): TxResult {
    log.debug { "Executing transactions: $txs" }
    return when (val maybeSnapshot = copyAndApply(txs)) {
        is TxCopyResult.Ok -> {
            validateAndExec(txs, maybeSnapshot.value)
        }

        is TxCopyResult.Fail -> {
            when (maybeSnapshot) {
                is TxCopyResult.Fail.NegativeAmount -> {
                    log.debug(maybeSnapshot.e) { "Negative value during snapshot creation and TX application." }
                    TxResult.Fail.InsufficientResources("Transactions blocked due to negative value.")
                }
            }
        }
    }
}

private sealed interface TxCopyResult<T> {
    data class Ok<T>(val value: T) : TxCopyResult<T>
    sealed interface Fail<T> : TxCopyResult<T> {
        class NegativeAmount<T>(val e: NegativeZException) : Fail<T>
    }
}

private fun User.copyAndApply(txs: List<Tx>): TxCopyResult<User> {
    val snapshot = deepCopy()
    try {
        txs.forEach { snapshot._applyTx(it) }
    } catch (e: NegativeZException) {
        return TxCopyResult.Fail.NegativeAmount(e)
    }
    return TxCopyResult.Ok(snapshot)
}

fun interface TxValidator {
    fun validateTx(user: User): TxResult
}

private fun User.validateAndExec(txs: List<Tx>, snapshot: User): TxResult {
    val fails = txValidators.map { it.validateTx(snapshot) }.filterIsInstance<TxResult.Fail>()
    if (fails.isNotEmpty()) {
        return TxResult.Fail.of(fails)
    }
    txs.forEach(::_applyTx)
    return TxResult.Success
}

@Suppress("FunctionName", "kotlin:S100")
private fun User._applyTx(tx: Tx) {
    when (tx) {
        is TxResource -> _applyResourceTx(tx)
        is TxBuilding -> _applyBuildTx(tx)
        else -> error("Unknown Tx type: ${tx::class}")
    }
}
