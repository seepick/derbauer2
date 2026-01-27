package com.github.seepick.derbauer2.game.logic

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

class User {
    private val log = logger {}
    private val _all = mutableListOf<Entity>()
    val all: List<Entity> get() = _all

    fun <E : Entity> add(entity: E) = entity.also {
        // FIXME if add entity:ownable, then would need to wire through TX-infra to do validations
        // e.g. can add(Food(100.units)) if enough storage
        // add(House(100.units)) if enough land, etc.
        // check preconditions, like: if add OccupiesLand, but has no Land resource yet
        if(all.filterIsInstance(entity::class.java).isNotEmpty()) {
//        if (all.any { it::class == entity::class }) {
            error("Entity ${entity::class.simpleName} already exists!")
        }
        log.info { "Adding ${entity::class.simpleName} -- $entity" }
        _all += entity
    }

    fun add(entity: Entity, vararg moreEntities: Entity) {
        add(entity)
        moreEntities.forEach { add(it) }
    }

    fun hasEntity(entityClass: KClass<out Entity>) = all.findOrNull(entityClass) != null

}

// TODO only exact match, not subtype; check singleton

fun <E : Entity> List<Entity>.findOrNull(klass: KClass<out E>): E? =
    filterIsInstance(klass.java).firstOrNull()

inline fun <reified E : Entity> List<Entity>.findOrNull(): E? =
    filterIsInstance<E>().firstOrNull()

fun <E : Entity> List<Entity>.find(entityClass: KClass<out E>): E =
    findOrNull(entityClass) ?: errorNotFoundEntity(entityClass, this)

inline fun <reified E : Entity> List<Entity>.find(): E =
    findOrNull<E>() ?: errorNotFoundEntity(E::class, this)

fun <E : Entity, V> List<Entity>.letIfExists(type: KClass<out E>, letCode: (E) -> V): V? =
    findOrNull(type)?.let(letCode)

 fun <E : Entity> List<Entity>.alsoIfExists(klass: KClass<out E>, alsoCode: (E) -> Unit) {
    findOrNull(klass)?.also(alsoCode)
}

fun errorNotFoundEntity(type: KClass<*>, options: List<Any>): Nothing {
    error("Nothing found for type: ${type.simpleName} (available: ${options.map { it::class.simpleName }})")
}
