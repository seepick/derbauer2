package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.z
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class ExtensionsKtTest : DescribeSpec( {
    describe("isAbleToStore") {
        it("Given storage Then yes") {
            val user = User()
            user.add(Granary(10.z))
            val food = user.add(Food(0.z))
            user.isAbleToStore(food, 1.z).shouldBeTrue()
        }
        it("Given no storage Then no") {
            val user = User()
            val food = user.add(Food(0.z))
            user.isAbleToStore(food, 1.z).shouldBeFalse()
        }
        it("Given full storage Then no") {
            val user = User()
            val storage = user.add(Granary(10.z))
            val food = user.add(Food(storage.totalStorageAmount))
            user.isAbleToStore(food, 1.z).shouldBeFalse()
        }
    }
    describe("land use") {
        it("Given a house and enough land Then house occupies all") {
            val user = User()
            val house = House(1.z)
            user.add(Land(house.totalLandUse))
            user.add(house)

            user.totalLandUse shouldBe house.landUse
            user.landAvailable shouldBeEqual 0.z
        }
        it("Given two buildings and enough land Then both occupy all") {
            val user = User()
            val house = House(1.z)
            val farm = Farm(1.z)
            user.add(Land(house.landUse + farm.landUse))
            user.add(house, farm)

            user.totalLandUse shouldBe (house.landUse + farm.landUse)
            user.landAvailable shouldBeEqual 0.z
        }
    }
})
