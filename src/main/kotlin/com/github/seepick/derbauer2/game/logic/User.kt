package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.buildings
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.resources
import kotlin.reflect.KClass

class User {

    private val _all = mutableListOf<Entity>()
    val all: List<Entity> get() = _all



    fun addEntity(entity: Entity) {
        _all += entity
    }

    fun <R : Resource> resource(type: KClass<R>): R =
        resourceOrNull(type) ?: error("Resource not found for type: ${type.simpleName}")

    fun <R : Resource> resourceOrNull(type: KClass<R>): R? = resources.findOrNull(type) as R?

    fun <E : Entity, R> letIfExists(type: KClass<E>, default: R, letter: (E) -> R): R =
        (resources.findOrNull(type) as E?)?.let(letter) ?: default

    fun <B : Building> building(type: KClass<B>): B = buildings.find(type) as B

    private fun <T : Any> List<T>.find(type: KClass<out T>): T =
        findOrNull(type)
            ?: error("Nothing found for type: ${type.simpleName} (available: ${map { it::class.simpleName }})")

    private fun <T : Any> List<T>.findOrNull(type: KClass<out T>): T? =
        filterIsInstance(type.java).firstOrNull()

}
