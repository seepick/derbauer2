package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.Building
import kotlin.reflect.KClass

class User {

    // var citizens = 10.units ... or is it a (special) resource?
    val resources = mutableListOf<Resource>()
    val buildings = mutableListOf<Building>()

    fun <R : Resource> resource(type: KClass<R>): R = resources.find(type) as R
    fun <B : Building> building(type: KClass<B>): B = buildings.find(type) as B

    private fun <T : Any> List<T>.find(type: KClass<out T>): T =
        filterIsInstance(type.java).firstOrNull() ?: error("Nothing found for type: ${type.simpleName} (available: ${map { it::class.simpleName }})")

}
