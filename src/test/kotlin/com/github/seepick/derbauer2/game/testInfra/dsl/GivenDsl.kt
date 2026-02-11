package com.github.seepick.derbauer2.game.testInfra.dsl

import com.github.seepick.derbauer2.common.ListX
import com.github.seepick.derbauer2.common.unit.StrictDouble
import com.github.seepick.derbauer2.common.unit.Z
import com.github.seepick.derbauer2.common.unit.z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.happening.HappeningRef
import com.github.seepick.derbauer2.game.happening.happenings.HappeningRefRegistry
import com.github.seepick.derbauer2.game.initGame
import com.github.seepick.derbauer2.game.prob.disableAllProbs
import com.github.seepick.derbauer2.game.stat.Stat
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import com.github.seepick.derbauer2.game.view.WhenHomePageDsl
import com.github.seepick.derbauer2.textengine.audio.SoundPlayer
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

private val log = logger {}

@Suppress("TestFunctionName")
context(koin: KoinTest)
fun Given(
    initAssets: Boolean = false,
    disableAllProbs: Boolean = true,
    code: GivenDsl.() -> Unit,
): GivenDsl {
    koin.declareMock<SoundPlayer> {
        every { play(any()) } just Runs
//            log.debug { "TEST beep for reason=[${arg<String>(0)}]" }
    }
    koin.getKoin().initGame(initAssets = initAssets)

    val givenDsl = GivenDsl(koin)
    if (disableAllProbs) {
        givenDsl.disableAllProbs()
    }
    return givenDsl.apply(code)
}

@TestDsl
class GivenDsl(override val koin: KoinTest) : KoinTest by koin, DslContext {

    fun registerHappeningRef(vararg refs: HappeningRef) {
        koin.declareMock<HappeningRefRegistry> {
            every { all } answers { refs.toList() }
        }
    }

    inline fun <reified A : Asset> setOwned(amount: Z = 0.z): A =
        user.all.findOrAdd<A>().also {
            it.ownedForTest = amount
        }

    inline fun <reified S : Stat<StrictDouble.MinusOneToOne>> setStatD11(number: StrictDouble.MinusOneToOne): S =
        user.all.findOrAdd<S>().apply {
            changeTo(number)
        }

    inline fun <reified E : Entity> ListX<in E>.findOrAdd(): E =
        findOrNull<E>() ?: newEntity()

    inline fun <reified E : Entity> newEntity(): E {
        val primaryConstructor = E::class.primaryConstructor
            ?: error("Unable to get primary ctor for: ${E::class.qualifiedName}")
        val entity = try {
            primaryConstructor.callBy(emptyMap()) // call() doesn't support default params, thus.
        } catch (e: Exception) {
            throw EntityConstructorNotFoundException(E::class, e)
        }
        user.add(entity)
        return entity
    }
}

class EntityConstructorNotFoundException(entityClass: KClass<*>, cause: Throwable) :
    IllegalArgumentException("Unable to find 0-arg constructor for entity class: ${entityClass.simpleName}", cause)

@Suppress("TestFunctionName")
infix fun GivenDsl.When(code: WhenHomePageDsl.() -> Unit) = WhenHomePageDsl(WhenDslImpl(koin)).apply(code)
