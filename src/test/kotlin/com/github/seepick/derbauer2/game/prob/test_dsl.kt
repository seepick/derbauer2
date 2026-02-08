package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.testInfra.dsl.GivenDsl
import org.koin.test.get

/** Assumes the koin test context has injected/overwritten [ProbsImpl] with the test stub. */
val GivenDsl.probs get() = koin.get<Probs>() as ProbsStub

fun GivenDsl.disableAllProbs() {
    ProbDiffuserKey.all.forEach { key ->
        probs.fixateDiffuserPassthrough(key)
    }
    // provider, selector, thresholder?
}
