package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.common.StrictDouble
import com.github.seepick.derbauer2.game.common.Zz

class ProbsStub : Probs {

    private val providerHandles: MutableMap<ProbProviderKey<*>, ProbProviderHandle<*>> = mutableMapOf()
    private val selectorHandles: MutableMap<ProbSelectorKey, ProbSelectorHandle<*>> = mutableMapOf()
    private val diffuserHandles: MutableMap<ProbDiffuserKey, ProbDiffuserHandle> = mutableMapOf()
    private val thresholderHandles: MutableMap<ProbThresholderKey, ProbThresholderHandle> = mutableMapOf()

    private val fixatedProviderHandles: MutableMap<ProbProviderKey<*>, ProbProviderHandle<*>> = mutableMapOf()
    private val fixatedSelectorHandles: MutableMap<ProbSelectorKey, ProbSelectorHandle<*>> = mutableMapOf()
    private val fixatedDiffuserHandles: MutableMap<ProbDiffuserKey, ProbDiffuserHandle> = mutableMapOf()
    private val fixatedThresholderHandles: MutableMap<ProbThresholderKey, ProbThresholderHandle> = mutableMapOf()

    override fun <T> setProvider(
        key: ProbProviderKey<T>,
        calculator: ProbCalculator,
        provider: () -> T,
    ) {
        providerHandles[key] = ProbProviderHandle(
            key = key,
            calculator = calculator,
            provider = provider,
        )
    }

    override fun <T> getProvisionOrNull(key: ProbProviderKey<T>): T? {
        val handle = getProviderHandle(key) ?: return null
        return handle.provide()
    }

    override fun <T> setSelector(
        key: ProbSelectorKey,
        selector: ProbSelector<T>,
    ) {
        selectorHandles[key] = ProbSelectorHandle(
            key = key,
            selector = selector,
        )
    }

    override fun <T> getSelectedItem(
        key: ProbSelectorKey,
        items: List<T>,
    ): T {
        val handle = getSelectorHandle<T>(key) ?: error("No selector for key $key")
        return handle.selector.select(items)
    }

    override fun setDiffuser(
        key: ProbDiffuserKey,
        diffuser: ProbDiffuser,
    ) {
        diffuserHandles[key] = ProbDiffuserHandle(
            key = key,
            diffuser = diffuser,
        )
    }

    override fun diffuse(
        key: ProbDiffuserKey,
        baseValue: Zz,
    ): Zz {
        val handle = getDiffuserHandle(key) ?: error("No diffuser for key $key")
        return handle.diffuser.diffuse(baseValue)
    }

    override fun setThresholder(
        key: ProbThresholderKey,
        threshold: () -> StrictDouble.ZeroToOne,
    ) {
        thresholderHandles[key] = ProbThresholderHandle(
            key = key,
            thresholder = FixedProbThresholder(threshold),
        )
    }

    override fun isThresholdReached(key: ProbThresholderKey): Boolean {
        val handle = getThresholderHandle(key) ?: error("No thresholder for key $key")
        return handle.thresholder.nextBoolean()
    }

    private fun <T> getProviderHandle(key: ProbProviderKey<T>): ProbProviderHandle<T>? {
        @Suppress("UNCHECKED_CAST")
        return (fixatedProviderHandles[key] ?: providerHandles[key]) as ProbProviderHandle<T>?
    }

    private fun <T> getSelectorHandle(key: ProbSelectorKey): ProbSelectorHandle<T>? {
        @Suppress("UNCHECKED_CAST")
        return (fixatedSelectorHandles[key] ?: selectorHandles[key]) as ProbSelectorHandle<T>?
    }

    private fun getDiffuserHandle(key: ProbDiffuserKey): ProbDiffuserHandle? {
        return fixatedDiffuserHandles[key] ?: diffuserHandles[key]
    }

    private fun getThresholderHandle(key: ProbThresholderKey): ProbThresholderHandle? {
        return fixatedThresholderHandles[key] ?: thresholderHandles[key]
    }

    // ---------- STUB extensions "manipulative" testing ----------

    fun fixateSelector(
        key: ProbSelectorKey,
        newSelector: ProbSelector<out Any>,
    ) {
        val selectorHandle = selectorHandles[key] ?: error("No selector for key $key")
        fixatedSelectorHandles[key] = selectorHandle.withSelector(newSelector)
    }

    fun fixateProvider(
        key: ProbProviderKey<*>,
        calc: ProbCalculator,
    ) {
        val providerHandle = providerHandles[key] ?: error("No provider for key $key")
        fixatedProviderHandles[key] = providerHandle.copy(calculator = calc)
    }

    fun fixateDiffuser(
        key: ProbDiffuserKey,
        diffuser: ProbDiffuser,
    ) {
        val diffuserHandle = diffuserHandles[key] ?: error("No diffuser for key $key")
        fixatedDiffuserHandles[key] = diffuserHandle.copy(diffuser = diffuser)
    }

    fun fixateThresholder(
        key: ProbThresholderKey,
        thresholder: ProbThresholder,
    ) {
        val thresholderHandle = thresholderHandles[key] ?: error("No thresholder for key $key")
        fixatedThresholderHandles[key] = thresholderHandle.copy(thresholder = thresholder)
    }
}

fun ProbsStub.fixateProvider(key: ProbProviderKey<*>, constantValue: Boolean) {
    fixateProvider(key, AlwaysProbCalculator(constantValue))
}

fun ProbsStub.fixateSelector(key: ProbSelectorKey, selector: ProbSelector<out Any>) {
    fixateSelector(key, selector)
}

fun ProbsStub.fixateSelectorFirst(key: ProbSelectorKey) {
    fixateSelector(key, AlwaysFirstProbSelector())
}

fun ProbsStub.fixateDiffuser(key: ProbDiffuserKey, diffuser: ProbDiffuser) {
    fixateDiffuser(key, diffuser)
}

fun ProbsStub.fixateDiffuserPassthrough(key: ProbDiffuserKey) {
    fixateDiffuser(key, PassThroughDiffuser)
}

/** Using dataclass' copy() didn't work properly due to generics issue. */
fun <T> ProbSelectorHandle<T>.withSelector(newSelector: ProbSelector<out Any>): ProbSelectorHandle<T> {
    @Suppress("UNCHECKED_CAST")
    return ProbSelectorHandle(key, newSelector as ProbSelector<T>)
}
