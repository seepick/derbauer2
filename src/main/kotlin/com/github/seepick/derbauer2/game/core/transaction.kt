package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.transaction.Tx
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxType
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

private val log = logger {}

data class TxOwnable(
    override val ownableClass: KClass<out Ownable>,
    val operation: TxOperation,
    val amount: Z
) : Tx, OwnableReference {

    override val type = TxType.OWNABLE

    constructor(ownableClass: KClass<out Ownable>, amount: Zz) : this(
        ownableClass = ownableClass,
        operation = if (amount >= 0) TxOperation.INCREASE else TxOperation.DECREASE,
        amount = amount.toZAbs()
    )

    override fun toString() =
        "${this::class.simpleName}(${ownableClass.simpleNameEmojied} ${operation.symbol}$amount)"
}

@Suppress("FunctionName", "kotlin:S100")
fun User._applyOwnableTx(tx: TxOwnable) {
    val ownableEntity: Ownable = all.find(tx.ownableClass) as Ownable
    log.trace { "Applying: $tx for $ownableEntity" }
    when (tx.operation) {
        TxOperation.INCREASE -> {
            ownableEntity._setOwnedInternal += tx.amount
        }

        TxOperation.DECREASE -> {
            ownableEntity._setOwnedInternal -= tx.amount // during TX, will throw NegativeZException if negative
        }
    }
}
