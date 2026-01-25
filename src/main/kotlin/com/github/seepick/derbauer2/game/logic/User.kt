package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.buildings
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.resources
import kotlin.reflect.KClass

class User {

    private val _all = mutableListOf<Entity>()
    val all: List<Entity> get() = _all

    fun <E : Entity> addEntity(entity: E) = entity.also {
        // TODO validate only one of a kind!
        _all += entity
    }

    fun <R : Resource> resource(type: KClass<R>): R =
        (resources.findOrNull(type) as R?) ?: errorNotFoundEntity(type, resources)

    fun <R : Resource> resourceOrNull(type: KClass<R>): R? = resources.findOrNull(type) as R?

    fun <E : Entity, R> letIfExists(type: KClass<E>, default: R, letter: (E) -> R): R =
        (resources.findOrNull(type) as E?)?.let(letter) ?: default

    fun <B : Building> building(type: KClass<B>): B =
        (buildings.findOrNull(type) as B?) ?: errorNotFoundEntity(type, buildings)


    private fun <T : Any> List<T>.find(type: KClass<out T>): T =
        findOrNull(type) ?: errorNotFoundEntity(type, this)

    private fun <T : Any> List<T>.findOrNull(type: KClass<out T>): T? =
        filterIsInstance(type.java).firstOrNull()

    private fun errorNotFoundEntity(type: KClass<*>, options: List<Any>): Nothing {
        error("Nothing found for type: ${type.simpleName} (available: ${options.map { it::class.simpleName }})")
    }
}
