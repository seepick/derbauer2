package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.ResourceChange
import kotlin.reflect.KClass

interface ResourceTurnStep {
    val phase: TurnPhase
    val requiresEntities: List<KClass<out Entity>> get() = emptyList()
    fun calcResourceChanges(): List<ResourceChange>
}

interface ResourceTurnStepSingle : ResourceTurnStep {
    fun calcResourceChange(): ResourceChange
    override fun calcResourceChanges(): List<ResourceChange> = listOf(calcResourceChange())
}

abstract class DefaultResourceTurnStep(
    val user: User,
    override val phase: TurnPhase,
    override val requiresEntities: List<KClass<out Entity>>
) : ResourceTurnStepSingle

enum class TurnPhase {
    First, Last;
}
