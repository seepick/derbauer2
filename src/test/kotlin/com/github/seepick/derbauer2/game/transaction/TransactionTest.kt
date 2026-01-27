package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Land
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.should
import io.kotest.matchers.types.shouldBeInstanceOf

class TransactionTest : DescribeSpec({
    describe("When changing resource and storage") {
        it("Given no storage When adding resource Then fail") {
            val user = User()
            user.add(Food(0.z))

            user.execTx(Tx.TxResource(Food::class, 1.zz))
                .shouldBeInstanceOf<TxResult.Fail>().message.should {
                    it.lowercase().contains("not enough storage")
                }
        }
        it("Given enough storage When adding resource Then succeed") {
            val user = User()
            user.add(Granary(1.z))
            user.add(Food(0.z))

            user.execTx(Tx.TxResource(Food::class, 1.zz))
                .shouldBeSuccess()
        }
        it("Given enough storage When adding resource and removing storage Then fail") {
            val user = User()
            user.add(Land(20.z))
            user.add(Granary(1.z))
            user.add(Food(0.z))

            user.execTx(
                Tx.TxResource(Food::class, 1.zz),
                Tx.TxBuild(Granary::class) // FIXME -1.zz
            )
                .shouldBeSuccess()
        }
    }
})
