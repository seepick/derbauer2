package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.common.unit.z
import com.github.seepick.derbauer2.common.unit.zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.addResource
import com.github.seepick.derbauer2.game.resource.buildResourceChanges
import com.github.seepick.derbauer2.game.turn.TurnReport
import com.github.seepick.derbauer2.game.turn.empty
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.doubles.shouldBeNegative
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull

class HappinessModifierTest : StringSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    context("HappinessCitizenPreModifier") {
        "Given some 🙎🏻‍♂️ and 🥳 Then -🥳 * 🙎🏻‍♂️" {
            val citizen = user.addResource(Citizen(), 100.z)
            val happiness = user.add(Happiness())
            val expectedHappinessChange = -(citizen.owned.value * Mechanics.statHappinessConsumedPerCitizen)

            val modification = HappinessCitizenPreModifier().calcModifierOrNull(user, happiness::class)

            modification.shouldNotBeNull() shouldBeEqual expectedHappinessChange
        }
    }
    // context("HappinessSeasonPreModifier")
    context("HappinessDeathPostModifier") {
        "Given some 🙎🏻‍♂️ died ☠️ Then decrease happiness 🥳☹️" {
            user.add(Citizen())
            user.add(Happiness())
            val report = TurnReport.empty().copy(
                resourceChanges = buildResourceChanges { add(Citizen::class, (-1).zz) })

            val modification = HappinessDeathPostModifier().calcModifierOrNull(report, user, Happiness::class)

            modification.shouldNotBeNull().shouldBeNegative()
        }
    }
})
