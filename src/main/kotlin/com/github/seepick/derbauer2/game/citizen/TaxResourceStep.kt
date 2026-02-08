package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.GrowthDiffuser
import com.github.seepick.derbauer2.game.prob.ProbDiffuserKey
import com.github.seepick.derbauer2.game.prob.ProbInitializer
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.resource.CapitalismTech
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.tech.hasTech
import com.github.seepick.derbauer2.game.turn.DefaultResourceStep
import com.github.seepick.derbauer2.game.turn.ResourceStep

private val probTaxKey = ProbDiffuserKey("tax")
val ProbDiffuserKey.Companion.taxKey get() = probTaxKey

class TaxResourceStep(user: User, private val probs: Probs) : ProbInitializer,
    DefaultResourceStep(user, ResourceStep.Order.tax, listOf(Citizen::class, Gold::class)) {

    override fun initProb() {
        probs.setDiffuser(ProbDiffuserKey.taxKey, GrowthDiffuser(variation = Mechanics.taxGrowthVariation))
    }

    override fun calcChangesChecked() = buildResourceChanges {
        val citizen = user.findResource<Citizen>()
        val rawTax = citizen.owned * Mechanics.taxRate
        val diffusedTax = probs.diffuse(ProbDiffuserKey.taxKey, rawTax.zz).toZLimitMinZero()
        val techAdjustedTax = if (user.hasTech(CapitalismTech::class)) {
            diffusedTax * Mechanics.techCapitalismTaxMultiplier
        } else {
            diffusedTax
        }
        add(Gold::class, techAdjustedTax)
    }
}
