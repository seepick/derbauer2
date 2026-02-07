package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import java.util.concurrent.atomic.AtomicInteger

private val turnStepCounter = AtomicInteger(0)

fun TurnStep.Companion.build(
    resource: Resource,
    change: Zz,
    order: Int = turnStepCounter.getAndIncrement(),
) = object : TurnStep {

    override val order = order

    override fun calcTurnChanges() = buildResourceChanges {
        add(resource, change)
    }
}

fun TurnStep.calcShouldContain(resource: Resource, expected: Zz) {
    calcTurnChanges().shouldContainChange(resource, expected)
}
