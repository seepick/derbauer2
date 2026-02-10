package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Field
import com.github.seepick.derbauer2.game.building.Tent
import com.github.seepick.derbauer2.game.building.addBuilding
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
        it("Given a tent and enough land Then tent occupies all") {
            val tent = Tent()
            user.addResource(Land(), tent.landUse)
            user.addBuilding(tent, 1.z)

            user.totalLandUse shouldBe tent.landUse
            user.landAvailable shouldBeEqual 0.z
        }
        it("Given two buildings and enough land Then both occupy all") {
            val tent = Tent()
            val field = Field()
            user.addResource(Land(), tent.landUse + field.landUse)
            user.addBuilding(tent, 1.z)
            user.addBuilding(field, 1.z)

            user.totalLandUse shouldBe tent.landUse + field.landUse
            user.landAvailable shouldBeEqual 0.z
        }
    }
})
