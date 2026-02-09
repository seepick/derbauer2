package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.ActionBus
import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import com.github.seepick.derbauer2.game.transaction.DefaultTxValidatorRegistry
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.transaction.shouldBeSuccess
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify

class BuildTxTest : DescribeSpec({
    lateinit var user: User
    lateinit var buildingService: BuildingService
    lateinit var actionBus: ActionBus
    beforeTest {
        user = User(DefaultTxValidatorRegistry.validators)
        actionBus = mockk()
        buildingService = BuildingService(user, actionBus)
    }

    describe("general") {
        fun User.givenSufficientResourcesForHouse(): House {
            val house = House()
            add(Gold())
            add(Land())
            add(house)
            execTx(
                TxOwnable(Gold::class, house.costsGold.zz),
                TxOwnable(Land::class, house.landUse.zz),
            )
            return house
        }

        it("Given enough money and land Then succeed and dispatch action") {
            val house = user.givenSufficientResourcesForHouse()
            every { actionBus.dispatch(any()) } just Runs

            buildingService.build(house::class).shouldBeSuccess()
            verify { actionBus.dispatch(eq(BuildingBuiltAction(house::class))) }
        }
        it("Given not enough money Then fail") {
            val house = user.givenSufficientResourcesForHouse()
            user.findResource(Gold::class).ownedForTest = house.costsGold - 1

            buildingService.build(house::class).shouldBeInstanceOf<TxResult.Fail.InsufficientResources>()
        }
        it("Given not enough land Then fail") {
            val house = user.givenSufficientResourcesForHouse()
            user.findResource(Land::class).ownedForTest = 0.z

            buildingService.build(house::class).shouldBeInstanceOf<TxResult.Fail.LandOveruse>()
        }
    }
})
