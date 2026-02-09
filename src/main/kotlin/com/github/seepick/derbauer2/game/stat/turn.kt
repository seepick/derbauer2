package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.StrictDouble
import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.GlobalTurnStep
import com.github.seepick.derbauer2.game.turn.TurnReport
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.reflect.KClass

class StatCompositeGlobalTurnStep(
    private val user: User,
    private val preModifiers: GlobalPreStatModifierRepo,
    private val postModifiers: GlobalPostStatModifierRepo,
) : GlobalTurnStep {

    private val log = logger {}

    init {
        require(preModifiers.getAll().all { it !is Entity }) {
            "Turn pre-modifiers must NOT implement Entity! (${preModifiers.getAll()})"
        }
        require(postModifiers.getAll().all { it !is Entity }) {
            "Turn post-modifiers must NOT implement Entity! (${postModifiers.getAll()})"
        }
    }

    override fun execPreTurn() {
        execModifiers(preModifiers.getAll(), PreStatModifier::class) { statClass ->
            calcModifierOrNull(user, statClass)
        }
    }

    override fun execPostTurn(report: TurnReport) {
        execModifiers(postModifiers.getAll(), PostStatModifier::class) { statClass ->
            calcModifierOrNull(report, user, statClass)
        }
    }

    private fun <T : Any> execModifiers(
        koinGlobalModifiers: List<T>,
        filterClass: KClass<T>,
        modification: T.(StatKClass) -> Double?,
    ) {
        val entityLocalModifiers = user.all.filterIsInstance(filterClass.java)
        val allModifiers = entityLocalModifiers + koinGlobalModifiers
        log.debug { "Exec turn using modifiers: $allModifiers" }
        user.stats.forEach { stat: Stat<out StrictDouble> ->
            stat.changeBy(
                allModifiers.mapNotNull { it.modification(stat::class) }.sum(),
            )
        }
    }

    override fun toString() = this::class.simpleName ?: "!simpleName!"
}
