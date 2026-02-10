package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.WarningType
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
            it("Given sufficient gold Then owned tents increases And action dispatched") {
                Given {
                    setOwned<Tent>(0.z)
                    setOwned<Gold>(Mechanics.tentCostsGold)
                    // without Land, engine doesn't check for it (sweet side-effect of dynamics ;)
                } When {
                    selectBuild {
                        build(Tent::class)
                    }
                } Then {
                    shouldOwn<Tent>(1.z)
                    shouldActionDispatched(BuildingBuiltAction(Tent::class))
                }
            }
            it("Given insufficient gold Then building fails") {
                Given {
                    setOwned<Tent>(0.z)
                    setOwned<Gold>(1.z)
                } When {
                    selectBuild {
                        build<Tent>()
                    }
                } Then {
                    shouldHaveRaisedWarningOfType(WarningType.INSUFFICIENT_RESOURCES)
                    shouldOwn<Tent>(0.z) // untouched
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
                    shouldHaveRaisedWarningOfType(WarningType.LAND_OVERUSE)
                    shouldOwn<Gold>(sufficientGold) // untouched
                    shouldOwn<Land>(insufficientLand) // untouched
                    shouldOwn<Farm>(0.z) // untouched
                }
            }
        }
    }
}
