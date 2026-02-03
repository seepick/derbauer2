package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.buildResourceChanges

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
