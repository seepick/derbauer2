package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.StrictDouble
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.GlobalTurnStep
import io.github.oshai.kotlinlogging.KotlinLogging.logger

interface StatModifier {
    fun modification(statClass: StatKClass): Double?
}

class StatTurnStep(
    private val user: User,
    private val staticModifiers: List<StatModifier>,
) : GlobalTurnStep {

    private val log = logger {}

    override fun execTurn() {
        val modifiers = user.all.filterIsInstance<StatModifier>() + staticModifiers
        log.debug { "execTurn() using modifiers: $modifiers" }
        user.stats.forEach { stat: Stat<out StrictDouble> ->
            stat.changeBy(modifiers.mapNotNull { it.modification(stat::class) }.sum())
        }
    }

    override fun toString() = this::class.simpleName ?: "!simpleName!"
}
