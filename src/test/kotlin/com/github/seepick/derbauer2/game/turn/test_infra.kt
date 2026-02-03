package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.shouldContainChange

fun TurnStep.Companion.build(
    resource: Resource,
    change: Zz,
    phase: TurnPhase = TurnPhase.First,
) = object : TurnStep {
    override val phase = phase
    override fun calcResourceChanges() = buildResourceChanges {
        add(resource, change)
    }
}

fun TurnStep.calcShouldContain(resource: Resource, expected: Zz) {
    calcResourceChanges().shouldContainChange(resource, expected)
}
