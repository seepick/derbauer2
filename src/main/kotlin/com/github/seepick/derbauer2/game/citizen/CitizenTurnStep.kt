package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.Z
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
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.stat.findStatOrNull
import kotlin.math.ceil

private val probEatKey = ProbDiffuserKey("eat")
val ProbDiffuserKey.Companion.eatKey get() = probEatKey
private val probBirthKey = ProbDiffuserKey("birth")
val ProbDiffuserKey.Companion.birthKey get() = probBirthKey

/**
 * Not implementing [com.github.seepick.derbauer2.game.turn.ResourceStep] as used as a composition element.
 * See: [com.github.seepick.derbauer2.game.turn.ProduceCitzenCompositeResourceStep]
 */
class CitizenTurnStep(private val user: User, private val probs: Probs) : ProbInitializer {

    override fun initProb() {
        probs.setDiffuser(ProbDiffuserKey.eatKey, GrowthDiffuser(variation = Mechanics.citizenEatVariation))
        probs.setDiffuser(ProbDiffuserKey.birthKey, GrowthDiffuser(variation = Mechanics.citizenBirthVariation))
    }

    fun calcTurnChanges(producedFood: Zz?) = buildResourceChanges {
        val citizen = user.findResourceOrNull<Citizen>() ?: return@buildResourceChanges
        if (citizen.owned == 0.z) {
            add(ResourceChange(citizen, 0.z))
            return@buildResourceChanges
        }
        val (changes, isStarving) = eatingAndStarving(citizen, producedFood ?: 0.zz)
        addChanges(changes)
        if (!isStarving) {
            add(birthChange(citizen))
        }
    }

    private fun eatingAndStarving(citizen: Citizen, producedFood: Zz): EatingStarvingResult {
        val food = user.findResourceOrNull<Food>() ?: return EatingStarvingResult(ResourceChanges.empty, false)
        val basicFoodEaten = calcBasicFoodEaten(citizen)
        val futureFoodOwned = food.owned.zz + (-basicFoodEaten) + producedFood
        if (futureFoodOwned >= 0.zz) { // enough food -> not starving
            return EatingStarvingResult(ResourceChanges(listOf(ResourceChange(food, -basicFoodEaten))), false)
        }
        // negative food eaten amount -> starve
        val ownedPlusProduce = food.owned + producedFood
        val starveChange = calcStarveChange(citizen, ownedPlusProduce, basicFoodEaten)
        val effectivelyEaten = if (ownedPlusProduce < 0.zz) 0.zz else -ownedPlusProduce
        return EatingStarvingResult(ResourceChanges(listOf(ResourceChange(food, effectivelyEaten), starveChange)), true)
    }

    /** @return negative numbered change */
    private fun calcBasicFoodEaten(citizen: Citizen): Z {
        val rawConsumed = ceil(citizen.owned.value.toDouble() * Mechanics.citizenEatAmount.number).toLong().zz
        val diffusedConsumed = probs.getDiffused(ProbDiffuserKey.eatKey, rawConsumed).toZLimitMinZero()
        return diffusedConsumed.coerceAtLeast(1.z)
    }

    private fun calcStarveChange(citizen: Citizen, foodOwned: Zz, eatenFood: Z): ResourceChange {
        val unfedCitizens =
            StarveCompute.howManyUnfed(citizen.owned, foodOwned, eatenFood, Mechanics.citizenEatAmount)
        val starving = (unfedCitizens * Mechanics.citizensStarvePerUnfedCitizen).coerceAtLeast(1.z)
        return ResourceChange(citizen, -starving)
    }

    private fun birthChange(citizen: Citizen): ResourceChange {
        val raw = citizen.owned * Mechanics.citizenBirthRate
        val diffused = probs.getDiffused(ProbDiffuserKey.birthKey, raw.zz).toZLimitMinZero()
        val happyInfluenced = happinessInfluencedBirthChange(diffused)
        val capped = happyInfluenced.coerceAtLeast(1.z)
        return ResourceChange(citizen, capped)
    }

    private fun happinessInfluencedBirthChange(base: Z): Z {
        val happiness = user.findStatOrNull(Happiness::class) ?: return base
        val multiplier = happiness.value.number * Mechanics.citizenBirthHappinessEffect.number
        return (base.value.toDouble() * (multiplier + 1.0)).toLong().z
    }
}

private data class EatingStarvingResult(
    val changes: ResourceChanges,
    val isStarving: Boolean,
)
