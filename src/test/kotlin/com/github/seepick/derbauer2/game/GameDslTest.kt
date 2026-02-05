package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.citizen.eatKey
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
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
import com.github.seepick.derbauer2.game.testInfra.z
import io.kotest.core.spec.style.StringSpec
import kotlin.math.ceil

class GameDslTest : DslTest, StringSpec() {
    init {
        installDslExtension()
        "Starve when no food despite farm" {
            // 10% eat; farm +3 food; 31 citizens -> 1 starves
            val givenCitizen = 31.z
            val givenFood = 0.z
            val givenFarm = 1.z
            val expectedCitizen = 30.z
            val expectedFood = 0.z
            Given(initAssets = true) {
                turnOff(TurnOff.happenings)
                prob {
                    updateDiffuser(ProbDiffuserKey.eatKey, PassThroughDiffuser)
                }
                setOwned<Land>(1000.z)
                setOwned<House>(ceil(givenCitizen.value.toDouble() / Mechanics.houseStoreCitizen.toDouble()).z)
                setOwned<Citizen>(givenCitizen)
                setOwned<Food>(givenFood)
                setOwned<Farm>(givenFarm)
            } When {
                nextTurnToReport()
            } Then {
                shouldOwn<Citizen>(expectedCitizen)
                shouldOwn<Food>(expectedFood)
            }
        }
    }
}
