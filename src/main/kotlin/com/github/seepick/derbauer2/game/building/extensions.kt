package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.transaction.TxOperation
import com.github.seepick.derbauer2.game.transaction.TxRequest
import com.github.seepick.derbauer2.game.transaction.TxRequest.TxResource
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.transaction
import kotlin.reflect.KClass

fun <B : Building> User.building(type: KClass<B>): B =
    (buildings.findOrNull(type) as B?) ?: errorNotFoundEntity(type, buildings)

val User.buildings get() = all.filterIsInstance<Building>()

context(user: User)
val BuildingReference.building get() = user.building(buildingClass)

fun User.build(building: Building): TxResult {
    return transaction(
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
