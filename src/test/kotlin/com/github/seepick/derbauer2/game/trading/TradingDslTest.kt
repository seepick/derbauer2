package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.WarningType
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.food
import com.github.seepick.derbauer2.game.resource.gold
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class TradingDslTest : DslTest, DescribeSpec() {
    init {
        installDslExtension()
        describe("When buy 🍖") {
            it("Given 0 💰 Then warn ⚠️") {
                Given {
                    setOwned<Gold>(0.z)
                    setOwned<Food>(0.z)
                    user.add(TradingFeature())
                } When {
                    selectPrompt("trade")
                    selectPrompt("buy 1 🍖")
                } Then {
                    shouldHaveRaisedWarningOfType(WarningType.INSUFFICIENT_RESOURCES)
                }
            }
            it("Given sufficient 💰 Then traded ✅ ") {
                Given {
                    setOwned<Gold>(Mechanics.buyFoodCostGold)
                    setOwned<Food>(0.z)
                    setOwned<Granary>(1.z)
                    user.add(TradingFeature())
                } When {
                    selectPrompt("trade")
                    selectPrompt("buy 1 🍖")
                } Then {
                    user.gold shouldBeEqual 0.z
                    user.food shouldBeEqual 1.z
                    shouldRaisedNoWarning()
                    shouldActionDispatched(
                        ResourcesTradedAction(
                            Food::class to 1.zz,
                            Gold::class to -Mechanics.buyFoodCostGold
                        )
                    )
                }
            }
        }
    }
}
