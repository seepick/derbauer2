package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.testInfra.dsl.GivenDsl
import com.github.seepick.derbauer2.game.testInfra.dsl.TestDsl
import org.koin.test.KoinTest
import org.koin.test.get

@TestDsl
class ProbDsl(private val koin: KoinTest) {

    fun updateProvider(key: ProbProviderKey<*>, constantValue: Boolean) {
        probsImpl().updateProvider(key, AlwaysProbCalculator(constantValue))
    }

    fun updateSelector(key: ProbSelectorKey, selector: ProbSelector<out Any>) {
        probsImpl().updateSelector(key, selector)
    }

    fun updateSelectorAlwaysFirst(key: ProbSelectorKey) {
        updateSelector(key, AlwaysFirstProbSelector())
    }

    fun updateDiffuser(key: ProbDiffuserKey, diffuser: ProbDiffuser) {
        probsImpl().updateDiffuser(key, diffuser)
    }

    fun updateDiffuserPassthrough(key: ProbDiffuserKey) {
        updateDiffuser(key, PassThroughDiffuser)
    }

    private fun probsImpl() = koin.get<Probs>() as ProbsImpl

}

fun GivenDsl.prob(code: ProbDsl.() -> Unit) {
    ProbDsl(koin).code()
}
