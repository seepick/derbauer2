package com.github.seepick.derbauer2.game.logic

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
            0L.units.magnitutedUnits.magnitude shouldBeEqual Magnitude.Single
        }
        describe("positive") {
            withData(singlesAndMagnitude) { (givenSingle: Long, expectedSize: Magnitude) ->
                givenSingle.units.magnitutedUnits.magnitude shouldBeEqual expectedSize
            }
        }
        describe("negative") {
            withData(singlesAndMagnitude.map { (it.first * - 1) to it.second }) { (givenSingle: Long, expectedSize: Magnitude) ->
                givenSingle.units.magnitutedUnits.magnitude shouldBeEqual expectedSize
            }
        }
    }
    // FIXME formatted test (positive and negative)
})
