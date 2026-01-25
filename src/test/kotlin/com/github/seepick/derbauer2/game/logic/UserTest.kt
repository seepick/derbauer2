package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.resource.Citizen
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class UserTest : DescribeSpec({
    var user = User()
    beforeTest { user = User() }

    describe("land use") {
        it("with house increases") {
            val house = House(1.units)
            user.addEntity(house)

            user.totalLandUse shouldBe house.landUse
        }
    }
    describe("add entity") {
        it("adds resource") {
            val citizen = Citizen(10.units)
            user.addEntity(citizen)
            user.resource(Citizen::class) shouldBe citizen
        }
        it("duplicate fail") {
            user.addEntity(Citizen(1.units))
            shouldThrow<Exception> { user.addEntity(Citizen(2.units)) }
                .message shouldContain Citizen::class.simpleName!!
        }
    }
})
