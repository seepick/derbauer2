package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.StrictDouble
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.GlobalTurnStep
import io.github.oshai.kotlinlogging.KotlinLogging.logger

interface StatModifier {
    fun modification(user: User, statClass: StatKClass): Double?
}

interface GlobalStatModifierRepo {
    val all: List<StatModifier>
}

class GlobalStatModifierRepoImpl(
    modifiers: List<StatModifier>,
) : GlobalStatModifierRepo {
    override val all = modifiers
}

class StatCompositeGlobalTurnStep(
    private val user: User,
    private val globalModifiers: GlobalStatModifierRepo,
) : GlobalTurnStep {

    private val log = logger {}

    override fun execTurn() {
        val entityLocalModifiers = user.all.filterIsInstance<StatModifier>()
        val allModifiers = entityLocalModifiers + globalModifiers.all
        log.debug { "execTurn() using modifiers: $allModifiers" }
        user.stats.forEach { stat: Stat<out StrictDouble> ->
            stat.changeBy(allModifiers.mapNotNull { it.modification(user, stat::class) }.sum())
        }
    }

    override fun toString() = this::class.simpleName ?: "!simpleName!"
}
