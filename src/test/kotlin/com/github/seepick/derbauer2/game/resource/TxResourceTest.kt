package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.transaction.shouldBeFail
import com.github.seepick.derbauer2.game.transaction.shouldBeSuccess
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.equals.shouldBeEqual

class TxResourceTest : DescribeSpec() {

    private lateinit var user: User

    override suspend fun beforeTest(testCase: TestCase) {
        user = User()
    }

    init {
        describe("simple non-storageable") {
            it("When add Then added") {
                val resource = user.add(Gold(0.units))
                user.txResource(resource::class, 1.units).shouldBeSuccess()
                resource.owned shouldBeEqual 1.units
            }
        }

        describe("storageable") {
            it("Given nothing When add Then fail") {
                val resource = user.add(Food(0.units))
                user.txResource(resource::class, 1.units).shouldBeFail()// FIXME ("foo")
            }
        }
    }
}
