package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building._applyBuildTx
import com.github.seepick.derbauer2.game.building.validateBuildTx
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.resource._applyResourceTx
import com.github.seepick.derbauer2.game.resource.validateResourceTx
import com.github.seepick.derbauer2.game.transaction.TxRequest.TxResource

fun User.tx(first: TxRequest, vararg other: TxRequest): TxResult {
    val txs = arrayOf(first, *other)
    val fails = txs.map(::validateTx).filterIsInstance<TxResult.Fail>()
    if (fails.isNotEmpty()) {
        return fails.first() // could aggregate messages if needed...
    }
    txs.forEach(::applyTx)
    return TxResult.Success
}

private fun User.validateTx(tx: TxRequest): TxResult =
    when (tx) {
        is TxResource -> validateResourceTx(tx)
        is TxRequest.TxBuild -> validateBuildTx(tx)
    }

private fun User.applyTx(tx: TxRequest) {
    when (tx) {
        is TxResource -> _applyResourceTx(tx)
        is TxRequest.TxBuild -> _applyBuildTx(tx)
    }
}
