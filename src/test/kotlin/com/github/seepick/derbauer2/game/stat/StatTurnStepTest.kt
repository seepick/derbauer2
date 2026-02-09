package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.double11
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.addResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull

class StatTurnStepTest : StringSpec({
    "Given modifier When applied Then user stat changed" {
        val user = User()
        val happiness = user.add(Happiness(0.0.double11))
        val modifier = object : StatModifier {
            override fun modification(user: User, statClass: StatKClass): Double? =
                if (statClass == happiness::class) -0.5 else null
        }

        StatCompositeGlobalTurnStep(user, GlobalStatModifierRepoImpl(listOf(modifier))).execTurn()

        happiness.value.number shouldBeEqual -0.5
    }
})

class HappinessCitizenModifierTest : StringSpec({
    "Given citizens and happiness Then change based on citizens" {
        val user = User()
        val citizen = user.addResource(Citizen(), 100.z)
        val initialHappiness = 0.0
        val happiness = user.add(Happiness(initialHappiness.double11))

        val modification = HappinessCitizenModifier().modification(user, happiness::class)

        modification.shouldNotBeNull() shouldBeEqual -(citizen.owned.value * Mechanics.statHappinessConsumedPerCitizen)
    }
})
