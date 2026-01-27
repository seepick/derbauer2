package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.errorNotFoundEntity
import com.github.seepick.derbauer2.game.logic.findOrNull
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
fun <B : Building> User.building(type: KClass<B>): B =
    (buildings.findOrNull(type) as? B) ?: errorNotFoundEntity(type, buildings)

val User.buildings get() = all.filterIsInstance<Building>()

context(user: User)
val BuildingReference.building get() = user.building(buildingClass)
