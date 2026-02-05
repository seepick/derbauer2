package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.turn.Season
import com.github.seepick.derbauer2.game.turn.Turn
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class TurnTest : DescribeSpec({
    describe("toFullInfo") {
        it("all") {
            Turn(1).prettyString shouldBe "${Season.Spring.emoji}  W1 Y1"
            Turn(14 + 52).prettyString shouldBe "${Season.Summer.emoji}  W14 Y2"
            Turn(27).prettyString shouldBe "${Season.Autumn.emoji}  W27 Y1"
            Turn(40).prettyString shouldBe "${Season.Winter.emoji}  W40 Y1"
        }
    }
    describe("values") {
        it("week") {
            Turn(1).week shouldBe 1
            Turn(2).week shouldBe 2
            Turn(52).week shouldBe 52
            Turn(53).week shouldBe 1
        }
        it("year") {
            Turn(1).year shouldBe 1
            Turn(2).year shouldBe 1
            Turn(52).year shouldBe 1
            Turn(53).year shouldBe 2
        }
        it("season") {
            Turn(1).season shouldBe Season.Spring
            Turn(14).season shouldBe Season.Summer
            Turn(27).season shouldBe Season.Autumn
            Turn(40).season shouldBe Season.Winter
            Turn(53).season shouldBe Season.Spring
        }
    }
})
