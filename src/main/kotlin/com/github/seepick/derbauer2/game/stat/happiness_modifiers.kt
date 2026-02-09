package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.happinessChanger
import com.github.seepick.derbauer2.game.core.hasEntity
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.citizen
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import com.github.seepick.derbauer2.game.turn.TurnReport

class HappinessCitizenPreModifier : PreStatModifier {
    override fun calcModifierOrNull(user: User, statClass: StatKClass): Double? {
        if (statClass != Happiness::class || !user.hasEntity(Citizen::class)) {
            return null
        }
        return -(user.citizen.value.toDouble() * Mechanics.statHappinessConsumedPerCitizen)
    }

    override fun toString() = "${this::class.simpleName}"
}

class HappinessSeasonPreModifier(
    private val turn: CurrentTurn,
) : PreStatModifier {
    override fun calcModifierOrNull(user: User, statClass: StatKClass): Double? {
        if (statClass != Happiness::class) {
            return null
        }
        return turn.current.season.happinessChanger
    }

    override fun toString() = "${this::class.simpleName}"
}

class HappinessDeathPostModifier : PostStatModifier {
    override fun calcModifierOrNull(report: TurnReport, user: User, statClass: StatKClass): Double? {
        if (statClass != Happiness::class || !user.hasEntity(Citizen::class)) {
            return null
        }
        val citizenChange = report.resourceChanges.changeFor(Citizen::class) ?: return null

        return if (citizenChange.change.isNegative) {
            Mechanics.statHappinessDeathPenalty
        } else {
            null
        }
    }

    override fun toString() = "${this::class.simpleName}"
}
