package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.Percent
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.foodProductionBonus
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import kotlin.reflect.KClass

/**
 * Mostly about buildings.
 *
 * Not implementing [com.github.seepick.derbauer2.game.turn.ResourceTurnStep] as used as a composition element.
 * See: [com.github.seepick.derbauer2.game.turn.ProduceCitzenCompositeResourceTurnStep]
 */
class ProducesResourceTurnStep(val user: User) {
    fun calcResourceChanges() = buildResourceChanges {
        val bonusesByResource = user.all
            .filterIsInstance<GlobalResourceProductionBonus>().groupBy { it.handlingResource }
        plainProduction().changes.forEach { change ->
            val bonuses = bonusesByResource[change.resourceClass] ?: emptyList()
            val totalBonus = bonuses.sumOf { it.productionBonus(user).number }.`%`
            val baseProduction = change.change
            val finalAmount = baseProduction.timesFloor(1.0 + totalBonus.number)
            add(ResourceChange(change.resourceClass, finalAmount))
        }
    }

    private fun plainProduction() = ResourceChanges(
        user.all.filterIsInstance<ProducesResource>().map { producer ->
            ResourceChange(
                resource = user.findResource(producer.producingResourceClass),
                changeAmount = producer.totalOrSimpleProduceResourceAmount,
            )
        }
    )
}

/** Global, as in koin declared beans, and NOT an entity stored in user.all. */
interface GlobalResourceProductionBonus {
    val handlingResource: KClass<out Resource>
    /** @return can be negative as well */
    fun productionBonus(user: User): Percent
}

class SeasonalFoodProductionBonus(private val turn: CurrentTurn) : GlobalResourceProductionBonus {
    override val handlingResource = Food::class
    override fun productionBonus(user: User) = turn.current.season.foodProductionBonus
}
