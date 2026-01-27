package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.resource.totalLandUse
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class UserTest : DescribeSpec({
    var user = User()
    beforeTest { user = User() }

    describe("land use") {
        it("with house increases") {
            val house = House(1.z)
            user.add(house)

            user.totalLandUse shouldBe house.landUse
        }
    }
    describe("add entity") {
        it("adds resource") {
            val citizen = Citizen(10.z)
            user.add(citizen)
            user.resource(Citizen::class) shouldBe citizen
        }
        it("duplicate fail") {
            user.add(Citizen(1.z))
            shouldThrow<Exception> { user.add(Citizen(2.z)) }
                .message shouldContain Citizen::class.simpleName!!
        }
    }
})
