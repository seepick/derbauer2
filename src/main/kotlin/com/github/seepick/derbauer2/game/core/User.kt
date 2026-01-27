package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.ListX
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

interface DeepCopyable<T> {
    fun deepCopy(): T
}

class User : DeepCopyable<User> {
    private val log = logger {}
    private val _all = mutableListOf<Entity>()
    val all = ListX(_all)

    fun <E : Entity> enable(entity: E) = entity.also {
        // FIXME if add entity:ownable, then would need to wire through TX-infra to do validations
        // e.g. can add(Food(100.units)) if enough storage
        // add(House(100.units)) if enough land, etc.
        // check preconditions, like: if add OccupiesLand, but has no Land resource yet
        if (all.any { it::class == entity::class }) {
            error("Entity ${entity::class.simpleName} already exists!")
        }
        log.info { "Adding ${entity::class.simpleName} -- $entity" }
        _all += entity
    }

//    fun add(entity: Entity, vararg moreEntities: Entity) {
//        add(entity)
//        moreEntities.forEach { add(it) }
//    }

    fun hasEntity(entityClass: KClass<out Entity>) = all.findOrNull(entityClass) != null

    override fun deepCopy(): User =
        User().also { copy ->
            all.forEach { entity ->
                copy.enable(entity.deepCopy())
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return _all == other._all
    }

    override fun hashCode(): Int =
        _all.hashCode()

    override fun toString() = "User(all=$_all)"
}
