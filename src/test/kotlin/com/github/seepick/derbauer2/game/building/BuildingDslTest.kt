package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import io.kotest.core.spec.style.DescribeSpec

class BuildingDslTest : DslTest, DescribeSpec() {
    init {
        installDslExtension()
        describe("When build simple building") {
            it("Given sufficient gold Then owned houses increases") {
                Given {
                    setOwned<House>(0.z)
                    setOwned<Gold>(Mechanics.houseCostsGold)
                    // without Land, engine doesn't check for it (sweet side-effect of dynamics ;)
                } When {
                    selectBuild {
                        build(House::class)
                    }
                } Then {
                    shouldOwn<House>(1.z)
                }
            }
            it("Given insufficient gold Then building fails") {
                Given {
                    setOwned<House>(0.z)
                    setOwned<Gold>(1.z)
                } When {
                    selectBuild {
                        build<House>()
                    }
                } Then {
                    shouldRaiseWarning("insufficient resources")
                    shouldOwn<House>(0.z) // untouched
                    shouldOwn<Gold>(1.z) // untouched
                }
            }
            it("Given almost not enough land When build exceeding farm Then building fails") {
                val insufficientLand = Mechanics.farmLandUse - 1.z
                val sufficientGold = Mechanics.farmCostsGold
                Given {
                    setOwned<Gold>(sufficientGold)
                    setOwned<Land>(insufficientLand)
                    setOwned<Farm>(0.z)
                } When {
                    selectBuild {
                        build<Farm>()
                    }
                } Then {
                    shouldRaiseWarning("using more land than available")
                    shouldOwn<Gold>(sufficientGold) // untouched
                    shouldOwn<Land>(insufficientLand) // untouched
                    shouldOwn<Farm>(0.z) // untouched
                }
            }
        }
    }
}
