package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.TxBuild
import com.github.seepick.derbauer2.game.building._applyBuildTx
import com.github.seepick.derbauer2.game.building.validateBuildTx
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.TxResource
import com.github.seepick.derbauer2.game.resource._applyResourceTx
import com.github.seepick.derbauer2.game.resource.validateResourceTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger

private val log = logger {}

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
        is TxBuild -> validateBuildTx(tx)
        else -> error("Unknown Tx type: ${tx::class}")
    }

@Suppress("FunctionName")
private fun User._applyTx(tx: Tx) {
    when (tx) {
        is TxResource -> _applyResourceTx(tx)
        is TxBuild -> _applyBuildTx(tx)
        else -> error("Unknown Tx type: ${tx::class}")
    }
}
