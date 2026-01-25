package com.github.seepick.derbauer2.game.logic

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class GameTest : DescribeSpec({
    var game = Game()
    beforeTest { game = Game() }

    describe("turn") {
        it("next turn Then turn increments by 1") {
            val old = game.turn
            game.nextTurn()
            game.turn shouldBeEqual old + 1
        }
    }

})
