package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelPlural
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxOwned
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.TxValidator
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.transaction.merge
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

private val log = logger {}

data class TxResource(
    override val ownableClass: KClass<out Resource>,
    override val operation: TxOperation,
    override val amount: Z,
) : TxOwned<Resource>, ResourceReference {
    override val resourceClass = ownableClass

    constructor(resource: Resource, amount: Zz) : this(resource::class, amount)
    constructor(targetClass: KClass<out Resource>, amount: Zz) : this(
        ownableClass = targetClass,
        operation = if (amount >= 0) TxOperation.INCREASE else TxOperation.DECREASE,
        amount = amount.asZ()
    )
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

object ResourceTxValidator : TxValidator {
    override fun validateTx(user: User) = with(user) {
        resources.filterIsInstance<StorableResource>().map { resource ->
            if (resource.owned > storageFor(resource)) {
                TxResult.Fail.InsufficientResources("Not enough storage for ${resource.emojiAndLabelPlural}")
            } else TxResult.Success
        }.merge()
    }
}

@Suppress("FunctionName", "kotlin:S100")
fun User._applyResourceTx(tx: TxResource) {
    val resource = resource(tx.resourceClass)
    log.trace { "Applying: $tx for $resource" }
    when (tx.operation) {
        TxOperation.INCREASE -> resource._setOwnedInternal += tx.amount
        TxOperation.DECREASE -> resource._setOwnedInternal -= tx.amount
    }
}
