package com.github.seepick.derbauer2.game.logic

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

class User {

    private val log = logger {}
    private val _all = mutableListOf<Entity>()
    val all: List<Entity> get() = _all

    fun <E : Entity> add(entity: E) = entity.also {
        if (all.any { it::class == entity::class }) {
            error("Entity ${entity::class.simpleName} already exists!")
        }
        log.info { "Adding ${entity::class.simpleName} -- $entity" }
        _all += entity
    }

    fun <E : Entity> List<E>.find(entityClass: KClass<out E>): E =
        findOrNull(entityClass) ?: errorNotFoundEntity(entityClass, this)

    fun <T : Any> List<T>.findOrNull(type: KClass<out T>): T? =
        filterIsInstance(type.java).firstOrNull() // FIXME only exact match, not subtype

    fun <E : Entity, V> letIfExists(type: KClass<E>, extractValue: (E) -> V): V? =
        (all.findOrNull(type) as E?)?.let(extractValue)

    fun errorNotFoundEntity(type: KClass<*>, options: List<Any>): Nothing {
        error("Nothing found for type: ${type.simpleName} (available: ${options.map { it::class.simpleName }})")
    }
}

