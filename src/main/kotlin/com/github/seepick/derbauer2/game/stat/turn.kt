package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.StrictDouble
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.GenericTurnStep

interface StatModifier {
    fun modification(statClass: StatKClass): Double?
}

class StatTurnStep(
    private val user: User,
) : GenericTurnStep {

    override fun execTurn() {
        val modifiers = user.all.filterIsInstance<StatModifier>()
        user.stats.forEach { stat: Stat<out StrictDouble> ->
            stat.change(modifiers.mapNotNull { it.modification(stat::class) }.sum())
        }
    }

    override fun toString() = this::class.simpleName ?: "!simpleName!"
}
