package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.TxResource
import com.github.seepick.derbauer2.game.resource.landAvailable
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxOwned
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.execTx
import kotlin.reflect.KClass

class TxBuild(
    override val targetClass: KClass<out Building>,
    override val operation: TxOperation,
    override val amount: Z
) : TxOwned<Building>, BuildingReference {
    constructor(targetClass: KClass<out Building>, amount: Zz) : this(
        targetClass = targetClass,
        operation = if (amount >= 0) TxOperation.INCREASE else TxOperation.DECREASE,
        amount = amount.asUnsigned()
    )

    override val buildingClass = targetClass
}

fun User.build(building: Building): TxResult =
    execTx(
        TxResource(
            targetClass = Gold::class,
            operation = TxOperation.DECREASE,
            amount = building.costsGold,
        ),
        TxBuild(
            targetClass = building::class,
            operation = TxOperation.INCREASE,
            amount = 1.z,
        )
    )

fun User.validateBuildTx(tx: TxBuild): TxResult {
    if (landAvailable < tx.building.landUse) {
        return TxResult.Fail.InsufficientResources("Not enough land")
    }
    return TxResult.Success
}

@Suppress("FunctionName")
fun User._applyBuildTx(tx: TxBuild) {
    @Suppress("DEPRECATION")
    building(tx.buildingClass)._setOwnedOnlyByTx += 1
}
