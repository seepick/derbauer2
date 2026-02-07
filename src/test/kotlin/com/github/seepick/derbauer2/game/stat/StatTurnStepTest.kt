package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.strictMin1To1
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.prob.ProbsImpl
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.addResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeLessThan

class StatTurnStepTest : StringSpec({
    "Given many 🙎🏻‍♂️ Then decreased" {
        val initialHappiness = 0.0
        val user = User()
        user.addResource(Citizen(), 1000.z)
        val happiness = user.add(Happiness(initialHappiness.strictMin1To1))

        StatTurnStep(user, ProbsImpl()).execTurn()

        happiness.value.number shouldBeLessThan initialHappiness
    }
})
