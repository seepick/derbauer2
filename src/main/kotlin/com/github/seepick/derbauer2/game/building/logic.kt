package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Action
import com.github.seepick.derbauer2.game.core.ActionBus
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.core.simpleNameEmojied
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.landOwned
import com.github.seepick.derbauer2.game.resource.totalLandUse
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.TxValidator
import com.github.seepick.derbauer2.game.transaction.execTx
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

class BuildingService(
    private val user: User,
    private val actionBus: ActionBus,
) {
    private val log = logger {}

    fun build(buildingClass: KClass<out Building>): TxResult {
        log.info { "Build ${buildingClass.simpleNameEmojied}" }
        return user.execTx(
            TxOwnable(buildingClass, 1.zz),
            TxOwnable(Gold::class, -user.findBuilding(buildingClass).costsGold),
        ).ifIsSuccess {
            actionBus.dispatch(BuildingBuiltAction(buildingClass))
        }
    }
}

data class BuildingBuiltAction(val buildingClass: KClass<out Building>) : Action {
    override fun toString() = "${this::class.simpleName}(building=${buildingClass.simpleNameEmojied})"
}

/** Do NOT make this a functional implementation as we need a concrete class reference. */
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
