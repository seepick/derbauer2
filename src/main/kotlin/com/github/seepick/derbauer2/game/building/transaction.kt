package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.landAvailable
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxRequest
import com.github.seepick.derbauer2.game.transaction.TxRequest.TxResource
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.tx

fun User.build(building: Building): TxResult {
    return tx(
        TxResource(
            resourceClass = Gold::class,
            operation = TxOperation.DECREASE,
            amount = building.costsGold,
        ),
        TxRequest.TxBuild(
            buildingClass = building::class,
        )
    )
}

fun User.validateBuildTx(tx: TxRequest.TxBuild): TxResult {
    if (landAvailable < tx.building.landUse) {
        return TxResult.Fail.InsufficientResources("Not enough land")
    }
    return TxResult.Success
}

@Suppress("FunctionName")
fun User._applyBuildTx(tx: TxRequest.TxBuild) {
    @Suppress("DEPRECATION")
    building(tx.buildingClass)._directSetOwned += 1
}
