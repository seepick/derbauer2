package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.happening.happenings.HappeningDescriptor
import com.github.seepick.derbauer2.game.random.ProbabilityProvider
import com.github.seepick.derbauer2.game.random.addRandomIfNotNull
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.random.Random

class HappeningTurner(
    private val user: User,
) {
    private val log = logger {}
    private val probability = ProbabilityProvider(
        startValue = 0.0,
        growthRate = 0.02,
    ) {
        log.debug { "New happening going to happen." }
        val isNegative = Random.nextDouble(0.0, 1.0) < Mechanics.turnProbHappeningIsNegative
        val descriptor = HappeningDescriptor.all.filter {
            (!isNegative || it.nature == HappeningNature.Negative) && it.canHappen(user)
        }.random()
        descriptor.build(user)
    }

    fun buildHappeningMultiPages(): List<Happening> = buildList {
        addRandomIfNotNull(probability)
    }
}
