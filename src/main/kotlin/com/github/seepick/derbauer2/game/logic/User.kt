package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.House
import kotlin.reflect.KClass

class User {

    // FIXME var citizens = 10.units
    val resources = mutableListOf<Resource>()
    val buildings = mutableListOf<Building>()

    fun <R : Resource> resource(type: KClass<R>): R = resources.find(type) as R
    fun <B : Building> building(type: KClass<B>): B = buildings.find(type) as B

    private fun <T : Any> List<T>.find(type: KClass<out T>): T =
        filterIsInstance(type.java).firstOrNull() ?: error("Nothing found for type: $type")

    init {
        resources += Gold(Mechanics.startingGold)
        resources += Food(Mechanics.startingFood)
        buildings += House()
        buildings += Farm()
    }
}
