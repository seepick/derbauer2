package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

val User.buildings get() = ListX(all.filterIsInstance<Building>())

@Suppress("UNCHECKED_CAST")
fun <B : Building> User.building(type: KClass<B>): B = buildings.find(type) as B

context(user: User)
val BuildingReference.building get() = user.building(buildingClass)
