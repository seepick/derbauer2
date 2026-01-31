package com.github.seepick.derbauer2.game.probability

import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.Percent
import com.github.seepick.derbauer2.game.common.compareTo
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlin.random.Random

// TODO cleanup file

interface ProbabilityManager {
    fun provider(
        source: ProbabilityProviderSource,
        calculator: ProbabilityCalculator,
        provider: () -> Any
    )

    fun provideIt(source: ProbabilityProviderSource): Any?

    fun <T> selector(
        source: ProbabilitySelectorSource,
        selector: ProbabilitySelector<T>
    )

    fun <T> selectIt(source: ProbabilitySelectorSource, items: List<T>): T

}

fun interface ProbabilitySelector<T> {
    fun select(items: List<T>): T
}

open class AlwaysSameProbabilitySelector<T>(val index: Int) : ProbabilitySelector<T> {
    override fun select(items: List<T>) = items[index]!!
}

class AlwaysFirstProbabilitySelector<T> : AlwaysSameProbabilitySelector<T>(index = 0)

class RandomProbabilitySelector<T> : ProbabilitySelector<T> {
    override fun select(items: List<T>) = items.random()
}

data class ProbabilityProviderProvider<T>(
    private val source: ProbabilityProviderSource,
    private val calculator: ProbabilityCalculator,
    private val provider: () -> T,
) {

    private val log = logger {}

    fun provide(): T? =
        if (calculator.nextBoolean()) {
            log.debug { "${this::class.simpleName} probability hit for: $source" }
            provider()
        } else {
            null
        }
}

data class ProbabilitySelectorSelector<T>(
    val source: ProbabilitySelectorSource,
    val selector: ProbabilitySelector<T>,
) {
    fun withSelector(newSelector: ProbabilitySelector<out Any>): ProbabilitySelectorSelector<T> {
        @Suppress("UNCHECKED_CAST")
        return ProbabilitySelectorSelector(source, newSelector as ProbabilitySelector<T>)
    }
}

/** To identify for tests. */
enum class ProbabilityProviderSource {
    HappeningTurner,
    HappeningIsNeg,
}

/** To identify for tests. */
enum class ProbabilitySelectorSource {
    Happenings,
}

fun interface ProbabilityCalculator {
    fun nextBoolean(): Boolean
}

class AlwaysProbabilityCalculator(private val always: Boolean) : ProbabilityCalculator {
    override fun nextBoolean() = always
}

class PercentageProbabilityCalculator(
    /** Will be true if: random < threshold */
    private val threshold: Percent,
) : ProbabilityCalculator {
    override fun nextBoolean(): Boolean {
        return Random.nextDouble(0.0, 1.0) < threshold
    }
}

class GrowthProbabilityCalculator(
    startValue: Percent,
    private val growthRate: Percent,
) : ProbabilityCalculator {

    init {
        require(startValue >= 0.0) { "startValue must be >= 0.0 but was: $startValue" }
        require(growthRate > 0.0) { "growthRate must be > 0.0 but was: $growthRate" }
    }

    private var currentValue = startValue

    override fun nextBoolean(): Boolean {
        if (Random.nextDouble(0.0, 1.0) <= currentValue) {
            currentValue = 0.0.`%` // reset
            return true
        }
        currentValue += growthRate
        if (currentValue > 1.0) currentValue = 1.0.`%`
        return false
    }
}

class ProbabilityManagerImpl : ProbabilityManager {

    // visible for testing
    val providers = mutableMapOf<ProbabilityProviderSource, ProbabilityProviderProvider<Any>>()
    val selectors = mutableMapOf<ProbabilitySelectorSource, ProbabilitySelectorSelector<Any>>()

    override fun provider(
        source: ProbabilityProviderSource,
        calculator: ProbabilityCalculator,
        provider: () -> Any,
    ) {
        println("set $source for ${calculator::class.simpleName}")
        require(!providers.containsKey(source)) { "Probability source $source is already registered!" }
        providers[source] = ProbabilityProviderProvider(source, calculator, provider)
    }

    override fun provideIt(source: ProbabilityProviderSource): Any? {
        val handler = providers[source] ?: error("Provider $source was not registered!")
        return handler.provide()
    }

    override fun <T> selector(
        source: ProbabilitySelectorSource,
        selector: ProbabilitySelector<T>
    ) {
        require(!selectors.containsKey(source)) { "Probability source $source is already registered!" }
        @Suppress("UNCHECKED_CAST")
        selectors[source] = ProbabilitySelectorSelector(source, selector) as ProbabilitySelectorSelector<Any>
    }

    override fun <T> selectIt(source: ProbabilitySelectorSource, items: List<T>): T {
        val someSelector = selectors[source] ?: error("Selector $source was not registered!")
        @Suppress("UNCHECKED_CAST")
        val selector =
            someSelector as? ProbabilitySelectorSelector<T>
                ?: error("Selector $source registered for invalid item type!")
        return selector.selector.select(items)
    }
}
