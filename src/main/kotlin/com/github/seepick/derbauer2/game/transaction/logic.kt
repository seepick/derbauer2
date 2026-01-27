package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.TxBuilding
import com.github.seepick.derbauer2.game.building._applyBuildTx
import com.github.seepick.derbauer2.game.building.validateBuildTx
import com.github.seepick.derbauer2.game.common.NegativeZException
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.TxResource
import com.github.seepick.derbauer2.game.resource._applyResourceTx
import com.github.seepick.derbauer2.game.resource.validateResourceTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

fun User.execTx(first: Tx, vararg other: Tx) =
    execTx(listOf(first, *other))

sealed interface TxMaybe<T> {
    data class Ok<T>(val value: T) : TxMaybe<T>
    class Fail<T>(val fail: TxResult.Fail) : TxMaybe<T>
}

private fun User.copyAndApply(txs: List<Tx>): TxMaybe<User> {
    val snapshot = deepCopy()
    try {
        txs.forEach { snapshot._applyTx(it) }
    } catch (_: NegativeZException) {
        log.info { "Transactions blocked already during snapshot due to negative value." }
        return TxMaybe.Fail(TxResult.Fail.InsufficientResources())
    }
    return TxMaybe.Ok(snapshot)
}

fun User.execTx(txs: List<Tx>): TxResult {
    log.debug { "Executing transactions: $txs" }

    val maybeSnapshot = copyAndApply(txs)
    val snapshot: User
    when (maybeSnapshot) {
        is TxMaybe.Fail -> return maybeSnapshot.fail
        is TxMaybe.Ok -> snapshot = maybeSnapshot.value
    }

    val fails = listOf(
        snapshot.validateResourceTx(),
        snapshot.validateBuildTx(),
    ).filterIsInstance<TxResult.Fail>()

    if (fails.isNotEmpty()) {
        return TxResult.Fail.of(fails)
    }

    txs.forEach(::_applyTx)
    return TxResult.Success
}

@Suppress("FunctionName")
private fun User._applyTx(tx: Tx) {
    when (tx) {
        is TxResource -> _applyResourceTx(tx)
        is TxBuilding -> _applyBuildTx(tx)
        else -> error("Unknown Tx type: ${tx::class}")
    }
}
