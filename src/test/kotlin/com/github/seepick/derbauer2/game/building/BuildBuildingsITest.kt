package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.testInfra.itest.Given
import com.github.seepick.derbauer2.game.testInfra.itest.Then
import com.github.seepick.derbauer2.game.testInfra.itest.When
import com.github.seepick.derbauer2.game.testInfra.itest.installGameKoinExtension
import io.kotest.core.spec.style.DescribeSpec
import org.koin.test.KoinTest

class BuildBuildingsITest : KoinTest, DescribeSpec() {
    init {
        installGameKoinExtension()
        describe("When build simple building") {
            it("Given sufficient gold Then owned houses increases") {
                Given() {
                    setOwned<House>(0.z)
                    setOwned<Gold>(Mechanics.houseCostsGold.z)
                    // without the concept of Land, the engine doesn't check for it; sweet side-effect of dynamics
                } When {
                    selectBuild {
                        build(House::class)
                    }
                } Then {
                    shouldOwn<House>(1.z)
                }
            }
            it("Given insufficient gold Then building fails") {
                Given() {
                    setOwned<House>(0.z)
                    setOwned<Gold>(1.z)
                } When {
                    selectBuild {
                        build(House::class)
                    }
                } Then {
                    shouldRaiseWarning("insufficient resources")
                    shouldOwn<House>(0.z) // untouched
                    shouldOwn<Gold>(1.z) // untouched
                }
            }
        }
    }
}
