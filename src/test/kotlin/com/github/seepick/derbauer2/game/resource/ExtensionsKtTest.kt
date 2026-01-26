package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.units
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class ExtensionsKtTest : DescribeSpec( {
    describe("isAbleToStore") {
        it("Given storage Then yes") {
            val user = User()
            user.add(Granary(10.units))
            val food = user.add(Food(0.units))
            user.isAbleToStore(food, 1.units).shouldBeTrue()
        }
        it("Given no storage Then no") {
            val user = User()
            val food = user.add(Food(0.units))
            user.isAbleToStore(food, 1.units).shouldBeFalse()
        }
        it("Given full storage Then no") {
            val user = User()
            val storage = user.add(Granary(10.units))
            val food = user.add(Food(storage.totalStorageAmount))
            user.isAbleToStore(food, 1.units).shouldBeFalse()
        }
    }
})
