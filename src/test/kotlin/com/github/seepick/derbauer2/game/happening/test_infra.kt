package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.happening.happenings.HappeningRefRegistry
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import io.kotest.property.Arb
import io.kotest.property.arbitrary.boolean
import io.kotest.property.arbitrary.enum
import io.kotest.property.arbitrary.next
import io.mockk.mockk

class HappeningRefRegistryStub(descriptors: List<HappeningRef>) : HappeningRefRegistry {
    override val all = descriptors
}

fun HappeningRef.Companion.any() = HappeningRefStub()

class HappeningRefStub(
    override val nature: HappeningNature = Arb.enum<HappeningNature>().next(),
    val canHappen: Boolean = Arb.boolean().next(),
    val willHappen: Boolean = Arb.boolean().next(),
    val happening: Happening = mockk(),
) : HappeningRef {
    override fun canHappen(user: User, probs: Probs) = canHappen
    override fun willHappen(user: User, probs: Probs) = willHappen
    override fun buildHappening(user: User) = happening
    override fun initProb(probs: Probs, user: User, turn: CurrentTurn) {
        // no-op
    }
}
