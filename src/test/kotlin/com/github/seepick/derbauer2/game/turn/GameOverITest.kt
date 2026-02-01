package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.testInfra.itest.Given
import com.github.seepick.derbauer2.game.testInfra.itest.ITest
import com.github.seepick.derbauer2.game.testInfra.itest.Then
import com.github.seepick.derbauer2.game.testInfra.itest.When
import com.github.seepick.derbauer2.game.testInfra.itest.installGameKoinExtension
import com.github.seepick.derbauer2.game.view.GameOverPage
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeInstanceOf

class GameOverITest : ITest, DescribeSpec() {
    init {
        installGameKoinExtension()
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
