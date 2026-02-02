package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import kotlin.reflect.KClass

interface TurnStep {
    val phase: TurnPhase
    val requiresEntities: List<KClass<out Entity>> get() = emptyList()
    fun calcResourceChanges(): ResourceChanges
    // more to come ...
}

enum class TurnPhase {
    First, Last;
}

interface TurnStepSingle : TurnStep {
    override fun calcResourceChanges(): ResourceChanges
}

abstract class DefaultTurnStep(
    val user: User,
    override val phase: TurnPhase,
    override val requiresEntities: List<KClass<out Entity>>
) : TurnStepSingle
