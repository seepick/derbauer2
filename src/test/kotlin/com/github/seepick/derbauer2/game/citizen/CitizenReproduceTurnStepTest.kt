package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.enableAndSet
import com.github.seepick.derbauer2.game.resource.givenStorage
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import io.kotest.core.spec.style.DescribeSpec

class CitizenReproduceTurnStepTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    describe("When reproduce") {
        it("Given no citizens Then stay zero") {
            user.givenStorage<Citizen>(100.z)
            val citizen = user.enableAndSet(Citizen(), 0.z)

            val changes = CitizenReproduceTurnStep(user).calcResourceChanges()

            changes.shouldContainChange(citizen, 0.zz)
        }
        it("Given 1 citizen Then get minimum of 1") {
            user.givenStorage<Citizen>(100.z)
            val citizen = user.enableAndSet(Citizen(), 1.z)

            val changes = CitizenReproduceTurnStep(user).calcResourceChanges()

            changes.shouldContainChange(citizen, Mechanics.citizenReproductionMinimum.zz)
        }
        it("Given 100 citizen Then increase by reproduction rate") {
            user.givenStorage<Citizen>(200.z)
            val citizen = user.enableAndSet(Citizen(), 100.z)

            val changes = CitizenReproduceTurnStep(user).calcResourceChanges()

            val expected = citizen.owned * Mechanics.citizenReproductionRate
            changes.shouldContainChange(citizen, expected.zz)
        }
        it("Given 100 citizen and almost full Then increase capped") {
            user.givenStorage<Citizen>(102.z)
            val citizen = user.enableAndSet(Citizen(), 100.z)

            val changes = CitizenReproduceTurnStep(user).calcResourceChanges()

            changes.shouldContainChange(citizen, 2.zz)
        }
    }
})
