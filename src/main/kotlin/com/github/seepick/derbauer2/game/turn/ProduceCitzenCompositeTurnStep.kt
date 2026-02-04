package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.citizen.CitizenTurnStep
import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.ProbInitializer
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.ProducesResourceTurnStep
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import kotlin.reflect.KClass

class ProduceCitzenCompositeTurnStep(
    user: User,
    probs: Probs,
) : TurnStep, ProbInitializer {

    private val citizenStep = CitizenTurnStep(user, probs)
    private val produceResourceStep = ProducesResourceTurnStep(user)
    override val requiresEntities = emptyList<KClass<out Entity>>()
    override val order = TurnStepOrder.producesResourcesAndCitizen

    override fun initProb() {
        citizenStep.initProb()
    }

    override fun calcTurnChanges(): ResourceChanges {
        val productionChanges = produceResourceStep.calcResourceChanges()
        val foodChange = productionChanges.changeFor(Food::class)
        val citizenChanges = citizenStep.calcTurnChanges(foodChange)

        return productionChanges.merge(citizenChanges)
    }
}