package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.AlwaysFalseProbCalculator
import com.github.seepick.derbauer2.game.prob.AlwaysTrueProbCalculator
import com.github.seepick.derbauer2.game.prob.ProbProviderKey
import com.github.seepick.derbauer2.game.prob.ProbsStub
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk


class HappeningTurnerTest : StringSpec({
    lateinit var probs: ProbsStub
    lateinit var happeningDescriptor: HappeningDescriptor
    lateinit var happening: Happening
    beforeTest {
        probs = ProbsStub()
        happeningDescriptor = mockk()
        happening = mockk()
        every { happeningDescriptor.initProb(any(), any(), any()) } just Runs
        every { happeningDescriptor.buildHappening(any()) } returns happening
    }
    fun turner(
        descriptors: List<HappeningDescriptor> = listOf(happeningDescriptor),

        ) = HappeningTurner(
        user = User(),
        probs = probs,
        repo = { descriptors },
        currentTurn = mockk(),
    ).apply {
        initProb()
    }

    fun givenHappening(nature: HappeningNature, willHappen: Boolean, canHappen: Boolean = willHappen) {
        every { happeningDescriptor.nature } returns nature
        every { happeningDescriptor.canHappen(any(), any()) } returns canHappen
        every { happeningDescriptor.willHappen(any(), any()) } returns willHappen
    }

    "When instantiate with empty descriptors repo Then throw" {
        shouldThrow<IllegalArgumentException> { turner(descriptors = emptyList()) }
    }
    "Given not happening Then null" {
        val turner = turner()
        probs.fixateProvider(ProbProviderKey.happeningTurner, AlwaysFalseProbCalculator)

        turner.maybeHappening().shouldBeNull()
    }
    "Single registered doesnt want to But have to if nothing else found" {
        givenHappening(HappeningNature.Negative, willHappen = false, canHappen = true)
        val turner = turner()
        probs.fixateProvider(ProbProviderKey.happeningTurner, AlwaysTrueProbCalculator)
        probs.fixateProvider(ProbProviderKey.happeningIsNegative, AlwaysTrueProbCalculator)

        turner.maybeHappening() shouldBeSameInstanceAs happening
    }
})
