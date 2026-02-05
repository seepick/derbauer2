package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import kotlin.math.ceil

class StarveComputeTest : DescribeSpec({
    describe("howManyStarve") {
        listOf(
            Triple(0, 0, 0),
            Triple(1, 0, 1),
            Triple(5, 0, 5),
            Triple(11, 0, 11),
            Triple(1, 1, 0),
            Triple(10, 1, 0),
            Triple(11, 1, 1),
            Triple(19, 1, 9),
            Triple(20, 1, 10),
            Triple(21, 1, 11),
        ).forEach { (citizen, food, expectedCitizenUnfed) ->
            it("Given 10% eat and $citizen 🙎🏻‍♂️ and $food 🍖 Then $expectedCitizenUnfed unfed") {
                val eatRatio = 10.`%`
                val eaten = ceil(citizen * eatRatio.value).toLong().z
                StarveCompute.howManyUnfed(citizen.z, food.zz, eaten, eatRatio) shouldBeEqual expectedCitizenUnfed.z
            }
        }
        listOf(
            Triple(5, 2, 1), // eat: 2.5 -> ceil up: 3 -> left food: 2 -> 1 starve
            Triple(5, 3, 0),
        ).forEach { (citizen, food, expectedCitizenUnfed) ->
            it("Given 50% eat and $citizen 🙎🏻‍♂️ and $food 🍖 Then $expectedCitizenUnfed unfed") {
                val eatRatio = 50.`%`
                val eaten = ceil(citizen * eatRatio.value).toLong().z
                StarveCompute.howManyUnfed(citizen.z, food.zz, eaten, eatRatio) shouldBeEqual expectedCitizenUnfed.z
            }
        }
    }
})
