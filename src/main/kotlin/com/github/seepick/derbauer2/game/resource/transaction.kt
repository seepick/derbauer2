package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabel
import com.github.seepick.derbauer2.game.transaction.Tx.TxResource
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.execTx
import kotlin.reflect.KClass

fun User.execTxResource(
    resourceClass: KClass<out Resource>,
    amount: Zz,
) = execTx(
    TxResource(
        resourceClass = resourceClass,
        amount = amount,
    )
)

fun User.execTxResource(
    resourceClass: KClass<out Resource>,
    amount: Z,
) = execTx(
    TxResource(
        resourceClass = resourceClass,
        amount = amount.asSigned,
    )
)

fun User.validateResourceTx(tx: TxResource): TxResult {
    val resource = resource(tx.resourceClass)
    when (tx.operation) {
        TxOperation.DECREASE -> {
            if (resource.owned < tx.amount) {
                return TxResult.Fail.InsufficientResources("Not enough ${resource.emojiAndLabel}")
            }
        }
        TxOperation.INCREASE -> {
            if (resource is StorableResource) {
                if (!isAbleToStore(resource, tx.amount)) {
                    return TxResult.Fail.InsufficientResources("Not enough storage for ${resource.emojiAndLabel}")
                }
            }
        }
    }
    return TxResult.Success
}

@Suppress("FunctionName", "DEPRECATION")
fun User._applyResourceTx(tx: TxResource) {
    val resource = resource(tx.resourceClass)
    when (tx.operation) {
        TxOperation.INCREASE -> resource._setOwnedOnlyByTx += tx.amount
        TxOperation.DECREASE -> resource._setOwnedOnlyByTx -= tx.amount
    }
}
