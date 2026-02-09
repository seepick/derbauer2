package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.turn.ReportsImpl
import com.github.seepick.derbauer2.game.turn.TurnReport
import com.github.seepick.derbauer2.game.turn.empty
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next

class FoodMerchantFeatureTest : StringSpec({
    val threshold = 100
    lateinit var user: User
    beforeTest {
        user = User()
    }
    fun foodTrade() = TradeRequest(Food::class, Arb.int(-100..100).next().zz)

    fun reportWithFoodTrade(tradingCount: Int) = TurnReport.empty().copy(
        actions = List(tradingCount) {
            ResourcesTradedAction(foodTrade())
        }
    )

    "Insufficient trades Then check fails" {
        val reports = ReportsImpl().apply {
            add(reportWithFoodTrade(threshold - 1))
        }
        FoodMerchantFeature.Descriptor.check(user, reports) shouldBe false
    }
    "Sufficient trades Then check passes" {
        val reports = ReportsImpl().apply {
            add(reportWithFoodTrade(threshold - 1))
            add(reportWithFoodTrade(1))
        }
        FoodMerchantFeature.Descriptor.check(user, reports) shouldBe true
    }
})
