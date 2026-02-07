package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import java.util.concurrent.atomic.AtomicInteger

private val turnStepCounter = AtomicInteger(0)

fun ResourceStep.Companion.build(
    resource: Resource,
    change: Zz,
    order: Int = turnStepCounter.getAndIncrement(),
) = object : ResourceStep {

    override val order = order

    override fun calcChanges() = buildResourceChanges {
        add(resource, change)
    }
}

fun ResourceStep.calcShouldContain(resource: Resource, expected: Zz) {
    calcChanges().shouldContainChange(resource, expected)
}
