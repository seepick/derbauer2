package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.citizen
import com.github.seepick.derbauer2.game.transaction.TxValidator
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

class User(val txValidators: List<TxValidator>) : DeepCopyable<User> {

    private val log = logger {}

    var userTitle = UserTitle.initial
    var cityTitle = CityTitle.initial

    private val allMutable = mutableListOf<Entity>()
    val all = ListX(allMutable)

    fun <E : Entity> add(entity: E, disableCheck: Boolean = false) = entity.also {
        validate(entity, disableCheck)
        if (!disableCheck) { // don't log if doing deep copy (for TX simulation)
            log.info { "Enabling (${entity::class.simpleNameEmojied}): $entity" }
        }
        allMutable += entity
    }

    private fun <E : Entity> validate(entity: E, disableCheck: Boolean) {
        if (entity::class.simpleName == null) {
            throw UserEnableException(
                "Cannot add anonymous class (entity=$entity / entity.label='${entity.labelSingular}')"
            )
        }
        if (!disableCheck && entity is Ownable && entity.owned != 0.z) {
            throw UserEnableException(
                "Adding ownable ${entity.labelSingular} must be 0 but was: ${entity.owned}; " +
                        "(change it later via TX or: disable checks, but for tests only!)"
            )
        }
        if (all.any { it::class == entity::class }) {
            throw UserEnableException("Entity ${entity::class.simpleName} already exists!")
        }
    }

    override fun deepCopy(): User = User(txValidators).also { copy ->
        log.trace { "Creating deep copy." }
        copy.userTitle = userTitle
        copy.cityTitle = cityTitle
        all.forEach { entity ->
            // we are going to enable entities.owned > 0 (to bypass tx-validation, as we are just right in it ;)
            copy.add(entity.deepCopy(), disableCheck = true)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return allMutable == other.allMutable
    }

    override fun hashCode(): Int = allMutable.hashCode()

    override fun toString() = "User(all=${all.delegate.map { it.toString() }})"
}

fun User.hasEntities(vararg entityClasses: KClass<out Entity>) = entityClasses.all { hasEntity(it) }

fun User.hasEntity(entityClass: KClass<out Entity>) = all.findOrNull(entityClass) != null
inline fun <reified E : Entity> User.hasEntity() = hasEntity(E::class)

fun User.isGameOver() = hasEntity<Citizen>() && citizen == 0.z

class UserEnableException(message: String) : Exception(message)
