package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.building
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.emojiAndLabel
import com.github.seepick.derbauer2.game.logic.landAvailable
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.transaction.TxRequest.TxResource

fun User.transaction(first: TxRequest, vararg other: TxRequest): TxResult {
    val txs = arrayOf(first, *other)
    val fails = txs.map(::validateTx).filterIsInstance<TxResult.Fail>()
    if (fails.isNotEmpty()) {
        return fails.first() // could aggregate messages if needed...
    }
    txs.forEach(::applyTx)
    return TxResult.Success
}

private fun User.validateTx(tx: TxRequest): TxResult {
    when (tx) {
        is TxResource -> {
            val resource = resource(tx.resourceClass)
            when (tx.operation) {
                TxOperation.DECREASE -> {
                    if (resource.owned < tx.amount) {
                        return TxResult.Fail.InsufficientResources("Not enough ${resource.emojiAndLabel}")
                    }
                }

                TxOperation.INCREASE -> {
                    // TODO check storage type
                }
            }
        }

        is TxRequest.TxBuild -> {
            if (landAvailable < tx.building.landUse) {
                return TxResult.Fail.InsufficientResources("Not enough land")
            }
        }
    }
    return TxResult.Success
}

private fun User.applyTx(tx: TxRequest) {
    when (tx) {
        is TxResource -> {
            val resource = resource(tx.resourceClass)
            when (tx.operation) {
                TxOperation.INCREASE -> resource.owned += tx.amount
                TxOperation.DECREASE -> resource.owned -= tx.amount
            }
        }

        is TxRequest.TxBuild -> {
            building(tx.buildingClass).owned += 1
        }
    }
}
