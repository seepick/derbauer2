package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.turn.Reports
import com.github.seepick.derbauer2.game.turn.ReportsImpl
import com.github.seepick.derbauer2.game.turn.TurnReport
import com.github.seepick.derbauer2.game.turn.empty
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next

class FoodMerchantFeatureTest : StringSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }
    fun foodTradeWithAnyAmount() =
        TradeRequest(Food::class, Arb.int(-100..100).next().zz)

    fun reportWithFoodTrade(tradingCount: Int) = TurnReport.empty().copy(
        actions = List(tradingCount) {
            ResourcesTradedAction(foodTradeWithAnyAmount())
        }
    )

    "Insufficient trades Then check fails" {
        val reports = Reports(
            List(Mechanics.featureFoodMerchantThresholdFoodTradesAmountTimes) {
                reportWithFoodTrade(Mechanics.featureFoodMerchantThresholdFoodTradesAmount - 1)
            }
        )
        FoodMerchantFeature.Ref.check(user, reports) shouldBe false
    }

    "Many but not sufficient trades Then check fails" {
        val reports = Reports(TurnReport.empty())
        FoodMerchantFeature.Ref.check(user, reports) shouldBe false
    }
    "Sufficient trades Then check passes" {
        val reports = Reports(
            List(Mechanics.featureFoodMerchantThresholdFoodTradesAmountTimes) {
                reportWithFoodTrade(Mechanics.featureFoodMerchantThresholdFoodTradesAmount)
            }
        )

        FoodMerchantFeature.Ref.check(user, reports) shouldBe true
    }
})

operator fun Reports.Companion.invoke(vararg givens: TurnReport) = ReportsImpl().apply {
    givens.forEach { add(it) }
}

operator fun Reports.Companion.invoke(givens: List<TurnReport>) = ReportsImpl().apply {
    givens.forEach { add(it) }
}
