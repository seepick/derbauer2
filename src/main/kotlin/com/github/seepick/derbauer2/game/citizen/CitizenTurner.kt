package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.resource.resourceOrNull

class CitizenReproduceResourceTurnStep(
    val user: User,
) : ResourceTurnStep {
    override val phase = TurnPhase.First
    override val requiresEntities = listOf(Citizen::class)
    override fun calcResourceChanges() = user.all.find<Citizen>().let { citizen ->
        listOf( // TODO improve-refactor: allow to return single (optionally)
            ResourceChange(
                resource = citizen,
                changeAmount = if (citizen.owned == 0.z) {
                    0.z
                } else {
                    Mechanics.citizenReproductionMinimum.orMaxOf(
                        citizen.owned * Mechanics.citizenReproductionRate
                    )
                }
            )
        )
    }
}

class CitizenTurner(
    private val user: User,
) {
    fun buildResourceChanges() = buildResourceChanges {
        if (!user.hasEntity(Citizen::class)) {
            return@buildResourceChanges
        }
        consumeFoodOrStarve()?.let { add(it) }
        payTaxes()?.let { add(it) }
    }


    fun consumeFoodOrStarve(): ResourceChange? =
        user.resourceOrNull(Food::class)?.let { food ->
            val citizen = user.resource(Citizen::class)
            if (food.owned == 0.z) {
                val rawStarving = citizen.owned * Mechanics.citizensStarve
                val starving = rawStarving.orMaxOf(Mechanics.citizensStarveMinimum)
                ResourceChange(citizen, -starving)
            } else {
                val rawFoodConsumed = citizen.owned * Mechanics.citizenFoodConsume
                val foodConsumed = rawFoodConsumed.orMinOf(food.owned)
                ResourceChange(food, -foodConsumed)
            }
        }

    fun payTaxes(): ResourceChange? =
        user.resourceOrNull(Gold::class)?.let { gold ->
            val citizen = user.resource(Citizen::class)
            val taxIncome = citizen.owned * Mechanics.citizenTax
            ResourceChange(gold, taxIncome)
        }
}
