package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.comparables.shouldBeGreaterThan

class GaussianDiffuserTest : StringSpec({
    val printOutcomes = false
    "adds mean offset when standardDeviation is zero" {
        val mean = 100
        val deviation = 10
        val total = 1000

        val diffuser = GaussianDiffuser(deviation.z)
        val outcomes = (1..total).map { diffuser.diffuse(mean.zz).value }
        if (printOutcomes) {
            outcomes.printOccurences()
        }
        outcomes.all { it in 90..110 }.shouldBeTrue()
        outcomes.count { it in 95..100 } shouldBeGreaterThan outcomes.count { it in 90..95 }
    }
})

fun List<Long>.printOccurences() {
    sorted().associateWith { res -> count { res == it } }.forEach { (number, occurence) ->
        println("$number: $occurence")
    }
}
