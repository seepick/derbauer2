package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.logic.Mechanics
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.z
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.buildResourceReport
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.resource.resourceOrNull
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun citizenModule() = module {
    singleOf(::CitizenTurner)
}

class CitizenTurner(
    private val user: User,
) {
    fun executeAndBuildReport() = buildResourceReport {
        val citizen = user.resourceOrNull(Citizen::class) ?: return@buildResourceReport

        user.resourceOrNull(Gold::class)?.let { gold ->
            val taxIncome = (citizen.owned.value * Mechanics.citizenTaxPercentage).toLong().z
            // FIXME create TX "upstairs"
//            gold.owned += taxIncome
            add(gold, taxIncome)
        }

        user.resourceOrNull(Food::class)?.let { food ->
            val foodConsumed = (citizen.owned.value * Mechanics.citizenFoodConsumePercentage).toLong().z
            val food = user.resource(Food::class)
//            food.owned -= foodConsumed // FIXME check for negative! maybe dont manipulate directly, but via method!
            // TODO if not enough food, citizens should decrease (left, or died)
            add(food, -foodConsumed)
        }
        // TODO increase citizens (birth, immigration)
    }
}