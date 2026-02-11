package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.common.unit.double11
import com.github.seepick.derbauer2.common.unit.z
import com.github.seepick.derbauer2.game.building.Tent
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.stat.happiness
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import kotlin.math.ceil

class CapitalismDslTest : DslTest, StringSpec() {
    init {
        installDslExtension()
        "Given capitalism, some 🙎🏻‍♂️, 🥳 When next turn Then -🥳 proportionally to 🙎🏻‍♂️" {
            val citizenOwned = 100
            val initialHappiness = 0.0.double11
            Given {
                setOwned<Citizen>(citizenOwned.z)
                setStatD11<Happiness>(initialHappiness)
                setOwned<Tent>(ceil(citizenOwned.toDouble() / Mechanics.tentStoresCitizen).toLong().z)
                user.add(CapitalismTech())
            } When {
                nextTurn()
            } Then {
                val expectedHappiness = initialHappiness.number +
                        (citizenOwned.toDouble() * Mechanics.techCapitalismHappinessPerCitizenMultiplier)
                user.happiness shouldBeEqual expectedHappiness.double11
            }
        }
    }
}
