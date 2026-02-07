package com.github.seepick.derbauer2.game.turn

import com.github.seepick.derbauer2.game.citizen.CitizenTurnStep
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.ProbInitializer
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.ProducesResourceTurnStep
import com.github.seepick.derbauer2.game.resource.ResourceChanges

class ProduceCitzenCompositeResourceStep(
    user: User,
    probs: Probs,
) : ResourceStep, ProbInitializer {

    private val citizenStep = CitizenTurnStep(user, probs)
    private val produceResourceStep = ProducesResourceTurnStep(user)
    override val order = ResourceStep.Order.producesResourcesAndCitizen

    override fun initProb() {
        citizenStep.initProb()
    }

    override fun calcChanges(): ResourceChanges {
        val productionChanges = produceResourceStep.calcResourceChanges()
        val foodChange = productionChanges.changeFor(Food::class)
        val citizenChanges = citizenStep.calcTurnChanges(foodChange?.changeAmount)
        return productionChanges.merge(citizenChanges)
    }
}
