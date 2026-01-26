package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.landAvailable
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.transaction.Tx
import com.github.seepick.derbauer2.game.transaction.Tx.TxResource
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.execTx

fun User.build(building: Building): TxResult {
    return execTx(
        TxResource(
            resourceClass = Gold::class,
            operation = TxOperation.DECREASE,
            amount = building.costsGold,
        ),
        Tx.TxBuild(
            buildingClass = building::class,
        )
    )
}

fun User.validateBuildTx(tx: Tx.TxBuild): TxResult {
    if (landAvailable < tx.building.landUse) {
        return TxResult.Fail.InsufficientResources("Not enough land")
    }
    return TxResult.Success
}

@Suppress("FunctionName")
fun User._applyBuildTx(tx: Tx.TxBuild) {
    @Suppress("DEPRECATION")
    building(tx.buildingClass)._setOwnedOnlyByTx += 1
}
