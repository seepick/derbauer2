package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.ResourceReportBuilder
import com.github.seepick.derbauer2.game.resource.buildResourceReport
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.resource.resourceOrNull

class CitizenTurner(
    private val user: User,
) {
    fun buildReport() = buildResourceReport {
        if(!user.hasEntity(Citizen::class)) {
            return@buildResourceReport
        }
        maybeAddTaxation()
        maybeConsumeFoodOrStarve()
    }

    private fun ResourceReportBuilder.maybeAddTaxation() {
        user.resourceOrNull(Gold::class)?.let { gold ->
            val citizen = user.resource(Citizen::class)
            val taxIncome = citizen.owned * Mechanics.citizenTax
            add(gold, taxIncome)
        }
    }

    private fun ResourceReportBuilder.maybeConsumeFoodOrStarve() {
        user.resourceOrNull(Food::class)?.let { food ->
            val citizen = user.resource(Citizen::class)
            if (food.owned == 0.z) {
                val rawStarving = citizen.owned * Mechanics.citizensStarve
                val starving = rawStarving.maxOf(Mechanics.citizensStarveMinimum)
                add(citizen, -starving)
            } else {
                val rawFoodConsumed = citizen.owned * Mechanics.citizenFoodConsume
                val foodConsumed = rawFoodConsumed.minOf(food.owned)
                add(food, -foodConsumed)
            }
        }
    }
}
