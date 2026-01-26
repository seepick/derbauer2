package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.random.ProbabilityProvider
import com.github.seepick.derbauer2.game.logic.random.addMaybe
import kotlin.random.Random

class HappeningTurner(
    private val user: User,
) {
    private val prob = ProbabilityProvider(
        startValue = 0.0,
        growthRate = 0.02,
    ) {
        val isNegative = Random.nextDouble(0.0, 1.0) < 0.10 // TODO influence by luck
        HappeningDescriptor.all.filter {
            if (isNegative) it.nature == HappeningNature.Negative
            else true
        }.random().build(user)
    }

    fun turn(): List<Happening> = buildList {
        addMaybe(prob)
    }
}
