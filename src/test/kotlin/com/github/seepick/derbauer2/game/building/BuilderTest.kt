package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.gold
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class BuilderTest : DescribeSpec({
    var user = User()
    beforeTest {
        user = User()
    }

    describe("land") {
        fun User.givenSufficientResourcesForHouse(): House {
            val house = House(0.units)
            addEntity(Gold(house.costsGold))
            addEntity(Land(house.landUse))
            addEntity(house)
            return house
        }
        it("Given enough money and land Then succeed") {
            val house = user.givenSufficientResourcesForHouse()

            Builder(user).build(house) shouldBeEqual BuildResult.Success
        }
        it("Given not enough money Then fail") {
            val house = user.givenSufficientResourcesForHouse()
            user.gold = 0.units

            Builder(user).build(house) shouldBeEqual BuildResult.InsufficientResources
        }
        it("Given not enough land Then fail") {
            val house = user.givenSufficientResourcesForHouse()
            user.resource(Land::class).owned = 0.units

            Builder(user).build(house) shouldBeEqual BuildResult.InsufficientResources
        }
    }
})
