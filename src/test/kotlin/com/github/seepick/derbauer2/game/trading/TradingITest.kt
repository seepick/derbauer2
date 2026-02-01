package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.testInfra.itest.Given
import com.github.seepick.derbauer2.game.testInfra.itest.ITest
import com.github.seepick.derbauer2.game.testInfra.itest.Then
import com.github.seepick.derbauer2.game.testInfra.itest.When
import com.github.seepick.derbauer2.game.testInfra.itest.installGameKoinExtension
import io.kotest.core.spec.style.DescribeSpec

class TradingITest : ITest, DescribeSpec() {
    init {
        installGameKoinExtension()
        describe("When buy resource") {
            it("Given no gold Then warn") {
                Given {
                    setOwned<Gold>(0.z)
                    setOwned<Food>(0.z)
                    user.enable(TradingFeature())
                } When {
                    selectPrompt("trade")
                    selectPrompt("buy 1 🍖")
                } Then {
                    shouldRaiseWarning("insufficient resources")
                }
            }
        }
    }
}
