package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.enableAndSet
import com.github.seepick.derbauer2.game.resource.givenStorage
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class CitizenReproduceTurnStepTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    describe("When reproduce") {
        it("Given no citizens Then stay zero") {
            user.givenStorage<Citizen>(100.z)
            val citizen = user.enableAndSet(Citizen(), 0.z)

            val change = CitizenReproduceTurnStep(user).calcResourceChange()

            change shouldBeEqual ResourceChange(citizen, 0.z)
        }
        it("Given 1 citizen Then get minimum of 1") {
            user.givenStorage<Citizen>(100.z)
            val citizen = user.enableAndSet(Citizen(), 1.z)

            val change = CitizenReproduceTurnStep(user).calcResourceChange()

            change shouldBeEqual ResourceChange(citizen, Mechanics.citizenReproductionMinimum)
        }
        it("Given 100 citizen Then increase by reproduction rate") {
            user.givenStorage<Citizen>(200.z)
            val citizen = user.enableAndSet(Citizen(), 100.z)

            val change = CitizenReproduceTurnStep(user).calcResourceChange()

            val expected = citizen.owned * Mechanics.citizenReproductionRate
            change shouldBeEqual ResourceChange(citizen, expected)
        }
        it("Given 100 citizen and almost full Then increase capped") {
            user.givenStorage<Citizen>(102.z)
            val citizen = user.enableAndSet(Citizen(), 100.z)

            val change = CitizenReproduceTurnStep(user).calcResourceChange()

            change shouldBeEqual ResourceChange(citizen, 2.z)
        }
    }
})
