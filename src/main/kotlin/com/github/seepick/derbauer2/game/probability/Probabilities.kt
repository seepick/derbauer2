package com.github.seepick.derbauer2.game.probability

import io.github.oshai.kotlinlogging.KotlinLogging.logger

fun interface ProbabilityRegistrant {
    fun registerProbabilities()
}

class ProbabilityInitializer(
    private val registrants: List<ProbabilityRegistrant>,
) {
    private val log = logger {}

    fun registerAll() {
        log.debug { "Registering ${registrants.size} probability registrants" }
        registrants.forEach { it.registerProbabilities() }
    }
}

interface Probabilities {
    fun setProvider(source: ProbabilityProviderSource, calculator: ProbabilityCalculator, provider: () -> Any)
    fun getProvision(source: ProbabilityProviderSource): Any?
    fun <T> setSelector(source: ProbabilitySelectorSource, selector: ProbabilitySelector<T>)
    fun <T> getSelection(source: ProbabilitySelectorSource, items: List<T>): T
}

class ProbabilitiesImpl : Probabilities {

    private val log = logger {}

    // visible for testing
    val providerHandles = mutableMapOf<ProbabilityProviderSource, ProbabilityProviderHandle<Any>>()
    val selectorHandles = mutableMapOf<ProbabilitySelectorSource, ProbabilitySelectorHandle<Any>>()

    override fun setProvider(
        source: ProbabilityProviderSource,
        calculator: ProbabilityCalculator,
        provider: () -> Any,
    ) {
        log.trace { "set $source for ${calculator::class.simpleName}" }
        require(!providerHandles.containsKey(source)) { "Probability source $source is already registered!" }
        providerHandles[source] = ProbabilityProviderHandle(source, calculator, provider)
    }

    override fun getProvision(source: ProbabilityProviderSource): Any? {
        val handler = providerHandles[source] ?: error("Provider $source was not registered!")
        return handler.provide()
    }

    override fun <T> setSelector(
        source: ProbabilitySelectorSource,
        selector: ProbabilitySelector<T>
    ) {
        require(!selectorHandles.containsKey(source)) { "Probability source $source is already registered!" }
        @Suppress("UNCHECKED_CAST")
        selectorHandles[source] = ProbabilitySelectorHandle(source, selector) as ProbabilitySelectorHandle<Any>
    }

    override fun <T> getSelection(source: ProbabilitySelectorSource, items: List<T>): T {
        val someHandle = selectorHandles[source] ?: error("Selector $source was not registered!")
        @Suppress("UNCHECKED_CAST")
        val handle = someHandle as? ProbabilitySelectorHandle<T>
            ?: error("Selector $source registered for invalid item type!")
        return handle.selector.select(items)
    }
}
