package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.common.z
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

class User : DeepCopyable<User> {
    private val log = logger {}

    init {
        log.info { "Creating new User instance." }
    }

    var designator = UserDesignator.default
    var cityDesignator = CityDesignator.default

    // var title: UserTitle; var cityTitle: CityTitle; etc could be here
    private val _all = mutableListOf<Entity>()
    val all = ListX(_all)

    fun <E : Entity> enable(entity: E, disableCheck: Boolean = false) = entity.also {
        if (!disableCheck && entity is Ownable) {
            require(entity.owned == 0.z) { "Enable must have 0 owned; change it later via TX: $entity" }
        }
        if (all.any { it::class == entity::class }) {
            error("Entity ${entity::class.simpleName} already exists!")
        }
        if (!disableCheck) {
            log.info { "Enabling $entity" }
        }
        _all += entity
    }

    fun hasEntity(entityClass: KClass<out Entity>) = all.findOrNull(entityClass) != null

    override fun deepCopy(): User =
        User().also { copy ->
            log.info { "Creating deep copy." }
            all.forEach { entity ->
                // we are going to enable entities.owned > 0 (to bypass tx-validation, as we are just right in it ;)
                copy.enable(entity.deepCopy(), disableCheck = true)
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return _all == other._all
    }

    override fun hashCode(): Int =
        _all.hashCode()

    override fun toString() = "User($_all)"
}
