package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.GrowthProbCalculator
import com.github.seepick.derbauer2.game.prob.PercentageProbCalculator
import com.github.seepick.derbauer2.game.prob.ProbInitializer
import com.github.seepick.derbauer2.game.prob.ProbProviderKey
import com.github.seepick.derbauer2.game.prob.ProbSelectorKey
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.prob.RandomProbSelector
import io.github.oshai.kotlinlogging.KotlinLogging.logger

val ProbProviderKey.Companion.happeningTurner get() = ProbProviderKey<Happening>("happeningTurner")
val ProbProviderKey.Companion.happeningIsNegative get() = ProbProviderKey<Boolean>("happeningIsNegative")
val ProbSelectorKey.Companion.happeningChoice get() = ProbSelectorKey("happeningChoice")

class HappeningTurner(
    private val user: User,
    private val probs: Probs,
    private val repo: HappeningDescriptorRepo,
) : ProbInitializer {

    private val log = logger {}

    /** Delayed post-ctor initializer; can't do it in init, due to Koin startup complexity and interface lookoup. */
    override fun initProb() {
        log.debug { "Registering probabilities." }
        probs.setProvider(
            ProbProviderKey.happeningTurner,
            GrowthProbCalculator(
                startValue = Mechanics.happeningInitialProb,
                growthRate = Mechanics.happeningGrowthRate,
            )
        ) {
            log.debug { "New happening going to happen." }
            val isNegative = probs.getProvision(ProbProviderKey.happeningIsNegative) != null
            val descriptors = repo.getAllDescriptors().filter {
                (!isNegative || it.nature == HappeningNature.Negative) && it.canHappen(user)
            }
            val descriptor = probs.getSelection(ProbSelectorKey.happeningChoice, descriptors)
            descriptor.buildHappening(user)
        }

        probs.setProvider(
            ProbProviderKey.happeningIsNegative,
            PercentageProbCalculator(
                threshold = Mechanics.happeningIsNegativeChance
            )
        ) { true }

        probs.setSelector<HappeningDescriptor>(ProbSelectorKey.happeningChoice, RandomProbSelector())
    }

    fun buildHappeningMultiPages(): List<Happening> = buildList {
        probs.getProvision(ProbProviderKey.happeningTurner)?.also {
            add(it)
        }
    }
}
