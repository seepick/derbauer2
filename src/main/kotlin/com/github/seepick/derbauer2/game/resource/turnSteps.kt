package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.TurnPhase
import com.github.seepick.derbauer2.game.turn.TurnStep

/** This includes obviously mostly buildings. */
class ProducesResourceTurnStep(val user: User) : TurnStep {
    override val phase = TurnPhase.First

    override fun calcResourceChanges(): ResourceChanges {
        val modifiers = user.all.filterIsInstance<ResourceProductionModifier>()
        // FIXME implement me
        return ResourceChanges(
            user.all.filterIsInstance<ProducesResource>().map { producer ->
                val resource = user.findResource(producer.producingResourceClass)
                val producingAmount = if (producer is ProducesResourceOwnable) {
                    producer.totalProducingResourceAmount
                } else {
                    producer.producingResourceAmount
                }
                // TODO could become negative! cap at 0; TEST: inject modifier which turns down a lot
                val adjustedProducingAmount = user.capResourceAmount(resource, producingAmount)
                ResourceChange(resource, adjustedProducingAmount)
            })
    }
}

interface ResourceProductionModifier {
    fun handlesResource(resource: Resource): Boolean
    fun modifyAmount(user: User, source: Z): Z
}
