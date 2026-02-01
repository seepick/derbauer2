package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.resource.ResourceChange
import kotlin.reflect.KClass

interface ResourceTurnStep {
    val phase: TurnPhase
    val requiresEntities: List<KClass<out Entity>>
    fun calcResourceChanges(): List<ResourceChange>
}

enum class TurnPhase {
    First, Last;
}
