package com.github.seepick.derbauer2.game.logic

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.resource.Food
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class StorageTest : DescribeSpec({
    var user = User()
    beforeTest { user = User() }

    describe("storage capacity for") {
        it("increases") {
            val granary = Granary(1.z)
            user.add(granary)

            user.storageFor(Food::class) shouldBeEqual granary.totalStorageAmount
        }
    }
})