package com.github.seepick.derbauer2.game.common

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class UnitsTest : DescribeSpec({
    val singlesAndMagnitude = listOf(
        1L to Magnitude.Single,
        1023L to Magnitude.Single,
        1024L to Magnitude.Kilo,
        1_048_575L to Magnitude.Kilo,
        1_048_576L to Magnitude.Mega,
        1_073_741_823L to Magnitude.Mega,
        1_099_511_627_775L to Magnitude.Giga,
        1_099_511_627_776L to Magnitude.Tera,
        1_125_899_906_842_623L to Magnitude.Tera,
        1_125_899_906_842_624L to Magnitude.Peta,
        1_152_921_504_606_846_975L to Magnitude.Peta,
        1_152_921_504_606_846_976L to Magnitude.Exa,
    )
    context("magnituded units") {
        describe("zero") {
            0.z.magnitutedValue.magnitude shouldBeEqual Magnitude.Single
        }
        describe("positive") {
            withData(singlesAndMagnitude) { (givenSingle: Long, expectedSize: Magnitude) ->
                givenSingle.z.magnitutedValue.magnitude shouldBeEqual expectedSize
            }
        }
        describe("negative") {
            withData(singlesAndMagnitude.map { -it.first to it.second }) { (givenSingle, expectedSize) ->
                givenSingle.zz.magnitutedValue.magnitude shouldBeEqual expectedSize
            }
        }
    }
    describe("toPlusString") {
        it("z unsigned") {
            0.z.toPrefixedString() shouldBeEqual "0"
            1.z.toPrefixedString() shouldBeEqual "+1"
            1024.z.toPrefixedString() shouldBeEqual "+1k"
        }
        it("zz signed") {
            (-1024).zz.toSymboledString() shouldBeEqual "-1k"
            (-1).zz.toSymboledString() shouldBeEqual "-1"
            0.zz.toSymboledString() shouldBeEqual "0"
            1.zz.toSymboledString() shouldBeEqual "+1"
            1024.zz.toSymboledString() shouldBeEqual "+1k"
        }
    }
})
