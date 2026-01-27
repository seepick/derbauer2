package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.transaction.shouldBeFail
import com.github.seepick.derbauer2.game.transaction.shouldBeSuccess
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class TxResourceTest : DescribeSpec({
    lateinit var user: User
    beforeTest {
        user = User()
    }
    describe("simple non-storageable") {
        it("When add Then added") {
            val resource = user.add(Gold(0.z))
            user.execTxResource(resource::class, 1.z).shouldBeSuccess()
            resource.owned shouldBeEqual 1.z
        }
    }
    describe("storageable") {
        it("Given no storage When add Then fail") {
            val resource = user.add(Food(0.z))

            user.execTxResource(resource::class, 1.z).shouldBeFail("Not enough storage")
        }
        it("Given enough storage When add Then succeed") {
            user.add(Granary(1.z))
            val resource = user.add(Food(0.z))

            user.execTxResource(resource::class, 1.z).shouldBeSuccess()
        }
    }
})
