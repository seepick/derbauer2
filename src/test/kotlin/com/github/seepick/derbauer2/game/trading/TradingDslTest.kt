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
import com.github.seepick.derbauer2.game.testInfra.dsl.pageAs
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import com.github.seepick.derbauer2.textengine.prompt.Prompt
import com.github.seepick.derbauer2.textengine.prompt.SingleSelectPrompt
import com.github.seepick.derbauer2.textengine.shouldContainSingleIgnoringCase
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class TradingDslTest : DslTest, DescribeSpec() {
    init {
        installDslExtension()
        describe("When buy 🍖") {
            val defaultFoodBuyAmount = 10
            it("Given 0 💰 Then warn ⚠️") {
                Given {
                    setOwned<Gold>(0.z)
                    setOwned<Food>(0.z)
                    user.add(TradingFeature())
                } When {
                    selectPrompt("trade")
                    selectPrompt("buy $defaultFoodBuyAmount 🍖")
                } Then {
                    shouldHaveRaisedWarningOfType(WarningType.INSUFFICIENT_RESOURCES)
                }
            }
            it("Given sufficient 💰 Then traded ✅ ") {
                Given {
                    setOwned<Gold>(Mechanics.buyFoodCostGold * defaultFoodBuyAmount)
                    setOwned<Food>(0.z)
                    setOwned<Granary>(1.z)
                    user.add(TradingFeature())
                } When {
                    selectPrompt("trade")
                    selectPrompt("buy $defaultFoodBuyAmount 🍖")
                } Then {
                    user.gold shouldBeEqual 0.z
                    user.food shouldBeEqual defaultFoodBuyAmount.z
                    shouldRaisedNoWarning()
                    shouldActionDispatched(
                        ResourcesTradedAction(
                            Food::class to defaultFoodBuyAmount.zz,
                            Gold::class to -(Mechanics.buyFoodCostGold * defaultFoodBuyAmount)
                        )
                    )
                }
            }
        }
        describe("When sell 🍖") {
            it("Max sell capped 3/10") {
                val foodOwned = 3
                Given {
                    setOwned<Gold>(0.z)
                    setOwned<Granary>(1.z)
                    setOwned<Food>(foodOwned.z)
                    user.add(TradingFeature())
                } When {
                    selectPrompt("trade")
                } Then {
                    pageAs<TradingPage>().prompt.selectOptions.shouldContainSingleIgnoringCase("Sell $foodOwned 🍖 Food")
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
val Prompt.selectOptions: List<String>
    get() = (this as SingleSelectPrompt).options.items.map { it.label.value }
