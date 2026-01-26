package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.User
import kotlin.reflect.KClass

fun <B : Building> User.building(type: KClass<B>): B =
    (buildings.findOrNull(type) as B?) ?: errorNotFoundEntity(type, buildings)

val User.buildings get() = all.filterIsInstance<Building>()

context(user: User)
val BuildingReference.building get() = user.building(buildingClass)
