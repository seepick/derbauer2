package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.addBuilding
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.ActionBusStub
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.addResource
import com.github.seepick.derbauer2.game.resource.gold
import com.github.seepick.derbauer2.game.trading.TradeOperation.Buy
import com.github.seepick.derbauer2.game.trading.TradeOperation.Sell
import com.github.seepick.derbauer2.game.transaction.DefaultTxValidatorRegistry
import com.github.seepick.derbauer2.game.transaction.TxResult
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class TradingServiceTest : DescribeSpec({
    lateinit var user: User
    lateinit var service: TradingService
    beforeTest {
        user = User(DefaultTxValidatorRegistry.validators)
        service = TradingService(user, ActionBusStub())
    }

    context("Buy") {
        describe("Results") {
            it("When buy infinite resource like gold Then succeed") {
                user.add(Gold())
                service.trade(
                    TradeSingleRequest(Gold::class, Buy, 1.z),
                ) shouldBeEqual TxResult.Success
            }
            it("Given enough storage When buy storable Then succeed") {
                user.add(Food())
                user.addBuilding(Granary(), 1.z)

                service.trade(
                    TradeSingleRequest(Food::class, Buy, 1.z),
                ) shouldBeEqual TxResult.Success
            }
            it("Given no storage When buy Then fail") {
                user.add(Food())

                service.trade(
                    TradeSingleRequest(Food::class, Buy, 1.z),
                ).shouldBeInstanceOf<TxResult.Fail.InsufficientResources>()
            }
        }

        describe("Operation") {
            it("When buy infinite resource gold Then increased") {
                user.add(Gold())
                service.trade(TradeSingleRequest(Gold::class, Buy, 1.z))
                user.gold shouldBeEqual 1.z
            }
        }
    }

    context("Sell") {
        describe("Results") {
            it("Given no gold When selling Then fails") {
                user.add(Gold())

                service.trade(
                    TradeSingleRequest(Gold::class, Sell, 1.z),
                ).shouldBeInstanceOf<TxResult.Fail.InsufficientResources>()
            }
            it("Given some gold When selling Then succeed") {
                user.addResource(Gold(), 1.z)

                service.trade(
                    TradeSingleRequest(Gold::class, Sell, 1.z),
                ) shouldBeEqual TxResult.Success
            }
        }
        describe("Operation") {
            it("Given no gold When selling Then gold unchanged") {
                user.add(Gold())
                service.trade(TradeSingleRequest(Gold::class, Sell, 1.z))
                user.gold shouldBe 0.z
            }
            it("Given some gold When selling Then gold changed") {
                user.addResource(Gold(), 1.z)
                service.trade(TradeSingleRequest(Gold::class, Sell, 1.z))
                user.gold shouldBe 0.z
            }
        }
    }
})
