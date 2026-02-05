package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.citizen.eatKey
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.prob.PassThroughDiffuser
import com.github.seepick.derbauer2.game.prob.ProbDiffuserKey
import com.github.seepick.derbauer2.game.prob.prob
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.TurnOff
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import io.kotest.core.spec.style.StringSpec

class GameDslTest : DslTest, StringSpec() {
    init {
        installDslExtension()
        "Starve when no food despite farm" {
            val givenCitizen = 20.z
            val givenFood = 0.z
            val givenFarm = 1.z
            val expectedCitizen = 15.z
            val expectedFood = 3.z
            Given(initAssets = true) {
                turnOff(TurnOff.happenings)
                prob {
                    updateDiffuser(ProbDiffuserKey.eatKey, PassThroughDiffuser)
                }
                setOwned<Land>(100.z)
                setOwned<Food>(givenFood)
                setOwned<Farm>(givenFarm)
                setOwned<House>(4.z)
                setOwned<Citizen>(givenCitizen)
            } When {
                printPage()
                nextTurnToReport {
                    printPage()
                    nextPage()
                }
                nextTurnToReport {
                    printPage()
                    nextPage()
                }
            } Then {
                shouldOwn<Citizen>(expectedCitizen)
                shouldOwn<Food>(expectedFood)
            }
        }
    }
}
