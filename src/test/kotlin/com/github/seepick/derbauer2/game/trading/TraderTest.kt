package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.enableAndSet
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.gold
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.enableAndSet
import com.github.seepick.derbauer2.game.trading.TradeOperation.Buy
import com.github.seepick.derbauer2.game.trading.TradeOperation.Sell
import com.github.seepick.derbauer2.game.transaction.TxResult
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class TraderTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    context("Buy") {
        describe("Results") {
            it("When buy infinite resource like gold Then succeed") {
                user.enable(Gold())
                user.trade(
                    TradeRequest(Gold::class, Buy, 1.z),
                ) shouldBeEqual TxResult.Success
            }
            it("Given enough storage When buy storable Then succeed") {
                user.enable(Food())
                user.enableAndSet(Granary(), 1.z)

                user.trade(
                    TradeRequest(Food::class, Buy, 1.z),
                ) shouldBeEqual TxResult.Success
            }
            it("Given no storage When buy Then fail") {
                user.enable(Food())

                user.trade(
                    TradeRequest(Food::class, Buy, 1.z),
                ).shouldBeInstanceOf<TxResult.Fail.InsufficientResources>()
            }
        }

        describe("Operation") {
            it("When buy infinite resource gold Then increased") {
                user.enable(Gold())
                user.trade(TradeRequest(Gold::class, Buy, 1.z))
                user.gold shouldBeEqual 1.z
            }
        }
    }

    context("Sell") {
        describe("Results") {
            it("Given no gold When selling Then fails") {
                user.enable(Gold())

                user.trade(
                    TradeRequest(Gold::class, Sell, 1.z),
                ).shouldBeInstanceOf<TxResult.Fail.InsufficientResources>()
            }
            it("Given some gold When selling Then succeed") {
                user.enableAndSet(Gold(), 1.z)

                user.trade(
                    TradeRequest(Gold::class, Sell, 1.z),
                ) shouldBeEqual TxResult.Success
            }
        }
        describe("Operation") {
            it("Given no gold When selling Then gold unchanged") {
                user.enable(Gold())
                user.trade(TradeRequest(Gold::class, Sell, 1.z))
                user.gold shouldBe 0.z
            }
            it("Given some gold When selling Then gold changed") {
                user.enableAndSet(Gold(), 1.z)
                user.trade(TradeRequest(Gold::class, Sell, 1.z))
                user.gold shouldBe 0.z
            }
        }
    }
})
