package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import io.kotest.core.spec.style.StringSpec
import kotlin.math.ceil

class TaxDslTest : DslTest, StringSpec() {
    init {
        installDslExtension()
        "citizens pay tax" {
            val citizenCount = 100.z
            val givenGold = 0.z
            // ensure sufficient housing + birth
            val citizenCountPlusBabies = citizenCount + citizenCount * Mechanics.citizenBirthRate
            Given {
                val houseCount = ceil(citizenCountPlusBabies.value.toDouble() / Mechanics.houseStoreCitizen).toInt()
                val landCount = houseCount * Mechanics.houseLandUse.value
                setOwned<Gold>(givenGold)
                setOwned<Land>(landCount.z)
                setOwned<House>(houseCount.z)
                setOwned<Citizen>(citizenCount)
            } When {
                nextTurnToReport()
            } Then {
                val expectedTax = citizenCountPlusBabies * Mechanics.taxRate
                shouldOwn<Gold>(expectedTax)
            }
        }
    }
}
