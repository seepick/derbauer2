package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.building.Field
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.Tent
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
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

class FoodEatProduceDslTest : DslTest, FunSpec() {
    init {
        installDslExtension()
        test("Given almost full When food production and eating Then combined calculation") {
            val citizenCount = Mechanics.citizenEatAmount.neededToGetTo(1) // 10 citizens will eat 1 food
            val tentCount = 10.z // could check for tent-citizen-storage to compute tent count dynamically...
            val fieldCount = 1.z
            val granaryCount = 1.z
            Given {
                setOwned<Gold>(0.z)
                setOwned<Land>(0.z)
                val food = setOwned<Food>(0.z)
                setOwned<Field>(fieldCount)
                setOwned<Tent>(tentCount)
                setOwned<Granary>(granaryCount)
                setOwned<Land>(user.totalLandUse)
                setOwned<Citizen>(citizenCount)
                setOwned<Food>(user.totalStorageFor(food) - 1.z) // leave 1 food free storage to test storage limit
            } When {
                nextTurnToReport()
            } Then {
                shouldOwn<Food>(user.totalStorageFor<Food>()) // totally full, despite +/- food
            }
        }
    }
}
