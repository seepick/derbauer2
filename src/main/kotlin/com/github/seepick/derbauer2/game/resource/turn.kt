package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.TurnStep
import kotlin.reflect.KClass

/**
 * Mostly about buildings.
 * Does NOT implement [TurnStep] interface, as used as a composite.
 */
class ProducesResourceTurnStep(val user: User) {

    fun calcResourceChanges() = buildResourceChanges {
        val modifiersByResource = user.all
            .filterIsInstance<ResourceProductionModifier>().groupBy { it.handlingResource }
        plainProduction().changes.forEach { change ->
            val modifiers = modifiersByResource[change.resourceClass] ?: emptyList()
            val modifiedAmount = modifiers.fold(change.changeAmount) { acc, modifier ->
                modifier.modifyAmount(user, acc)
            }
            add(ResourceChange(change.resourceClass, modifiedAmount))
        }
    }

    private fun plainProduction() = ResourceChanges(
        user.all.filterIsInstance<ProducesResource>().map { producer ->
            ResourceChange(
                resource = user.findResource(producer.producingResourceClass),
                changeAmount = if (producer is ProducesResourceOwnable) {
                    producer.totalProducingResourceAmount
                } else {
                    producer.producingResourceAmount
                }
            )
        })

}

interface ResourceProductionModifier {
    val handlingResource: KClass<out Resource>
    fun modifyAmount(user: User, source: Zz): Zz
}
