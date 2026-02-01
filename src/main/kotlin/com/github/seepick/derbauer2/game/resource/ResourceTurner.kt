package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.core.User

class ResourceTurner(
    private val user: User,
) {
    fun buildResourceChanges() = buildResourceChanges {
        addAll(genericResourceProduction())
    }

    /** This includes obviously also buildings. */
    fun genericResourceProduction(): List<ResourceChange> {
        val changes = mutableListOf<ResourceChange>()
        user.all.filterIsInstance<ProducesResource>().forEach { producer ->
            val resource = user.resource(producer.producingResourceClass)
            val producingAmount = if (producer is ProducesResourceOwnable) {
                producer.totalProducingResourceAmount
            } else {
                producer.producingResourceAmount
            }
            val adjustedProducingAmount = user.capResourceAmount(resource, producingAmount)
            changes += ResourceChange(resource, adjustedProducingAmount)
        }
        return changes
    }
}
