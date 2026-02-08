package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

/**
 * Mostly about buildings.
 *
 * Not implementing [com.github.seepick.derbauer2.game.turn.ResourceStep] as used as a composition element.
 * See: [com.github.seepick.derbauer2.game.turn.ProduceCitzenCompositeResourceStep]
 */
class ProducesResourceTurnStep(val user: User) {

    fun calcResourceChanges() = buildResourceChanges {
        val modifiersByResource = user.all
            .filterIsInstance<GlobalResourceProductionModifier>().groupBy { it.handlingResource }
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
            val resource = user.findResource(producer.producingResourceClass)
            ResourceChange(
                resource = resource,
                changeAmount = producer.totalOrSimpleProduceResourceAmount,
            )
        })
}

interface GlobalResourceProductionModifier {
    val handlingResource: KClass<out Resource>
    fun modifyAmount(user: User, source: Zz): Zz
}
