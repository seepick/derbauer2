package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.citizen.eatKey
import com.github.seepick.derbauer2.game.citizen.taxKey
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.prob.ProbDiffuserKey
import com.github.seepick.derbauer2.game.prob.prob
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.totalLandUse
import com.github.seepick.derbauer2.game.resource.totalStorageFor
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
        test("Given almost full When food production and eating Then combined calculation") {
            val citizenCount = Mechanics.citizenEatAmount.neededToGetTo(1)
            val houseCount = 10.z // TODO Mechanics.howManyHousesNeededToStore(citizenCount)
            val farmCount = 1.z
            val granaryCount = 1.z

            Given {
                setOwned<Gold>(0.z)
                setOwned<Land>(0.z)
                val food = setOwned<Food>(0.z)
                // TODO TURN OFF probabilistic variation for predictable test
                val farm = setOwned<Farm>(farmCount)
                val house = setOwned<House>(houseCount)
                val granary = setOwned<Granary>(granaryCount)
                setOwned<Land>(user.totalLandUse)
                setOwned<Citizen>(citizenCount)
                setOwned<Food>(user.totalStorageFor(food) - 1.z) // leave 1 food free storage to test storage limit
                prob {
                    updateDiffuserPassthrough(ProbDiffuserKey.eatKey)
                }
            } When {
                nextTurnToReport()
            } Then {
                shouldOwn<Food>(user.totalStorageFor<Food>()) // totally full, despite +/- of food
            }
        }
        test("citizens pay taxes") {
            val citizenCount = 100.z
            // ensure sufficient housing also for newborns
            val citizenCountAfterReproduction = citizenCount + citizenCount * Mechanics.citizenBirthRate
            val houseCount = ceil(citizenCountAfterReproduction.value.toDouble() / Mechanics.houseStoreCitizen).toInt()
            val landCount = houseCount * Mechanics.houseLandUse.value

            Given {
                setOwned<Gold>(0.z)
                setOwned<Land>(landCount.z)
                setOwned<House>(houseCount.z)
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
