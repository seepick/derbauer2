package com.github.seepick.derbauer2.game.common

import kotlin.reflect.KClass

// TODO only exact match, not subtype; check singleton

@Suppress("JavaDefaultMethodsNotOverriddenByDelegation")
class ListX<E : Any>(val delegate: List<E>) : List<E> by delegate, ListXOps<E> {
    inline fun <reified X : E> find(): X = find(X::class) as X
    inline fun <reified X : E> findOrNull(): X? = findOrNull(X::class) as X?
}

interface ListXOps<E : Any> : List<E> {
    fun findOrNull(klass: KClass<out E>): E? =
        filterIsInstance(klass.java).firstOrNull()

    fun find(entityClass: KClass<out E>): E =
        findOrNull(entityClass) ?: errorNotFoundEntity(entityClass, this)

    fun <V> letIfExists(type: KClass<out E>, letCode: (E) -> V): V? =
        findOrNull(type)?.let(letCode)

    fun alsoIfExists(klass: KClass<out E>, alsoCode: (E) -> Unit) {
        findOrNull(klass)?.also(alsoCode)
    }
}

fun errorNotFoundEntity(type: KClass<*>, options: List<Any>): Nothing {
    error("Nothing found for type: ${type.simpleName} (available: ${options.map { it::class.simpleName }})")
}
