package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.common.StrictDouble
import com.github.seepick.derbauer2.game.common.Zz

@Suppress("ClassOrdering")
class ProbsStub : Probs {

    // ------------------------------ item PROVIDER ------------------------------

    private val providerHandles: MutableMap<ProbProviderKey<*>, ProbProviderHandle<*>> = mutableMapOf()
    private val fixatedProviders: MutableMap<ProbProviderKey<*>, ProbCalculator> = mutableMapOf()

    override fun <T> setProvider(key: ProbProviderKey<T>, calculator: ProbCalculator, provider: () -> T) {
        providerHandles[key] = ProbProviderHandle(key, calculator, provider)
    }

    override fun <T> provisionOrNull(key: ProbProviderKey<T>): T? {
        val handler = providerHandles[key] ?: error("No provider for key $key")
        val calculator = fixatedProviders[key] ?: handler.calculator
        return if (calculator.nextBoolean()) {
            @Suppress("UNCHECKED_CAST")
            handler.provider() as T
        } else {
            null
        }
    }

    fun fixateProvider(key: ProbProviderKey<*>, calc: ProbCalculator) {
        fixatedProviders[key] = calc
    }

    // ------------------------------ items SELECTOR ------------------------------

    private val selectorHandles: MutableMap<ProbSelectorKey, ProbSelectorHandle<*>> = mutableMapOf()
    private val fixatedSelectors: MutableMap<ProbSelectorKey, ProbSelector<*>> = mutableMapOf()

    override fun <T> setSelector(key: ProbSelectorKey, selector: ProbSelector<T>) {
        selectorHandles[key] = ProbSelectorHandle(key, selector)
    }

    override fun <T> selectItem(key: ProbSelectorKey, items: List<T>): T {
        val handle = selectorHandles[key] ?: error("No selector for key $key")
        @Suppress("UNCHECKED_CAST")
        val selector = (fixatedSelectors[key] ?: handle.selector) as ProbSelector<T>
        return selector.select(items)
    }

    fun fixateSelector(key: ProbSelectorKey, newSelector: ProbSelector<out Any>) {
        fixatedSelectors[key] = newSelector
    }

    // ------------------------------ value DIFFUSER ------------------------------

    private val diffuserHandles: MutableMap<ProbDiffuserKey, ProbDiffuserHandle> = mutableMapOf()
    private val fixatedDiffusers: MutableMap<ProbDiffuserKey, ProbDiffuser> = mutableMapOf()

    override fun setDiffuser(key: ProbDiffuserKey, diffuser: ProbDiffuser) {
        diffuserHandles[key] = ProbDiffuserHandle(key, diffuser)
    }

    override fun diffuse(key: ProbDiffuserKey, baseValue: Zz): Zz {
        val handle = diffuserHandles[key] ?: error("No diffuser for key $key")
        @Suppress("UNCHECKED_CAST")
        val diffuser = (fixatedDiffusers[key] ?: handle.diffuser)
        return diffuser.diffuse(baseValue)
    }

    fun fixateDiffuser(key: ProbDiffuserKey, diffuser: ProbDiffuser) {
        fixatedDiffusers[key] = diffuser
    }

    // ------------------------------ value THRESHOLD ------------------------------

    private val thresholderHandles = mutableMapOf<ProbThresholderKey, ProbThresholderHandle>()
    private val fixatedThresholders = mutableMapOf<ProbThresholderKey, ProbThresholder>()

    override fun setThresholder(key: ProbThresholderKey, threshold: () -> StrictDouble.ZeroToOne) {
        thresholderHandles[key] = ProbThresholderHandle(key, FixedProbThresholder(threshold))
    }

    override fun isThresholdReached(key: ProbThresholderKey): Boolean {
        val handle = thresholderHandles[key] ?: error("No thresholder for key $key")
        @Suppress("UNCHECKED_CAST")
        val thresholder = (fixatedThresholders[key] ?: handle.thresholder)
        return thresholder.isThresholdReached()
    }

    fun fixateThresholder(key: ProbThresholderKey, thresholder: ProbThresholder) {
        fixatedThresholders[key] = thresholder
    }
}

fun ProbsStub.fixateConstantValueProvider(key: ProbProviderKey<*>, constantValue: Boolean) {
    fixateProvider(key, ConstantProbCalculator(constantValue))
}


fun ProbsStub.fixateAlwaysFirstSelector(key: ProbSelectorKey) {
    fixateSelector(key, AlwaysFirstProbSelector())
}

fun ProbsStub.fixatePassthroughDiffuser(key: ProbDiffuserKey) {
    fixateDiffuser(key, PassThroughDiffuser)
}
