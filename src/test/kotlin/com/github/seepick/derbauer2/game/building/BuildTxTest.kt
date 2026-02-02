package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.resource
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.transaction.shouldBeSuccess
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeInstanceOf

class BuildTxTest : DescribeSpec({
    var user = User()
    beforeTest {
        user = User()
    }

    describe("general") {
        fun User.givenSufficientResourcesForHouse(): House {
            val house = House()
            enable(Gold())
            enable(Land())
            enable(house)
            execTx(
                TxOwnable(Gold::class, house.costsGold.zz),
                TxOwnable(Land::class, house.landUse.zz),
            )
            return house
        }

        it("Given enough money and land Then succeed") {
            val house = user.givenSufficientResourcesForHouse()

            user.build(house::class).shouldBeSuccess()
        }
        it("Given not enough money Then fail") {
            val house = user.givenSufficientResourcesForHouse()
            user.resource(Gold::class).ownedForTest = house.costsGold - 1

            user.build(house::class).shouldBeInstanceOf<TxResult.Fail.InsufficientResources>()
        }
        it("Given not enough land Then fail") {
            val house = user.givenSufficientResourcesForHouse()
            user.resource(Land::class).ownedForTest = 0.z

            user.build(house::class).shouldBeInstanceOf<TxResult.Fail.LandOveruse>()
        }
    }
})
