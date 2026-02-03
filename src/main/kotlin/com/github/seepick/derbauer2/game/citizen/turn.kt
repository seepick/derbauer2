package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.GrowthDiffuser
import com.github.seepick.derbauer2.game.prob.ProbDiffuserKey
import com.github.seepick.derbauer2.game.prob.ProbInitializer
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.resource.findResourceOrNull
import com.github.seepick.derbauer2.game.turn.DefaultTurnStep
import com.github.seepick.derbauer2.game.turn.TurnStepOrder

private val probEatKey = ProbDiffuserKey("eat")
val ProbDiffuserKey.Companion.eatKey get() = probEatKey

class CitizenTurnStep(user: User, private val probs: Probs) : ProbInitializer,
    DefaultTurnStep(user, TurnStepOrder.citizen, listOf(Citizen::class)) {

    override fun initProb() {
        probs.setDiffuser(ProbDiffuserKey.eatKey, GrowthDiffuser(variation = Mechanics.citizenEatGrowthVariation))
    }

    override fun calcResourceChanges() = buildResourceChanges {
        val citizen = user.findResource<Citizen>()
        if (citizen.owned == 0.z) {
            add(ResourceChange(citizen, 0.z))
            return@buildResourceChanges
        }
        val food = user.findResourceOrNull<Food>()

        val isStarving = if (food != null) {
            val rawConsumed = citizen.owned * Mechanics.citizenEatAmount
            val diffusedConsumed = probs.getDiffused(ProbDiffuserKey.eatKey, rawConsumed.zz).toZLimitMinZero()
            val adjustedConsumed = diffusedConsumed orMaxOf 1.z
            add(ResourceChange(food, -adjustedConsumed))

            if (adjustedConsumed > food.owned) { // not enough food, starve
                // TODO adjust starvation intensity depending how much is in negative
                val rawStarving = citizen.owned * Mechanics.citizensStarve
                val adjustedStarving = rawStarving orMaxOf Mechanics.citizensStarveMinimum
                add(ResourceChange(citizen, -adjustedStarving))
                true
            } else false
        } else false

        if (!isStarving) { // birth
            val raw = citizen.owned * Mechanics.citizenBirthRate
            val adjusted = raw orMaxOf 1.z // TODO more variability; more rare/less likely
            add(ResourceChange(citizen, adjusted))
        }
    }
}

private val probTaxKey = ProbDiffuserKey("tax")
val ProbDiffuserKey.Companion.taxKey get() = probTaxKey

class TaxesTurnStep(user: User, private val probs: Probs) : ProbInitializer,
    DefaultTurnStep(user, TurnStepOrder.taxes, listOf(Citizen::class, Gold::class)) {

    override fun initProb() {
        probs.setDiffuser(ProbDiffuserKey.taxKey, GrowthDiffuser(variation = Mechanics.taxGrowthVariation))
    }

    override fun calcResourceChanges() = buildResourceChanges {
        val citizen = user.findResource<Citizen>()
        val rawTax = citizen.owned * Mechanics.taxRate
        val diffusedTax = probs.getDiffused(ProbDiffuserKey.taxKey, rawTax.zz).toZLimitMinZero()
        add(Gold::class, diffusedTax)
    }
}
