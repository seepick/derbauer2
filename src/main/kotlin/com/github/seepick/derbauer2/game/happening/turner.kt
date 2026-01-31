package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.happening.happenings.HappeningDescriptor
import com.github.seepick.derbauer2.game.happening.happenings.HappeningDescriptorRepo
import com.github.seepick.derbauer2.game.probability.GrowthProbabilityCalculator
import com.github.seepick.derbauer2.game.probability.PercentageProbabilityCalculator
import com.github.seepick.derbauer2.game.probability.Probabilities
import com.github.seepick.derbauer2.game.probability.ProbabilityProviderSource
import com.github.seepick.derbauer2.game.probability.ProbabilitySelectorSource
import com.github.seepick.derbauer2.game.probability.RandomProbabilitySelector
import io.github.oshai.kotlinlogging.KotlinLogging.logger

class HappeningTurner(
    private val user: User,
    private val probabilities: Probabilities,
    repo: HappeningDescriptorRepo,
) {
    private val log = logger {}

    init {
        probabilities.setProvider(
            ProbabilityProviderSource.HappeningTurner,
            GrowthProbabilityCalculator(
                startValue = Mechanics.turnProbHappeningStart,
                growthRate = Mechanics.turnProbHappeningGrowthRate,
            )
        ) {
            log.debug { "New happening going to happen." }
            val isNegative = probabilities.getProvision(ProbabilityProviderSource.HappeningIsNeg) != null
            val descriptors = repo.all.filter {
                (!isNegative || it.nature == HappeningNature.Negative) && it.canHappen(user)
            }
            val descriptor = probabilities.getSelection(ProbabilitySelectorSource.Happenings, descriptors)
            descriptor.build(user)
        }

        probabilities.setProvider(
            ProbabilityProviderSource.HappeningIsNeg,
            PercentageProbabilityCalculator(
                threshold = Mechanics.turnProbHappeningIsNegative
            )
        ) {}

        probabilities.setSelector<HappeningDescriptor>(
            ProbabilitySelectorSource.Happenings,
            RandomProbabilitySelector()
        )
    }


    fun buildHappeningMultiPages(): List<Happening> = buildList {
        (probabilities.getProvision(ProbabilityProviderSource.HappeningTurner) as? Happening)?.let { add(it) }
    }
}
