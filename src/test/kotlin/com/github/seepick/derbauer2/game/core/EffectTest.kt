package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.common.emoji.Emoji
import com.github.seepick.derbauer2.common.unit.Z
import com.github.seepick.derbauer2.common.unit.z
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.ProducesResource
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.StorableResource
import com.github.seepick.derbauer2.game.resource.StoresResource
import com.github.seepick.derbauer2.game.resource.`food 🍖`
import com.github.seepick.derbauer2.game.resource.`gold 💰`
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.reflect.KClass

class EffectTest : DescribeSpec({
    describe("ProducesResource") {
        class ProducesResourceStub(
            override val producingResourceClass: KClass<out Resource>,
            override val produceResourceAmount: Z,
            override val label: String = "label",
        ) : Entity, ProducesResource {
            override fun deepCopy() = this
            fun toEffect() = ProducesResourceEffect(this)
        }

        fun List<Effect>.shouldContainOnlyEffect(other: ProducesResourceEffect<ProducesResourceStub>) {
            val me = shouldBeSingleton().first().shouldBeInstanceOf<ProducesResourceEffect<ProducesResourceStub>>()
            me.producingResourceClass shouldBe other.producingResourceClass
            me.produceResourceAmount shouldBe other.produceResourceAmount
        }
        it("Entity.toEffects") {
            val producing = ProducesResourceStub(Gold::class, 1.z)
            producing.toEffects().shouldContainOnlyEffect(ProducesResourceEffect(producing))
        }
        it("toFormattedString") {
            listOf(ProducesResourceStub(Gold::class, 1.z).toEffect())
                .toFormattedString() shouldBeEqual "+1/ ${Emoji.`gold 💰`}"
        }
    }
    describe("StoresResource") {
        class StoresResourceStub(
            override val storableResourceClass: KClass<out StorableResource>,
            override val storageAmount: Z,
            override val label: String = "label",
        ) : Entity, StoresResource {
            override fun deepCopy() = this
            fun toEffect() = StoresResourceEffect(this)
        }

        fun List<Effect>.shouldContainOnlyEffect(other: StoresResourceEffect<StoresResourceStub>) {
            val me = shouldBeSingleton().first().shouldBeInstanceOf<StoresResourceEffect<StoresResourceStub>>()
            me.storableResourceClass shouldBe other.storableResourceClass
            me.storageAmount shouldBe other.storageAmount
        }
        it("Entity.toEffects") {
            val storing = StoresResourceStub(Food::class, 1.z)
            storing.toEffects().shouldContainOnlyEffect(StoresResourceEffect(storing))
        }
        it("toFormattedString") {
            listOf(StoresResourceStub(Food::class, 10.z).toEffect())
                .toFormattedString() shouldBeEqual "/+10 ${Emoji.`food 🍖`}"
        }
    }
})
