package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.GaussianDiffuser
import com.github.seepick.derbauer2.game.prob.ProbDiffuserKey
import com.github.seepick.derbauer2.game.prob.ProbInitializer
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.resource.freeStorageFor
import com.github.seepick.derbauer2.game.turn.DefaultTurnStep
import com.github.seepick.derbauer2.game.turn.TurnPhase

class CitizenReproduceTurnStep(user: User) :
    DefaultTurnStep(user, TurnPhase.First, listOf(Citizen::class)) {
    override fun calcResourceChanges() = buildResourceChanges {
        val citizen = user.findResource<Citizen>()
        add(
            ResourceChange(
                resource = citizen,
                changeAmount = if (citizen.owned == 0.z) {
                    0.z
                } else {
                    val raw = citizen.owned * Mechanics.citizenReproductionRate
                    raw orMaxOf Mechanics.citizenReproductionMinimum orMinOf user.freeStorageFor(citizen)
                }
            )
        )
    }
}

class CitizenFoodEatenTurnStep(user: User) :
    DefaultTurnStep(user, TurnPhase.First, listOf(Citizen::class, Food::class)) {
    override fun calcResourceChanges() = buildResourceChanges {
        val citizen = user.findResource<Citizen>()
        add(
            if (citizen.owned == 0.z) {
                ResourceChange(citizen, 0.z)
            } else {
                val food = user.findResource<Food>()
                if (food.owned == 0.z) {
                    val rawStarving = citizen.owned * Mechanics.citizensStarve
                    val adjustedStarving = rawStarving orMaxOf Mechanics.citizensStarveMinimum
                    ResourceChange(citizen, -adjustedStarving)
                } else {
                    val rawConsumed = citizen.owned * Mechanics.citizenFoodConsume
                    val adjustedConsumed = rawConsumed orMaxOf 1.z orMinOf food.owned
                    ResourceChange(food, -adjustedConsumed)
                }
            }
        )
    }
}

private val probTaxKey = ProbDiffuserKey("tax")
val ProbDiffuserKey.Companion.taxKey get() = probTaxKey

class CitizenTaxesTurnStep(
    user: User,
    private val probs: Probs,
) : ProbInitializer,
    DefaultTurnStep(user, TurnPhase.Last, listOf(Citizen::class, Gold::class)) {

    private val taxProb = ProbDiffuserKey.taxKey

    override fun initProb() {
        probs.setDiffuser(taxProb, GaussianDiffuser())
    }

    override fun calcResourceChanges() = buildResourceChanges {
        val citizen = user.findResource<Citizen>()
        val rawTax = citizen.owned * Mechanics.citizenTaxRate
        val rawDiffusedTax = probs.getDiffused(taxProb, rawTax.zz)
        val limittedTax = rawDiffusedTax.toZLimitMinZero()
        add(Gold::class, limittedTax)
    }
}
