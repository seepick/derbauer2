package com.github.seepick.derbauer2.game.testInfra.dsl

import com.github.seepick.derbauer2.game.probability.Probabilities
import com.github.seepick.derbauer2.game.probability.ProbabilitiesImpl
import com.github.seepick.derbauer2.game.probability.ProbabilityCalculator
import com.github.seepick.derbauer2.game.probability.ProbabilityProviderHandle
import com.github.seepick.derbauer2.game.probability.ProbabilityProviderSource
import com.github.seepick.derbauer2.game.probability.ProbabilitySelector
import com.github.seepick.derbauer2.game.probability.ProbabilitySelectorHandle
import com.github.seepick.derbauer2.game.probability.ProbabilitySelectorSource
import org.koin.test.KoinTest
import org.koin.test.get

@TestDsl
class ProbabilityDsl(private val koin: KoinTest) : ProbalityProviderAddSourceAndCalc,
    ProbalitySelectorAddSourceAndSelector {
    var providers = ProvidersChangeDsl(this)
    var selectors = SelectorsChangeDsl(this)

    override operator fun plus(
        sourceAndCalc: Pair<ProbabilityProviderSource, ProbabilityCalculator>
    ): ProvidersChangeDsl {
        val probabilities = koin.get<Probabilities>() as ProbabilitiesImpl
        probabilities.providerHandles.updateCalc(sourceAndCalc.first, sourceAndCalc.second)
        return providers
    }

    override fun plus(
        sourceAndCalc: Pair<ProbabilitySelectorSource, ProbabilitySelector<out Any>>
    ): SelectorsChangeDsl {
        val probabilities = koin.get<Probabilities>() as ProbabilitiesImpl
        probabilities.selectorHandles.updateSelect(sourceAndCalc.first, sourceAndCalc.second)
        return selectors
    }
}

interface ProbalityProviderAddSourceAndCalc {
    operator fun plus(sourceAndCalc: Pair<ProbabilityProviderSource, ProbabilityCalculator>): ProvidersChangeDsl
}

class ProvidersChangeDsl(delegate: ProbalityProviderAddSourceAndCalc) : ProbalityProviderAddSourceAndCalc by delegate

interface ProbalitySelectorAddSourceAndSelector {
    operator fun plus(sourceAndCalc: Pair<ProbabilitySelectorSource, ProbabilitySelector<out Any>>): SelectorsChangeDsl
}

class SelectorsChangeDsl(delegate: ProbalitySelectorAddSourceAndSelector) :
    ProbalitySelectorAddSourceAndSelector by delegate


fun MutableMap<ProbabilityProviderSource, ProbabilityProviderHandle<Any>>.updateCalc(
    source: ProbabilityProviderSource, calc: ProbabilityCalculator
) {
    val providerProvider = this[source]!!
    this[source] = providerProvider.copy(calculator = calc)
}

fun MutableMap<ProbabilitySelectorSource, ProbabilitySelectorHandle<Any>>.updateSelect(
    source: ProbabilitySelectorSource, newSelector: ProbabilitySelector<out Any>
) {
    val selectorSelector = this[source]!!
    this[source] = selectorSelector.withSelector(newSelector) // copy didnt work properly; generics issue
}
