package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.ConstantFalseProbCalculator
import com.github.seepick.derbauer2.game.prob.ConstantTrueProbCalculator
import com.github.seepick.derbauer2.game.prob.ProbProviderKey
import com.github.seepick.derbauer2.game.prob.ProbsStub
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.mockk

class HappeningTurnerTest : StringSpec({
    lateinit var probs: ProbsStub
    lateinit var happening: Happening

    beforeTest {
        probs = ProbsStub()
        happening = mockk()
    }

    fun turner(descriptors: List<HappeningDescriptor>) = HappeningTurner(
        user = User(),
        probs = probs,
        repo = HappeningDescriptorRepoStub(descriptors),
        currentTurn = mockk(),
    ).apply {
        initProb()
    }

    "When instantiate with empty descriptors repo Then throw" {
        shouldThrow<IllegalArgumentException> { turner(emptyList()) }
    }
    "Given not happening Then null" {
        val turner = turner(listOf(HappeningDescriptor.any()))
        probs.fixateProvider(ProbProviderKey.happeningTurner, ConstantFalseProbCalculator)

        turner.maybeHappening().shouldBeNull()
    }
    "Single registered doesnt want to But have to if nothing else found" {
        val happeningDescriptor = HappeningDescriptorStub(
            nature = HappeningNature.Negative,
            canHappen = true,
            willHappen = false,
            happening = happening,
        )
        probs.fixateProvider(ProbProviderKey.happeningTurner, ConstantTrueProbCalculator)
        probs.fixateProvider(ProbProviderKey.happeningIsNegative, ConstantTrueProbCalculator)
        val turner = turner(listOf(happeningDescriptor))

        turner.maybeHappening() shouldBeSameInstanceAs happening
    }
})
