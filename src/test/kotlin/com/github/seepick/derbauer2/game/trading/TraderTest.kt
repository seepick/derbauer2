package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.gold
import com.github.seepick.derbauer2.game.logic.Game
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.trading.TradeOperation.Buy
import com.github.seepick.derbauer2.game.trading.TradeOperation.Sell
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class TraderTest : DescribeSpec({
    var game = Game()
    beforeTest {
        game = Game()
    }

    context("Buy") {
        describe("Results") {
            it("When buy infinite resource like gold Then succeed") {
                game.user.addEntity(Gold(0.units))
                Trader(game).trade(
                    TradeRequest(Gold::class, Buy, 1.units),
                ) shouldBeEqual TradeResult.Success
            }
            it("Given enough storage When buy storable Then succeed") {
                game.user.addEntity(Food(0.units))
                game.user.addEntity(Granary(1.units))

                Trader(game).trade(
                    TradeRequest(Food::class, Buy, 1.units),
                ) shouldBeEqual TradeResult.Success
            }
            it("Given no storage When buy Then fail") {
                game.user.addEntity(Food(0.units))

                Trader(game).trade(
                    TradeRequest(Food::class, Buy, 1.units),
                ) shouldBeEqual TradeResult.NotEnoughStorage
            }
        }

        describe("Operation") {
            val buyAmount = 1.units
            it("When buy infinite resource gold Then increased") {
                game.user.addEntity(Gold(0.units))
                Trader(game).trade(TradeRequest(Gold::class, Buy, 1.units))
                game.gold shouldBeEqual 1.units
            }
        }
    }

    context("Sell") {
        describe("Results") {
            it("Given no gold When selling Then fails") {
                game.user.addEntity(Gold(0.units))

                Trader(game).trade(
                    TradeRequest(Gold::class, Sell, 1.units),
                ) shouldBeEqual TradeResult.NotEnoughResources
            }
            it("Given some gold When selling Then succeed") {
                game.user.addEntity(Gold(1.units))

                Trader(game).trade(
                    TradeRequest(Gold::class, Sell, 1.units),
                ) shouldBeEqual TradeResult.Success
            }
        }
        describe("Operation") {
            it("Given no gold When selling Then gold unchanged") {
                game.user.addEntity(Gold(0.units))
                Trader(game).trade(TradeRequest(Gold::class, Sell, 1.units))
                game.gold shouldBe 0.units
            }
            it("Given some gold When selling Then gold changed") {
                game.user.addEntity(Gold(1.units))
                Trader(game).trade(TradeRequest(Gold::class, Sell, 1.units))
                game.gold shouldBe 0.units
            }
        }
    }
})
