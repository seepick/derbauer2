package com.github.seepick.derbauer2.game.trade

import com.github.seepick.derbauer2.common.unit.z
import com.github.seepick.derbauer2.common.unit.zz
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.WarningType
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
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

class TradeDslTest : DslTest, DescribeSpec() {
    init {
        installDslExtension()
        describe("When buy 🍖") {
            val defaultFoodBuyAmount = 10
            it("Given sufficient 💰 Then traded ✅ ") {
                Given {
                    setOwned<Gold>(Mechanics.buyFoodCostGold * defaultFoodBuyAmount)
                    setOwned<Food>(0.z)
                    setOwned<Granary>(1.z)
                    user.add(TradeFeature())
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
            it("Given little storage for 🍖 And plenty 💰 Then max buy capped to free storage") {
                val foodDiff = 2
                Given {
                    setOwned<Gold>(1000.z)
                    setOwned<Granary>(1.z)
                    setOwned<Food>(Mechanics.granaryCapacity - foodDiff)
                    user.add(TradeFeature())
                } When {
                    selectPrompt("trade")
                } Then {
                    pageAs<TradePage>().prompt.selectOptions.shouldContainSingleIgnoringCase("buy $foodDiff 🍖 food")
                }
            }
            it("Given plenty storage for 🍖 And little 💰 Then max buy capped to affordable amount") {
                val buyableFoodAmount = 2
                Given {
                    setOwned<Gold>(Mechanics.buyFoodCostGold * buyableFoodAmount)
                    setOwned<Granary>(1.z)
                    setOwned<Food>(0.z)
                    user.add(TradeFeature())
                } When {
                    selectPrompt("trade")
                } Then {
                    val options = pageAs<TradePage>().prompt.selectOptions
                    options shouldContainSingleIgnoringCase "buy $buyableFoodAmount 🍖 food"
                }
            }
        }
        describe("When sell 🍖") {
            // Successful sell
            it("Given less 🍖 owned than default tradable amount Then max sell capped to owned") {
                val foodOwned = 3
                Given {
                    setOwned<Gold>(0.z)
                    setOwned<Granary>(1.z)
                    setOwned<Food>(foodOwned.z)
                    user.add(TradeFeature())
                } When {
                    selectPrompt("trade")
                } Then {
                    val options = pageAs<TradePage>().prompt.selectOptions
                    options shouldContainSingleIgnoringCase "sell $foodOwned 🍖 food"
                }
            }
        }
        describe("Buy Land 🌍") {
            it("Given 0 💰 Then warn ⚠️") {
                Given {
                    setOwned<Gold>(0.z)
                    setOwned<Land>(0.z)
                    setOwned<Food>()
                    user.add(TradeFeature())
                    user.add(TradeLandFeature())
                } When {
                    selectPrompt("trade")
                    selectPrompt("buy 1 🌍")
                } Then {
                    shouldHaveRaisedWarningOfType(WarningType.INSUFFICIENT_RESOURCES)
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
val Prompt.selectOptions: List<String>
    get() = (this as SingleSelectPrompt).options.items.map { it.label.value }
