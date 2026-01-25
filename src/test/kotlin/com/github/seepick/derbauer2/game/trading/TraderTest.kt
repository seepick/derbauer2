package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.gold
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.trading.TradeOperation.Buy
import com.github.seepick.derbauer2.game.trading.TradeOperation.Sell
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class TraderTest : DescribeSpec({
    var user = User()
    beforeTest {
        user = User()
    }

    context("Buy") {
        describe("Results") {
            it("When buy infinite resource like gold Then succeed") {
                user.addEntity(Gold(0.units))
                Trader(user).trade(
                    TradeRequest(Gold::class, Buy, 1.units),
                ) shouldBeEqual TradeResult.Success
            }
            it("Given enough storage When buy storable Then succeed") {
                user.addEntity(Food(0.units))
                user.addEntity(Granary(1.units))

                Trader(user).trade(
                    TradeRequest(Food::class, Buy, 1.units),
                ) shouldBeEqual TradeResult.Success
            }
            it("Given no storage When buy Then fail") {
                user.addEntity(Food(0.units))

                Trader(user).trade(
                    TradeRequest(Food::class, Buy, 1.units),
                ) shouldBeEqual TradeResult.NotEnoughStorage
            }
        }

        describe("Operation") {
            val buyAmount = 1.units
            it("When buy infinite resource gold Then increased") {
                user.addEntity(Gold(0.units))
                Trader(user).trade(TradeRequest(Gold::class, Buy, 1.units))
                user.gold shouldBeEqual 1.units
            }
        }
    }

    context("Sell") {
        describe("Results") {
            it("Given no gold When selling Then fails") {
                user.addEntity(Gold(0.units))

                Trader(user).trade(
                    TradeRequest(Gold::class, Sell, 1.units),
                ) shouldBeEqual TradeResult.NotEnoughResources
            }
            it("Given some gold When selling Then succeed") {
                user.addEntity(Gold(1.units))

                Trader(user).trade(
                    TradeRequest(Gold::class, Sell, 1.units),
                ) shouldBeEqual TradeResult.Success
            }
        }
        describe("Operation") {
            it("Given no gold When selling Then gold unchanged") {
                user.addEntity(Gold(0.units))
                Trader(user).trade(TradeRequest(Gold::class, Sell, 1.units))
                user.gold shouldBe 0.units
            }
            it("Given some gold When selling Then gold changed") {
                user.addEntity(Gold(1.units))
                Trader(user).trade(TradeRequest(Gold::class, Sell, 1.units))
                user.gold shouldBe 0.units
            }
        }
    }
})
