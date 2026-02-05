package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.common.Zz
import io.github.oshai.kotlinlogging.KotlinLogging.logger

fun interface ProbInitializer {
    fun initProb()
}

class ProbRegistrator(
    private val registrants: List<ProbInitializer>,
) {
    private val log = logger {}

    fun registerAll() {
        log.debug { "Registering ${registrants.size} probability registrants" }
        registrants.forEach { it.initProb() }
    }
}

interface Probs {
    fun <T> setProvider(key: ProbProviderKey<T>, calculator: ProbCalculator, provider: () -> T)
    fun <T> getProvision(key: ProbProviderKey<T>): T?
    fun <T> setSelector(key: ProbSelectorKey, selector: ProbSelector<T>)
    fun <T> getSelection(key: ProbSelectorKey, items: List<T>): T
    fun setDiffuser(key: ProbDiffuserKey, diffuser: ProbDiffuser)
    fun getDiffused(key: ProbDiffuserKey, baseValue: Zz): Zz
}

class ProbsImpl : Probs {

    private val log = logger {}

    // visible for testing
    val providerHandles = mutableMapOf<ProbProviderKey<*>, ProbProviderHandle<*>>()
    val selectorHandles = mutableMapOf<ProbSelectorKey, ProbSelectorHandle<Any>>()
    val diffuserHandles = mutableMapOf<ProbDiffuserKey, ProbDiffuserHandle>()

    override fun <T> setProvider(
        key: ProbProviderKey<T>,
        calculator: ProbCalculator,
        provider: () -> T,
    ) {
        log.trace { "set $key for ${calculator::class.simpleName}" }
        require(!providerHandles.containsKey(key)) { "Probability source $key is already registered!" }
        providerHandles[key] = ProbProviderHandle(key, calculator, provider)
    }

    override fun <T> getProvision(key: ProbProviderKey<T>): T? {
        val handler = providerHandles[key] ?: error("Provider with key '$key' was not registered!")
        @Suppress("UNCHECKED_CAST")
        return handler.provide() as? T?
    }

    override fun <T> setSelector(key: ProbSelectorKey, selector: ProbSelector<T>) {
        require(!selectorHandles.containsKey(key)) { "Probability source $key is already registered!" }
        @Suppress("UNCHECKED_CAST")
        selectorHandles[key] = ProbSelectorHandle(key, selector) as ProbSelectorHandle<Any>
    }

    override fun <T> getSelection(key: ProbSelectorKey, items: List<T>): T {
        val someHandle = selectorHandles[key] ?: error("Selector $key was not registered!")
        @Suppress("UNCHECKED_CAST")
        val handle = someHandle as? ProbSelectorHandle<T>
            ?: error("Selector $key registered for invalid item type!")
        return handle.selector.select(items)
    }

    override fun setDiffuser(key: ProbDiffuserKey, diffuser: ProbDiffuser) {
        require(!diffuserHandles.containsKey(key)) { "Diffuser source $key is already registered!" }
        diffuserHandles[key] = ProbDiffuserHandle(key, diffuser)
    }

    override fun getDiffused(key: ProbDiffuserKey, baseValue: Zz): Zz {
        val handle = diffuserHandles[key] ?: error("Diffuser $key was not registered!")
        return handle.diffuser.diffuse(baseValue)
    }
}
