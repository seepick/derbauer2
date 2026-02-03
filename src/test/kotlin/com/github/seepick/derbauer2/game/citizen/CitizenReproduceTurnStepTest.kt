package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.addResource
import com.github.seepick.derbauer2.game.turn.calcShouldContain
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.longs.shouldBeGreaterThan


class CitizenReproduceTurnStepTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }
    fun turner() = CitizenReproduceTurnStep(user)


    describe("When reproduce aka birth without 🍖 food") {
        it("Given 0 🙎🏻‍♂️ Then change 0") {
            val citizen = user.addResource(Citizen(), 0.z)

            turner().calcShouldContain(citizen, 0.zz)
        }
        it("Given 1 🙎🏻‍♂️ Then +1 minimum birth") {
            val citizen = user.addResource(Citizen(), 1.z)

            turner().calcShouldContain(citizen, Mechanics.citizenReproductionMinimum.zz)
        }
        it("Given many 🙎🏻‍♂️ Then +many by reproduction rate") {
            val citizen = user.addResource(Citizen(), 100.z)

            turner().calcShouldContain(citizen, (citizen.owned * Mechanics.citizenReproductionRate).zz)
        }
    }
    describe("With 🍖 food") {
        it("Given many 🙎🏻‍♂️ and 0 🍖 Then change 0 as starvation will take over elsewhere") {
            val citizen = user.addResource(Citizen(), 100.z)
            user.addResource(Food(), 0.z)

            turner().calcShouldContain(citizen, 0.zz)
        }
        it("Given many 🙎🏻‍♂️ and some 🍖 Then +change") {
            user.addResource(Citizen(), 100.z)
            user.addResource(Food(), 1.z)

            turner().calcResourceChanges().changes.first().changeAmount.value shouldBeGreaterThan 0
        }
    }
})
