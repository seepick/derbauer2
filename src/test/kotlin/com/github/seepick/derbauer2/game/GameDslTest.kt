package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.Field
import com.github.seepick.derbauer2.game.building.Tent
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import com.github.seepick.derbauer2.game.testInfra.z
import io.kotest.core.spec.style.StringSpec
import kotlin.math.ceil

class GameDslTest : DslTest, StringSpec() {
    init {
        installDslExtension()
        "Given many 🙎🏻‍♂️ and no 🍖 but 1 field When next turn Then some starve ☠️ despite 1 field" {
            // 10% eat; field +3 food; 31 citizens -> 1 starves
            val givenCitizen = 31.z
            val givenFood = 0.z
            val givenFields = 1.z
            val expectedCitizen = givenCitizen - 1.z
            val expectedFood = 0.z
            Given(initAssets = true) {
                setOwned<Land>(1000.z)
                setOwned<Tent>(ceil(givenCitizen.value.toDouble() / Mechanics.tentStoresCitizen.toDouble()).z)
                setOwned<Citizen>(givenCitizen)
                setOwned<Food>(givenFood)
                setOwned<Field>(givenFields)
            } When {
                nextTurnToReport()
            } Then {
                shouldOwn<Citizen>(expectedCitizen)
                shouldOwn<Food>(expectedFood)
            }
        }
    }
}
