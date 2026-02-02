package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.TxOwned
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.landOwned
import com.github.seepick.derbauer2.game.resource.totalLandUse
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.TxValidator
import com.github.seepick.derbauer2.game.transaction.execTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

private val log = logger {}

fun User.build(buildingClass: KClass<out Building>): TxResult =
    execTx(
        TxOwned(buildingClass, 1.zz),
        TxOwned(Gold::class, -building(buildingClass).costsGold),
    )


object BuildingTxValidator : TxValidator {
    override fun validateTx(user: User) =
        with(user) {
            if (hasEntity(Land::class) && totalLandUse > landOwned) {
                TxResult.Fail.LandOveruse()
            } else {
                TxResult.Success
            }
        }
}
