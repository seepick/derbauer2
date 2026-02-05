package com.github.seepick.derbauer2.game.common

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PercentTest : DescribeSpec({
    describe("neededToGetTo") {
        it("ok") {
            10.`%`.neededToGetTo(1) shouldBe 10.z
            30.`%`.neededToGetTo(1) shouldBe 4.z // 3.33.. -> 4
            50.`%`.neededToGetTo(1) shouldBe 2.z
            100.`%`.neededToGetTo(1) shouldBe 1.z
        }
        it("nok") {
            shouldThrow<IllegalArgumentException> { 0.`%`.neededToGetTo(1) }
        }
    }
})
