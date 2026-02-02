package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import com.github.seepick.derbauer2.game.view.GameOverPage
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeInstanceOf

class GameOverDslTest : DslTest, DescribeSpec() {
    init {
        installDslExtension()
        describe("game over") {
            it("When no citizens Then game over happens") {
                Given {
                    setOwned<Citizen>(0.z)
                } When {
                    nextTurnToGameOver()
                } Then {
                    page.shouldBeInstanceOf<GameOverPage>()
                }
            }
        }
    }
}
