package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual

class TurnDslTest : DslTest, StringSpec() {
    init {
        installDslExtension()
        "When next turn Then turn number increases" {
            Given {
                require(turn == 1)
            } When {
                nextTurn()
            } Then {
                turn shouldBeEqual 2
            }
        }
    }
}
