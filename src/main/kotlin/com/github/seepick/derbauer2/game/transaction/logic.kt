package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.common.NegativeZException
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core._applyOwnableTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

fun User.execTx(first: Tx, vararg other: Tx) = execTx(listOf(first, *other))

fun User.execTx(txs: List<Tx>): TxResult = if (txs.isEmpty()) {
    TxResult.Success
} else {
    when (val maybeSnapshot = copyAndApply(txs)) {
        is TxCopyResult.Ok -> validateAndExec(txs, maybeSnapshot.value)
        is TxCopyResult.Fail -> handleFail(maybeSnapshot)
    }
}

private fun handleFail(maybeSnapshot: TxCopyResult.Fail<User>) = when (maybeSnapshot) {
    is TxCopyResult.Fail.NegativeAmount -> {
        log.debug { "Snapshot-creation/TX-application: ${maybeSnapshot.exception}" }
        TxResult.Fail.InsufficientResources(
            "Transactions blocked due to negative value.${maybeSnapshot.failDetails?.let { " ($it)" } ?: ""}"
        )
    }
}

private fun User.copyAndApply(txs: List<Tx>): TxCopyResult<User> {
    val snapshot = deepCopy()
    var lastTx = txs.first()
    try {
        txs.forEach { tx ->
            lastTx = tx
            snapshot._applyTx(tx)
        }
    } catch (e: NegativeZException) {
        return TxCopyResult.Fail.NegativeAmount(e, lastTx)
    }
    return TxCopyResult.Ok(snapshot)
}

private operator fun TxCopyResult.Fail.NegativeAmount.Companion.invoke(exception: NegativeZException, failingTx: Tx) =
    TxCopyResult.Fail.NegativeAmount<User>(exception, failingTx.toString())

/** @param snapshot got the TXs already applied to it. */
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
    when (val ref = tx.type.ref) {
        is TxTypeRef.Ownable -> ref.casted(tx) { _applyOwnableTx(it) }
    }
}

private sealed interface TxCopyResult<T> {
    data class Ok<T>(
        val value: T,
    ) : TxCopyResult<T>

    sealed class Fail<T>(
        val failDetails: String?,
    ) : TxCopyResult<T> {

        class NegativeAmount<T>(
            val exception: NegativeZException,
            failDetails: String? = null,
        ) : Fail<T>(failDetails) {
            companion object
        }
    }
}
