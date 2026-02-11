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
    private val happenings: HappeningRefRegistry,
    private val currentTurn: CurrentTurn,
) : ProbInitializer {

    private val log = logger {}
    private val happeningTurnCalculator = GrowthProbCalculator(
        startValue = 0.`%`,
        growthRate = Mechanics.happeningGrowthRate,
    )

    init {
        require(happenings.all.isNotEmpty()) { "At least one happening ref must be registered." }
    }

    /** Delayed post-ctor initializer; can't do it in init, due to Koin startup complexity and interface lookoup. */
    override fun initProb() {
        log.debug { "Registering probabilities." }
        happenings.all.forEach {
            // delegate to children
            it.initProb(probs, user, currentTurn)
        }
        probs.setProvider(
            key = ProbProviderKey.happeningTurner,
            calculator = happeningTurnCalculator,
            provider = ::provideHappening,
        )
        probs.setProvider(
            key = ProbProviderKey.happeningIsNegative,
            calculator = PercentageProbCalculator(Mechanics.happeningIsNegativeChance),
            provider = { true },
        )
        probs.setSelector<HappeningRef>(
            key = ProbSelectorKey.happeningChoice,
            selector = RandomProbSelector(),
        )
    }

    fun maybeHappening(): Happening? =
        probs.provisionOrNull(ProbProviderKey.happeningTurner)

    private fun provideHappening(): Happening {
        val canHappenHappenings = happenings.all.filter { it.canHappen(user, probs) }
        val filterIsNegative = probs.provisionOrNull(ProbProviderKey.happeningIsNegative) != null
        val willHappenHappenings = canHappenHappenings.filter {
            if (filterIsNegative) {
                it.nature == HappeningNature.Negative
            } else {
                it.nature != HappeningNature.Negative
            } && it.willHappen(user, probs)
        }
        val someHappenings = willHappenHappenings.ifEmpty { canHappenHappenings }
        val ref = probs.selectItem(ProbSelectorKey.happeningChoice, someHappenings)
        return ref.buildHappening(user)
    }
}
