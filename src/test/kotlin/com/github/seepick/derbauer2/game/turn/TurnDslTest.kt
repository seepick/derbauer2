package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.citizen.taxKey
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.prob.ProbDiffuserKey
import com.github.seepick.derbauer2.game.prob.prob
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import io.kotest.core.spec.style.FunSpec
import kotlin.math.ceil

class TurnDslTest : DslTest, FunSpec() {
    init {
        installDslExtension()
        test("citizens pay taxes") {
            val citizenCount = 100.z
            // ensure sufficient housing also for newborns
            val citizenCountAfterReproduction = citizenCount + citizenCount * Mechanics.citizenBirthRate
            val houseCount = ceil(citizenCountAfterReproduction.value.toDouble() / Mechanics.houseStoreCitizen).toInt()
            val landCount = houseCount * Mechanics.houseLandUse.value
            Given {
                setOwned<Gold>(0.z)
                changeOwned<Land>(landCount.z)
                changeOwned<House>(houseCount.z)
                setOwned<Citizen>(citizenCount)
                prob {
                    updateDiffuserPassthrough(ProbDiffuserKey.taxKey)
                }
            } When {
                nextTurnToReport()
            } Then {
                val expectedTax = citizenCountAfterReproduction * Mechanics.taxRate
                shouldOwn<Gold>(expectedTax)
            }
        }
    }
}
