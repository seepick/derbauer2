package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelPlural
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxOwned
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.execTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

private val log = logger {}

data class TxResource(
    override val targetClass: KClass<out Resource>,
    override val operation: TxOperation,
    override val amount: Z,
) : TxOwned<Resource>, ResourceReference {
    constructor(targetClass: KClass<out Resource>, amount: Zz) : this(
        targetClass = targetClass,
        operation = if (amount >= 0) TxOperation.INCREASE else TxOperation.DECREASE,
        amount = amount.asZ()
    )

    override val resourceClass = targetClass
}

fun User.execTxResource(
    resourceClass: KClass<out Resource>,
    amount: Zz,
) = execTx(
    TxResource(
        targetClass = resourceClass,
        amount = amount,
    )
)

fun User.execTxResource(
    resourceClass: KClass<out Resource>,
    amount: Z,
) = execTx(
    TxResource(
        targetClass = resourceClass,
        amount = amount.asZz,
    )
)

fun User.validateResourceTx(): TxResult {
    val fails = resources.filterIsInstance<StorableResource>().map { resource ->
        if (resource.owned > storageFor(resource)) {
            TxResult.Fail.InsufficientResources("Not enough storage for ${resource.emojiAndLabelPlural}")
        } else TxResult.Success
    }.filterIsInstance<TxResult.Fail>()
    if(fails.isNotEmpty()) {
        log.debug { "Failed: $fails" }
        return fails.first() // TODO merge
    }
//    val resource = resource(tx.resourceClass)
//    when (tx.operation) {
//        TxOperation.DECREASE -> {
//            if (resource.owned < tx.amount) {
//                return TxResult.Fail.InsufficientResources("Not enough ${resource.emojiAndLabelPlural}")
//            }
//        }
//
//        TxOperation.INCREASE -> {
//            if (resource is StorableResource) {
//                if (!isAbleToStore(resource, tx.amount)) {
//                    return TxResult.Fail.InsufficientResources("Not enough storage for ${resource.emojiAndLabelPlural}")
//                }
//            }
//        }
//    }
    return TxResult.Success
}

@Suppress("FunctionName", "DEPRECATION")
fun User._applyResourceTx(tx: TxResource) {
    val resource = resource(tx.resourceClass)
    when (tx.operation) {
        TxOperation.INCREASE -> resource._setOwnedInternal += tx.amount
        TxOperation.DECREASE -> resource._setOwnedInternal -= tx.amount
    }
}
