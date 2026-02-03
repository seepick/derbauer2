package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Farm
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.building.addAndSet
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class LandExtensionsTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    describe("land use") {
        it("Given a house and enough land Then house occupies all") {
            val house = House()
            user.addAndSet(Land(), house.landUse)
            user.addAndSet(house, 1.z)

            user.totalLandUse shouldBe house.landUse
            user.landAvailable shouldBeEqual 0.z
        }
        it("Given two buildings and enough land Then both occupy all") {
            val house = House()
            val farm = Farm()
            user.addAndSet(Land(), house.landUse + farm.landUse)
            user.addAndSet(house, 1.z)
            user.addAndSet(farm, 1.z)

            user.totalLandUse shouldBe house.landUse + farm.landUse
            user.landAvailable shouldBeEqual 0.z
        }
    }
})
