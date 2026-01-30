package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.TxResource
import com.github.seepick.derbauer2.game.resource.landOwned
import com.github.seepick.derbauer2.game.resource.totalLandUse
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxOwned
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.TxValidator
import com.github.seepick.derbauer2.game.transaction.execTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

private val log = logger {}

fun User.build(buildingClass: KClass<out Building>): TxResult =
    execTx(
        TxBuilding(buildingClass, 1.zz),
        TxResource(Gold::class, -building(buildingClass).costsGold),
    )

data class TxBuilding(
    override val ownableClass: KClass<out Building>,
    override val operation: TxOperation,
    override val amount: Z
) : TxOwned<Building>, BuildingReference {

    override val buildingClass = ownableClass

    constructor(targetClass: KClass<out Building>, amount: Zz) : this(
        ownableClass = targetClass,
        operation = if (amount >= 0) TxOperation.INCREASE else TxOperation.DECREASE,
        amount = amount.asZ()
    )
}

fun User.execTxBuilding(
    buildingClass: KClass<out Building>,
    amount: Zz,
) = execTx(
    TxBuilding(
        targetClass = buildingClass,
        amount = amount,
    )
)

fun User.execTxBuilding(
    buildingClass: KClass<out Building>,
    amount: Z,
) = execTx(
    TxBuilding(
        targetClass = buildingClass,
        amount = amount.asZz,
    )
)

object BuildingTxValidator : TxValidator {
    override fun validateTx(user: User) = with(user) {
        if (hasEntity(Land::class) && totalLandUse > landOwned) {
            TxResult.Fail.LandOveruse()
        } else {
            TxResult.Success
        }
    }
}

@Suppress("FunctionName", "kotlin:S100")
fun User._applyBuildTx(tx: TxBuilding) {
    val building = building(tx.buildingClass)
    log.trace { "Applying: $tx for $building" }
    when (tx.operation) {
        TxOperation.INCREASE -> {
            building._setOwnedInternal += tx.amount
        }

        TxOperation.DECREASE -> {
            building._setOwnedInternal -= tx.amount
        }
    }
}
