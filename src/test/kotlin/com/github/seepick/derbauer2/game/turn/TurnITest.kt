package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.testInfra.itest.Given
import com.github.seepick.derbauer2.game.testInfra.itest.ITest
import com.github.seepick.derbauer2.game.testInfra.itest.Then
import com.github.seepick.derbauer2.game.testInfra.itest.When
import com.github.seepick.derbauer2.game.testInfra.itest.installGameKoinExtension
import io.kotest.core.spec.style.FunSpec
import kotlin.math.ceil

class TurnITest : ITest, FunSpec() {
    init {
        installGameKoinExtension()
        test("citizens pay taxes") {
            val citizenCount = 100.z
            // ensure sufficient housing also for newborns
            val citizenCountAfterReproduction = citizenCount + citizenCount * Mechanics.citizenReproductionRate
            val houseCount = ceil(citizenCountAfterReproduction.value.toDouble() / Mechanics.houseStoreCitizen).toInt()
            val landCount = houseCount * Mechanics.houseLandUse
            Given {
                setOwned<Gold>(0.z)
                changeOwned<Land>(landCount.z)
                changeOwned<House>(houseCount.z)
                setOwned<Citizen>(citizenCount)
            } When {
                nextTurnToReport()
            } Then {
                val expectedTax = citizenCountAfterReproduction * Mechanics.citizenTax
                shouldOwn<Gold>(expectedTax)
            }
        }
    }
}
