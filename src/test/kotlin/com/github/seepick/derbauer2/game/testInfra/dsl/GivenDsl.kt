package com.github.seepick.derbauer2.game.testInfra.dsl

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.happening.HappeningDescriptor
import com.github.seepick.derbauer2.game.happening.HappeningDescriptorRepo
import com.github.seepick.derbauer2.game.happening.happeningTurner
import com.github.seepick.derbauer2.game.initAssets
import com.github.seepick.derbauer2.game.prob.AlwaysFalseProbCalculator
import com.github.seepick.derbauer2.game.prob.ProbProviderKey
import com.github.seepick.derbauer2.game.prob.ProbRegistrator
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.prob.ProbsImpl
import com.github.seepick.derbauer2.game.prob.updateProvider
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import com.github.seepick.derbauer2.game.view.WhenHomePageDsl
import com.github.seepick.derbauer2.textengine.audio.Beeper
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.mockk.every
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.mock.declareMock
import kotlin.reflect.full.primaryConstructor

private val log = logger {}

@Suppress("TestFunctionName")
context(koin: KoinTest)
fun Given(initAssets: Boolean = false, code: GivenDsl.() -> Unit): GivenDsl {
    koin.declareMock<Beeper> {
        every { beep(any()) } answers {
            log.debug { "TEST beep for reason=[${arg<String>(0)}]" }
        }
    }
    koin.get<ProbRegistrator>().registerAll()
    if (initAssets) {
        koin.get<User>().initAssets()
    }
    return GivenDsl(koin).apply(code)
}

@TestDsl
class GivenDsl(override val koin: KoinTest) : KoinTest by koin, DslContext {

    fun mockHappeningDescriptorRepoReturns(vararg descriptors: HappeningDescriptor) {
        koin.declareMock<HappeningDescriptorRepo> {
            every { getAllDescriptors() } answers { descriptors.toList() }
        }
    }

    inline fun <reified A : Asset> setOwned(amount: Z): A {
        val asset = user.all.findOrSet<A>()
        asset.ownedForTest = amount
        return asset
    }

    inline fun <reified A : Asset> ListX<in A>.findOrSet(): A = findOrNull<A>() ?: createAssetInstance()

    inline fun <reified A : Asset> createAssetInstance(): A {
        val asset = A::class.primaryConstructor!!.call()
        user.add(asset)
        return asset
    }

    fun turnOff(off: TurnOff) {
        when (off) {
            TurnOff.Happenings -> {
                val probs = koin.get<Probs>() as ProbsImpl
                probs.updateProvider(ProbProviderKey.happeningTurner, AlwaysFalseProbCalculator)
            }
        }
    }
}

enum class TurnOff {
    Happenings
}

@Suppress("TestFunctionName")
infix fun GivenDsl.When(code: WhenHomePageDsl.() -> Unit) = WhenHomePageDsl(WhenDslImpl(koin)).apply(code)
