package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.AlwaysFalseProbCalculator
import com.github.seepick.derbauer2.game.prob.AlwaysTrueProbCalculator
import com.github.seepick.derbauer2.game.prob.ProbProviderKey
import com.github.seepick.derbauer2.game.prob.ProbsImpl
import com.github.seepick.derbauer2.game.prob.updateProvider
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk

class HappeningTurnerTest : StringSpec({
    lateinit var probs: ProbsImpl
    lateinit var happeningDescriptor: HappeningDescriptor
    lateinit var happening: Happening
    beforeTest {
        probs = ProbsImpl()
        happeningDescriptor = mockk()
        happening = mockk()
        every { happeningDescriptor.initProb(any(), any()) } just Runs
        every { happeningDescriptor.buildHappening(any()) } returns happening
    }
    fun turner(
        descriptors: List<HappeningDescriptor> = listOf(happeningDescriptor),
    ) = HappeningTurner(
        user = User(),
        probs = probs,
        repo = { descriptors },
    ).apply { initProb() }

    "When instantiate with empty descriptors repo Then throw" {
        shouldThrow<IllegalArgumentException> { turner(descriptors = emptyList()) }
    }
    "Given not happening Then null" {
        val turner = turner()
        probs.updateProvider(ProbProviderKey.happeningTurner, AlwaysFalseProbCalculator)

        turner.maybeHappening().shouldBeNull()
    }
    "Single registered doesnt want to But have to if nothing else found" {
        every { happeningDescriptor.nature } returns HappeningNature.Negative
        every { happeningDescriptor.canHappen(any(), any()) } returns true
        every { happeningDescriptor.willHappen(any(), any()) } returns false
        val turner = turner()
        probs.updateProvider(ProbProviderKey.happeningTurner, AlwaysTrueProbCalculator)
        probs.updateProvider(ProbProviderKey.happeningIsNegative, AlwaysTrueProbCalculator)

        turner.maybeHappening() shouldBeSameInstanceAs happening
    }
})
