package com.github.seepick.derbauer2.game.integrationTests

import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.integrationTests.testInfra.Given
import com.github.seepick.derbauer2.game.integrationTests.testInfra.Then
import com.github.seepick.derbauer2.game.integrationTests.testInfra.When
import com.github.seepick.derbauer2.game.integrationTests.testInfra.installGameKoinExtension
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import io.kotest.core.spec.style.FunSpec
import org.koin.test.KoinTest
import kotlin.math.ceil

class EndTurnITest : KoinTest, FunSpec() {
    init {
        installGameKoinExtension()
        test("citizens pay taxes") {
            val citizenCount = 100.z
            val houseCount = ceil(citizenCount.value.toDouble() / Mechanics.houseStoreCitizen).toInt()
            val landCount = houseCount * Mechanics.houseLandUse
            Given {
                setOwned<Gold>(0.z)
                changeOwned<Land>(landCount.z)
                changeOwned<House>(houseCount.z)
                setOwned<Citizen>(citizenCount)
            } When {
                nextTurn()
            } Then {
                val expectedTax = citizenCount * Mechanics.citizenTax
                shouldOwn<Gold>(expectedTax)
            }
        }
    }
}
