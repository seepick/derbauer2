package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.GrowthDiffuser
import com.github.seepick.derbauer2.game.prob.ProbDiffuserKey
import com.github.seepick.derbauer2.game.prob.ProbInitializer
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.findResourceOrNull

private val probEatKey = ProbDiffuserKey("eat")
val ProbDiffuserKey.Companion.eatKey get() = probEatKey

/**
 * Not implementing the [com.github.seepick.derbauer2.game.turn.TurnStep] interface, as used as a composition element.
 * See: [com.github.seepick.derbauer2.game.turn.ProduceCitzenCompositeTurnStep]
 */
class CitizenTurnStep(private val user: User, private val probs: Probs) : ProbInitializer {

    override fun initProb() {
        probs.setDiffuser(ProbDiffuserKey.eatKey, GrowthDiffuser(variation = Mechanics.citizenEatGrowthVariation))
    }

    fun calcTurnChanges(foodProduction: Zz?) = buildResourceChanges {
        val citizen = user.findResourceOrNull<Citizen>() ?: return@buildResourceChanges
        if (citizen.owned == 0.z) {
            add(ResourceChange(citizen, 0.z))
            return@buildResourceChanges
        }
        val (changes, isStarving) = eatingAndStarving(citizen, foodProduction ?: 0.zz)
        addChanges(changes)
        if (!isStarving) {
            add(birthChange(citizen))
        }
    }

    private fun eatingAndStarving(citizen: Citizen, foodChangingBy: Zz): EatingStarvingResult {
        val food = user.findResourceOrNull<Food>() ?: return EatingStarvingResult(ResourceChanges.empty, false)
        val eatChange = calcEatChange(citizen)
        val futureFoodOwned = food.owned.zz + eatChange.changeAmount // TODO make use of foodChangingBy
        if (futureFoodOwned >= 0.zz) { // not enough food => starving
            return EatingStarvingResult(ResourceChanges(listOf(eatChange)), false)
        }
        // negative food eaten amount => starve
        val starveChange = calcStarveChange(citizen, eatChange.changeAmount)
        return EatingStarvingResult(ResourceChanges(listOf(eatChange, starveChange)), true)
    }

    /** @return negative numbered change */
    private fun calcEatChange(citizen: Citizen): ResourceChange {
        val rawConsumed = citizen.owned * Mechanics.citizenEatAmount
        val diffusedConsumed = probs.getDiffused(ProbDiffuserKey.eatKey, rawConsumed.zz).toZLimitMinZero()
        val adjustedConsumed = diffusedConsumed orMaxOf 1.z
        return ResourceChange(Food::class, -adjustedConsumed)
    }

    private fun calcStarveChange(citizen: Citizen, changeAmount: Zz): ResourceChange {
        // TODO adjust starvation intensity depending how much `changeAmount` is in negative
        val rawStarving = citizen.owned * Mechanics.citizensStarve
        val adjustedStarving = rawStarving orMaxOf Mechanics.citizensStarveMinimum
        return ResourceChange(citizen, -adjustedStarving)
    }

    private fun birthChange(citizen: Citizen): ResourceChange {
        val raw = citizen.owned * Mechanics.citizenBirthRate
        val adjusted = raw orMaxOf 1.z // TODO more variability; more rare/less likely
        return ResourceChange(citizen, adjusted)
    }
}

private data class EatingStarvingResult(val changes: ResourceChanges, val isStarving: Boolean)
