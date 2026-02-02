package com.github.seepick.derbauer2.game.citizen

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Mechanics
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.enableAndSet
import com.github.seepick.derbauer2.game.resource.shouldContainChange
import io.kotest.core.spec.style.DescribeSpec

class CitizenTaxesTurnStepTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    describe("When taxes paid") {
        it("Given some citizens Then report contains taxes But user gold unchanged") {
            val gold = user.enableAndSet(Gold(), 0.z)
            val citizen = user.enableAndSet(Citizen(), 10.z)

            val changes = CitizenTaxesTurnStep(user).calcResourceChanges()

            val expectedTax = citizen.owned * Mechanics.citizenTax
            changes.shouldContainChange(gold, expectedTax.zz)
        }
    }
})
