package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.House
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UserTest : DescribeSpec({
    var user = User()
    beforeTest { user = User() }

    describe("totalLandUse") {
        it("with house increases") {
            val house = House(1.units)
            user.addEntity(house)

            user.totalLandUse shouldBe house.landUse
        }
    }

})
