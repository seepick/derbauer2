package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.enableAndSet
import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual

class StorageExtensionsTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }

    describe("totalStorageFor") {
        it("increases") {
            val granary = user.enableAndSet(Granary(), 1.z)

            user.totalStorageFor(Food::class) shouldBeEqual granary.totalStorageAmount
        }
    }
    describe("storageUsage in percent") {
        it("happy path") {
            val food = user.enableAndSet(Food(), 1.z)
            val granary = user.enableAndSet(Granary(), 1.z)
            val expected = (granary.totalStorageAmount.value.toDouble() / food.owned.value / 100.0).`%`

            with(user) {
                food.storageUsage shouldBeEqual expected
            }
        }
    }

    describe("isAbleToStore") {
        it("Given storage Then yes") {
            user.enableAndSet(Granary(), 10.z)
            val food = user.enableAndSet(Food(), 0.z)

            user.isAbleToStore(food, 1.z).shouldBeTrue()
        }
        it("Given no storage Then no") {
            val food = user.enableAndSet(Food(), 0.z)

            user.isAbleToStore(food, 1.z).shouldBeFalse()
        }
        it("Given full storage Then no") {
            val storage = user.enableAndSet(Granary(), 10.z)
            val food = user.enableAndSet(Food(), storage.totalStorageAmount)

            user.isAbleToStore(food, 1.z).shouldBeFalse()
        }
    }
})
