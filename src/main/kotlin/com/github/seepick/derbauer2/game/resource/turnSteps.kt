package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.ResourceTurnStep
import com.github.seepick.derbauer2.game.turn.TurnPhase

/** This includes obviously also buildings. */
class ResourceProducingResourceTurnStep(val user: User) : ResourceTurnStep {
    override val phase = TurnPhase.First

    override fun calcResourceChanges(): List<ResourceChange> =
        user.all.filterIsInstance<ProducesResource>().map { producer ->
            val resource = user.resource(producer.producingResourceClass)
            val producingAmount = if (producer is ProducesResourceOwnable) {
                producer.totalProducingResourceAmount
            } else {
                producer.producingResourceAmount
            }
            val adjustedProducingAmount = user.capResourceAmount(resource, producingAmount)
            ResourceChange(resource, adjustedProducingAmount)
        }
}
