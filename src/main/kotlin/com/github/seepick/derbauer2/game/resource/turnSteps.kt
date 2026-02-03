package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.TurnPhase
import com.github.seepick.derbauer2.game.turn.TurnStep
import kotlin.reflect.KClass

/** This includes obviously mostly buildings. */
class ProducesResourceTurnStep(val user: User) : TurnStep {
    override val phase = TurnPhase.First


    @Suppress("CognitiveComplexMethod")
    override fun calcResourceChanges() = buildResourceChanges {
        val modifiersByResource = user.all
            .filterIsInstance<ResourceProductionModifier>().groupBy { it.handlingResource }
        plainProduction().changes.forEach { change ->
            val modifiers = modifiersByResource[change.resourceClass] ?: emptyList()
            val modifiedAmount = modifiers.fold(change.changeAmount) { acc, modifier ->
                modifier.modifyAmount(user, acc)
            }
            val resource = user.findResource(change.resourceClass)
            add(ResourceChange(resource, limitAmount(resource, modifiedAmount)))
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

    private fun limitAmount(resource: Resource, modifiedAmount: Zz): Zz =
        if (modifiedAmount > 0) { // is positive
            val positiveChange = modifiedAmount.toZAbs()
            if (resource is StorableResource) { // limit to max
                positiveChange.value.coerceAtMost(user.freeStorageFor(resource).value).zz
            } else { // as much as you want
                positiveChange.zz
            }
        } else { // is negative
            if (resource is StorableResource) {
                if (resource.owned.zz + modifiedAmount < 0.zz) { // can't lose more than owned
                    -resource.owned.zz
                } else {
                    modifiedAmount
                }
            } else {
                modifiedAmount // ok to be negative
            }
        }
}

interface ResourceProductionModifier {
    val handlingResource: KClass<out Resource>
    fun modifyAmount(user: User, source: Zz): Zz
}
