package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.equals.shouldBeEqual

class DiffuserTests : StringSpec({
    val printOutcomes = true
    fun ProbDiffuser.createSampleSet(baseValue: Zz, sampleSize: Int = 1000): List<Long> {
        val outcomes = (1..sampleSize).map { diffuse(baseValue).value }
        if (printOutcomes) {
            outcomes.printOccurences()
        }
        return outcomes
    }
    context("GaussianDiffuser") {
        "Given mean=100 and dev=10 Then all within range and more near mean" {
            val mean = 100.zz
            val deviation = 10.z

            val outcomes = GaussianDiffuser(deviation).createSampleSet(mean)

            outcomes.all { it in 90..110 }.shouldBeTrue()
            outcomes.count { it in 95..100 } shouldBeGreaterThan outcomes.count { it in 90..95 }
        }
    }
    context("GrowthDiffuser") {
        "Given base=100 and variation=10% Then all within range and more near mean" {
            val baseValue = 100.zz
            val variation = 10.`%`

            val outcomes = GrowthDiffuser(variation).createSampleSet(baseValue)

            outcomes.all { it in 90..110 }.shouldBeTrue()
            outcomes.count { it in 95..100 } shouldBeGreaterThan outcomes.count { it in 90..95 }
        }
        "Given negative base value Then always return 0" {
            val baseValue = (-100).zz
            val variation = 10.`%`

            val outcomes = GrowthDiffuser(variation).createSampleSet(baseValue, sampleSize = 10)

            outcomes.distinct().shouldBeSingleton().first() shouldBeEqual 0
        }
    }
})


fun List<Long>.printOccurences() {
    sorted().associateWith { res -> count { res == it } }.forEach { (number, occurence) ->
        println("$number: $occurence")
    }
}
