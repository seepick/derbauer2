package com.github.seepick.derbauer2.game.common

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class RangeTest : DescribeSpec({
    describe("rangeOfMin1To1") {
        it("success") {
            val range = rangeOfMin1To1(
                -1.0 to "a",
                0.0 to "b",
                0.5 to "c",
            )
            listOf(
                -1.0 to "a",
                -0.1 to "a",
                0.0 to "b",
                0.4 to "b",
                0.5 to "c",
                1.0 to "c",
            ).forEach { (number, expected) ->
                range.map(Double11(number)) shouldBeEqual expected
            }
        }
    }
    describe("rangeOf validations") {
        it("empty fails") {
            shouldThrow<IllegalArgumentException> { rangeOf<DoubleAny, Any>() }
        }
        it("unsorted fails") {
            shouldThrow<IllegalArgumentException> { rangeOf(1.0.strictAny to "b", 0.5.strictAny to "a") }
        }
        it("duplicate fails") {
            shouldThrow<IllegalArgumentException> { rangeOf(0.0.strictAny to "a1", 0.0.strictAny to "a2") }
        }
    }
})
