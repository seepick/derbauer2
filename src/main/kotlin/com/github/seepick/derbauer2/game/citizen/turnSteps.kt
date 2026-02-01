package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.freeStorageFor
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.turn.DefaultResourceTurnStep
import com.github.seepick.derbauer2.game.turn.TurnPhase

class CitizenReproduceResourceTurnStep(user: User) :
    DefaultResourceTurnStep(user, TurnPhase.First, listOf(Citizen::class)) {
    override fun calcResourceChange() =
        user.resource<Citizen>().let { citizen ->
            ResourceChange(
                resource = citizen,
                changeAmount = if (citizen.owned == 0.z) {
                    0.z
                } else {
                    (citizen.owned * Mechanics.citizenReproductionRate)
                        .orMaxOf(Mechanics.citizenReproductionMinimum)
                        .orMinOf(user.freeStorageFor(citizen))
                }
            )
        }
}

class CitizenFoodEatenResourceTurnStep(user: User) :
    DefaultResourceTurnStep(user, TurnPhase.First, listOf(Citizen::class, Food::class)) {
    override fun calcResourceChange(): ResourceChange {
        val food = user.resource<Food>()
        val citizen = user.resource<Citizen>()
        return if (food.owned == 0.z) {
            val rawStarving = citizen.owned * Mechanics.citizensStarve
            val starving = rawStarving orMaxOf Mechanics.citizensStarveMinimum
            ResourceChange(citizen, -starving)
        } else {
            val rawFoodConsumed = citizen.owned * Mechanics.citizenFoodConsume
            val foodConsumed = rawFoodConsumed orMinOf food.owned
            ResourceChange(food, -foodConsumed)
        }
    }
}

class CitizenTaxesResourceTurnStep(user: User) :
    DefaultResourceTurnStep(user, TurnPhase.Last, listOf(Citizen::class, Gold::class)) {
    override fun calcResourceChange(): ResourceChange {
        val citizen = user.resource<Citizen>()
        val taxIncome = citizen.owned * Mechanics.citizenTax
        return ResourceChange(user.resource<Gold>(), taxIncome)
    }
}
