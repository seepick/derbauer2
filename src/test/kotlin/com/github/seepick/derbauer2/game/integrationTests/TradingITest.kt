package com.github.seepick.derbauer2.game.integrationTests

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.integrationTests.testInfra.Given
import com.github.seepick.derbauer2.game.integrationTests.testInfra.Then
import com.github.seepick.derbauer2.game.integrationTests.testInfra.When
import com.github.seepick.derbauer2.game.integrationTests.testInfra.installGameKoinExtension
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import io.kotest.core.spec.style.DescribeSpec
import org.koin.test.KoinTest

class TradingITest : KoinTest, DescribeSpec() {
    init {
        installGameKoinExtension()
        describe("When buy resource") {
            it("Given no gold Then warn") {
                Given {
                    setOwned<Gold>(0.z)
                    setOwned<Food>(0.z)
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
