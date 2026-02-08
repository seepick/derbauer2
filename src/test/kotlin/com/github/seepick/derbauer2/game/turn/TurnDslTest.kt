package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.testInfra.DslTest
import com.github.seepick.derbauer2.game.testInfra.dsl.Given
import com.github.seepick.derbauer2.game.testInfra.dsl.Then
import com.github.seepick.derbauer2.game.testInfra.dsl.When
import com.github.seepick.derbauer2.game.testInfra.installDslExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

class TurnDslTest : DslTest, FunSpec() {
    init {
        installDslExtension()
        test("When next turn Then turn number increases") {
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
