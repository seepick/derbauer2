package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass

private val turnStepCounter = AtomicInteger(0)
fun TurnStep.Companion.build(
    resource: Resource,
    change: Zz,
    order: Int = turnStepCounter.getAndIncrement(),
) = object : TurnStep {

    override val order = order
    override val requiresEntities = emptyList<KClass<out Entity>>()

    override fun calcTurnChanges() = buildResourceChanges {
        add(resource, change)
    }
}

fun TurnStep.calcShouldContain(resource: Resource, expected: Zz) {
    calcTurnChanges().shouldContainChange(resource, expected)
}
