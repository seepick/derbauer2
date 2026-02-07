package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.happening.happenings.HappeningDescriptorRepo
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

    init {
        require(repo.getAllDescriptors().isNotEmpty()) { "At least one happening descriptor must be registered." }
    }

    /** Delayed post-ctor initializer; can't do it in init, due to Koin startup complexity and interface lookoup. */
    override fun initProb() {
        log.debug { "Registering probabilities." }
        repo.getAllDescriptors().forEach {
            it.initProb(probs, user)
        }
        probs.setProvider(
            ProbProviderKey.happeningTurner,
            GrowthProbCalculator(
                startValue = 0.`%`,
                growthRate = Mechanics.happeningGrowthRate,
            )
        ) {
            log.debug { "New happening going to happen..." }
            val isNegative = probs.getProvision(ProbProviderKey.happeningIsNegative) != null

            val canDescriptors = repo.getAllDescriptors().filter { it.canHappen(user, probs) }
            val filteredCanDescriptors = canDescriptors.filter {
                if (isNegative) {
                    it.nature == HappeningNature.Negative
                } else {
                    it.nature != HappeningNature.Negative
                } && it.willHappen(user, probs)
            }
            val ensuredDescriptors = filteredCanDescriptors.ifEmpty { canDescriptors }
            val descriptor = probs.getSelection(ProbSelectorKey.happeningChoice, ensuredDescriptors)
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

    fun maybeHappening(): Happening? =
        probs.getProvision(ProbProviderKey.happeningTurner)
}
