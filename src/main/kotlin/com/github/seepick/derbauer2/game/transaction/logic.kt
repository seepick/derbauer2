package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building._applyBuildTx
import com.github.seepick.derbauer2.game.building.validateBuildTx
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.resource._applyResourceTx
import com.github.seepick.derbauer2.game.resource.validateResourceTx
import com.github.seepick.derbauer2.game.transaction.Tx.TxResource
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

// FIXME no, this doesnt work if we add a lot of TXs at the same time, changing a complex system in such a way, that it will be a false-positive
// e.g. adjusting +food and its -storage at the same time... would need to run some kind of "simulation" first...?!
fun User.execTx(first: Tx, vararg other: Tx)=
    execTx(listOf(first, *other))

fun User.execTx(txs: List<Tx>): TxResult {
    log.debug { "Executing transactions: $txs" }
    val fails = txs.map(::validateTx).filterIsInstance<TxResult.Fail>()
    if (fails.isNotEmpty()) {
        return fails.first() // could aggregate messages if needed...
    }
    txs.forEach(::_applyTx)
    return TxResult.Success
}

private fun User.validateTx(tx: Tx): TxResult =
    when (tx) {
        is TxResource -> validateResourceTx(tx)
        is Tx.TxBuild -> validateBuildTx(tx)
    }

@Suppress("FunctionName")
private fun User._applyTx(tx: Tx) {
    when (tx) {
        is TxResource -> _applyResourceTx(tx)
        is Tx.TxBuild -> _applyBuildTx(tx)
    }
}
