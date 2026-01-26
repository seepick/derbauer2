package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.gold
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.zp
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.trading.TradeOperation.Buy
import com.github.seepick.derbauer2.game.trading.TradeOperation.Sell
import com.github.seepick.derbauer2.game.transaction.TxResult
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class TraderTest : DescribeSpec({
    var user = User()
    beforeTest {
        user = User()
    }

    context("Buy") {
        describe("Results") {
            it("When buy infinite resource like gold Then succeed") {
                user.add(Gold(0.zp))
                user.trade(
                    TradeRequest(Gold::class, Buy, 1.zp),
                ) shouldBeEqual TxResult.Success
            }
            it("Given enough storage When buy storable Then succeed") {
                user.add(Food(0.zp))
                user.add(Granary(1.zp))

                user.trade(
                    TradeRequest(Food::class, Buy, 1.zp),
                ) shouldBeEqual TxResult.Success
            }
            it("Given no storage When buy Then fail") {
                user.add(Food(0.zp))

                user.trade(
                    TradeRequest(Food::class, Buy, 1.zp),
                ).shouldBeInstanceOf<TxResult.Fail.InsufficientResources>()
            }
        }

        describe("Operation") {
            val buyAmount = 1.zp
            it("When buy infinite resource gold Then increased") {
                user.add(Gold(0.zp))
                user.trade(TradeRequest(Gold::class, Buy, 1.zp))
                user.gold shouldBeEqual 1.zp
            }
        }
    }

    context("Sell") {
        describe("Results") {
            it("Given no gold When selling Then fails") {
                user.add(Gold(0.zp))

                user.trade(
                    TradeRequest(Gold::class, Sell, 1.zp),
                ).shouldBeInstanceOf<TxResult.Fail.InsufficientResources>()
            }
            it("Given some gold When selling Then succeed") {
                user.add(Gold(1.zp))

                user.trade(
                    TradeRequest(Gold::class, Sell, 1.zp),
                ) shouldBeEqual TxResult.Success
            }
        }
        describe("Operation") {
            it("Given no gold When selling Then gold unchanged") {
                user.add(Gold(0.zp))
                user.trade(TradeRequest(Gold::class, Sell, 1.zp))
                user.gold shouldBe 0.zp
            }
            it("Given some gold When selling Then gold changed") {
                user.add(Gold(1.zp))
                user.trade(TradeRequest(Gold::class, Sell, 1.zp))
                user.gold shouldBe 0.zp
            }
        }
    }
})
