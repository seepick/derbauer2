package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.transaction.TxValidator
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.turn.ReportsWritable
import com.github.seepick.derbauer2.game.turn.TurnReport
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

class User(
    val txValidators: List<TxValidator>,
) : DeepCopyable<User> {

    private val log = logger {}

    init {
        log.trace { "New User instance (txValidators.size=${txValidators.size})" }
    }

    var turn = 1
        private set
    var designator = UserDesignator.default
    var cityDesignator = CityDesignator.default
    private val _reports = ReportsWritable()
    val reports: Reports = _reports

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

    fun nextTurn(report: TurnReport) {
        turn++
        _reports.add(report)
    }

    fun hasEntity(entityClass: KClass<out Entity>) = all.findOrNull(entityClass) != null

    override fun deepCopy(): User =
        User(txValidators).also { copy ->
            log.trace { "Creating deep copy." }
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

inline fun <reified E : Entity> User.hasEntity() = all.findOrNull<E>() != null


val User.gold get() = resource(Gold::class).owned
val User.food get() = resource(Food::class).owned
val User.citizens get() = resource(Citizen::class).owned
val User.land get() = resource(Land::class).owned

fun User.isGameOver() = hasEntity<Citizen>() && citizens == 0.z
