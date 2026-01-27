package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.Granary
import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.building.TxBuilding
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.enableAndSet
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.TxResource
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.types.shouldBeInstanceOf

class TransactionTest : DescribeSpec({
    describe("When changing resource and storage") {
        it("Given nothing When adding non-existing resource Then fail") {
            shouldThrow<IllegalArgumentException> {
                User().execTx(TxResource(Land::class, 1.zz))
            }.message.shouldNotBeNull().should {
                it.contains("nothing found")
                it.contains("Land")
            }
        }

        it("Given zero gold When remove gold Then fail") {
            val user = User()
            user.enable(Gold())

            user.execTx(TxResource(Gold::class, (-1).zz))
                .shouldBeInstanceOf<TxResult.Fail>().message.should {
                    it.lowercase().contains("negative")
                }
        }

        it("Given zero land When build Then fail") {
            val user = User()
            user.enable(Land())
            user.enable(House())

            user.execTx(TxBuilding(House::class, 1.zz))
                .shouldBeInstanceOf<TxResult.Fail>().message.should {
                    it.lowercase().contains("FIXME building not enough land") // FIXME change msg
                }
        }
        it("Given no storage When adding resource Then fail") {
            val user = User()
            user.enable(Land())
            user.enable(Food())

            user.execTx(TxResource(Food::class, 1.zz))
                .shouldBeInstanceOf<TxResult.Fail>().message.should {
                    it.lowercase().contains("not enough storage")
                }
        }
        it("Given enough storage When adding resource Then succeed") {
            val user = User()
            user.enableAndSet(Land(), 50.z)
            user.enableAndSet(Granary(), 1.z)
            user.enable(Food())

            user.execTx(TxResource(Food::class, 1.zz)).shouldBeSuccess()
        }
        it("Given enough storage When adding resource and removing storage Then fail") {
            val user = User()
            user.enableAndSet(Land(), 20.z)
            user.enableAndSet(Granary(), 1.z)
            user.enable(Food())

            user.execTx(
                TxResource(Food::class, 1.zz),
                TxBuilding(Granary::class, (-1).zz)
            ).shouldBeFail("Not enough storage")
        }
    }
})
