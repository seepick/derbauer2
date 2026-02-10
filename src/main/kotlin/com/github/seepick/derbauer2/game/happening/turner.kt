package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.happening.happenings.HappeningRefRegistry
import com.github.seepick.derbauer2.game.prob.GrowthProbCalculator
import com.github.seepick.derbauer2.game.prob.PercentageProbCalculator
import com.github.seepick.derbauer2.game.prob.ProbInitializer
import com.github.seepick.derbauer2.game.prob.ProbProviderKey
import com.github.seepick.derbauer2.game.prob.ProbSelectorKey
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.prob.RandomProbSelector
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import io.github.oshai.kotlinlogging.KotlinLogging.logger

val ProbProviderKey.Companion.happeningTurner get() = ProbProviderKey<Happening>("happeningTurner")
val ProbProviderKey.Companion.happeningIsNegative get() = ProbProviderKey<Boolean>("happeningIsNegative")
val ProbSelectorKey.Companion.happeningChoice get() = ProbSelectorKey("happeningChoice")

class HappeningTurner(
    private val user: User,
    private val probs: Probs,
    private val repo: HappeningRefRegistry,
    private val currentTurn: CurrentTurn,
) : ProbInitializer {

    private val log = logger {}

    init {
        require(repo.all.isNotEmpty()) { "At least one happening descriptor must be registered." }
    }

    /** Delayed post-ctor initializer; can't do it in init, due to Koin startup complexity and interface lookoup. */
    override fun initProb() {
        log.debug { "Registering probabilities." }
        repo.all.forEach {
            it.initProb(probs, user, currentTurn)
        }
        probs.setProvider(
            ProbProviderKey.happeningTurner,
            GrowthProbCalculator(
                startValue = 0.`%`,
                growthRate = Mechanics.happeningGrowthRate,
            )
        ) {
            log.debug { "New happening going to happen..." }
            val isNegative = probs.provisionOrNull(ProbProviderKey.happeningIsNegative) != null

            val canDescriptors = repo.all.filter { it.canHappen(user, probs) }
            val filteredCanDescriptors = canDescriptors.filter {
                if (isNegative) {
                    it.nature == HappeningNature.Negative
                } else {
                    it.nature != HappeningNature.Negative
                } && it.willHappen(user, probs)
            }
            val ensuredDescriptors = filteredCanDescriptors.ifEmpty { canDescriptors }
            val descriptor = probs.selectItem(ProbSelectorKey.happeningChoice, ensuredDescriptors)
            descriptor.buildHappening(user)
        }

        probs.setProvider(
            ProbProviderKey.happeningIsNegative,
            PercentageProbCalculator(
                threshold = Mechanics.happeningIsNegativeChance
            )
        ) { true }

        probs.setSelector<HappeningRef>(ProbSelectorKey.happeningChoice, RandomProbSelector())
    }

    fun maybeHappening(): Happening? =
        probs.provisionOrNull(ProbProviderKey.happeningTurner)
}
