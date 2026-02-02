package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.transaction.TxValidator
import com.github.seepick.derbauer2.game.transaction.TxValidatorType
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.turn.ReportsWritable
import com.github.seepick.derbauer2.game.turn.TurnReport
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

class User(val txValidators: List<TxValidator> = TxValidatorType.all) : DeepCopyable<User> {

    private val log = logger {}

    var turn = 1
        private set

    var userTitle = UserTitle.initial
    var cityTitle = CityTitle.initial

    private var _reports = ReportsWritable()
    val reports: Reports = _reports

    private val _all = mutableListOf<Entity>()
    val all = ListX(_all)

    fun <E : Entity> enable(entity: E, disableCheck: Boolean = false) = entity.also {
        if (entity::class.simpleName == null) {
            throw UserEnableException(
                "Cannot enable anonymous class (entity=$entity / entity.label='${entity.labelSingular}')"
            )
        }
        log.debug { "Trying to enable '$entity' (disableCheck=$disableCheck)" }
        if (!disableCheck && entity is Ownable && entity.owned != 0.z) {
            throw UserEnableException("Enable must have 0 owned; change it later via TX: $entity")
        }
        if (all.any { it::class == entity::class }) {
            throw UserEnableException("Entity ${entity::class.simpleName} already exists!")
        }
        if (!disableCheck) { // don't log if doing deep copy (for TX simulation)
            log.info { "Enabling $entity" }
        }
        _all += entity
    }

    fun nextTurn(report: TurnReport) {
        turn++
        _reports.add(report)
    }

    override fun deepCopy(): User = User(txValidators).also { copy ->
        log.trace { "Creating deep copy." }
        copy.turn = turn
        copy.userTitle = userTitle
        copy.cityTitle = cityTitle
        copy._reports = _reports
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

    override fun hashCode(): Int = _all.hashCode()

    override fun toString() = "User($_all)"
}

fun User.hasEntity(entityClass: KClass<out Entity>) = all.findOrNull(entityClass) != null
inline fun <reified E : Entity> User.hasEntity() = hasEntity(E::class)

val User.gold get() = findResource(Gold::class).owned
val User.food get() = findResource(Food::class).owned
val User.citizens get() = findResource(Citizen::class).owned
val User.land get() = findResource(Land::class).owned

fun User.isGameOver() = hasEntity<Citizen>() && citizens == 0.z

class UserEnableException(message: String) : Exception(message)
