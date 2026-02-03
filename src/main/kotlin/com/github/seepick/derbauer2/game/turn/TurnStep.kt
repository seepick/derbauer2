package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass

interface TurnStep {
    val order: Int
    val requiresEntities: List<KClass<out Entity>> get() = emptyList()
    fun calcResourceChanges(): ResourceChanges
    // more to come ...
    companion object // for extension functions
}

object TurnStepOrder {
    private val incrementor = AtomicInteger(0)
    // order is of relevance!
    val producesResources = incrementor.getAndIncrement()

    val citizen = incrementor.getAndIncrement()

    val taxes = incrementor.getAndIncrement()
}

interface TurnStepSingle : TurnStep {
    override fun calcResourceChanges(): ResourceChanges
}

abstract class DefaultTurnStep(
    val user: User,
    override val order: Int,
    override val requiresEntities: List<KClass<out Entity>>
) : TurnStepSingle
