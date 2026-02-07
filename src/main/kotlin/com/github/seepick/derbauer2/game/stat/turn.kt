package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.hasEntities
import com.github.seepick.derbauer2.game.prob.ProbInitializer
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.citizen
import com.github.seepick.derbauer2.game.turn.GenericTurnStep

class StatTurnStep(
    private val user: User,
    private val probs: Probs,
) : ProbInitializer, GenericTurnStep {

    override fun initProb() {
    }

    override fun execTurn() {
        if (!user.hasEntities(Citizen::class, Happiness::class)) {
            return
        }
        val happiness = user.findStat(Happiness::class)
        val consumedByCitizens = user.citizen.value.toDouble() * Mechanics.statHappinessConsumedPerCitizen
        happiness.change(-consumedByCitizens)
    }

    override fun toString() = this::class.simpleName ?: "!simpleName!"

}
