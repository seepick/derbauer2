package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.gold
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.zp
import com.github.seepick.derbauer2.game.ownedForTest
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.transaction.TxResult
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeInstanceOf

class UserBuildTest : DescribeSpec({
    var user = User()
    beforeTest {
        user = User()
    }

    describe("general") {
        fun User.givenSufficientResourcesForHouse(): House {
            val house = House(0.zp)
            add(Gold(house.costsGold))
            add(Land(house.landUse))
            add(house)
            return house
        }

        it("Given enough money and land Then succeed") {
            val house = user.givenSufficientResourcesForHouse()

            user.build(house) shouldBeEqual TxResult.Success
        }
        it("Given not enough money Then fail") {
            val house = user.givenSufficientResourcesForHouse()
            user.gold = house.costsGold - 1

            user.build(house).shouldBeInstanceOf<TxResult.Fail.InsufficientResources>()
        }
        it("Given not enough land Then fail") {
            val house = user.givenSufficientResourcesForHouse()
            user.resource(Land::class).ownedForTest = 0.zp

            user.build(house).shouldBeInstanceOf<TxResult.Fail.InsufficientResources>()
        }
    }
})
