package com.github.seepick.derbauer2.game.testInfra.itest

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.happening.happenings.HappeningDescriptor
import com.github.seepick.derbauer2.game.happening.happenings.HappeningDescriptorRepo
import com.github.seepick.derbauer2.game.initAssets
import com.github.seepick.derbauer2.game.probability.Probabilities
import com.github.seepick.derbauer2.game.probability.ProbabilitiesImpl
import com.github.seepick.derbauer2.game.probability.ProbabilityCalculator
import com.github.seepick.derbauer2.game.probability.ProbabilityInitializer
import com.github.seepick.derbauer2.game.probability.ProbabilityProviderHandle
import com.github.seepick.derbauer2.game.probability.ProbabilityProviderSource
import com.github.seepick.derbauer2.game.probability.ProbabilitySelector
import com.github.seepick.derbauer2.game.probability.ProbabilitySelectorHandle
import com.github.seepick.derbauer2.game.probability.ProbabilitySelectorSource
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.audio.Beeper
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.assertions.withClue
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.every
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.mock.declareMock
import kotlin.reflect.full.primaryConstructor

private val log = logger {}

interface GameKoinTestContext {
    val koin: KoinTest
    val user: User get() = koin.get()
    val page: Page get() = koin.get(koin.get<CurrentPage>().pageClass)

}

@Suppress("UNCHECKED_CAST")
inline fun <reified P : Page> GameKoinTestContext.pageAs(): P {
    return page.shouldBeInstanceOf<P>()
}

@DslMarker
annotation class GameDsl

@Suppress("TestFunctionName")
context(koin: KoinTest)
fun Given(initAssets: Boolean = false, code: GivenDsl.() -> Unit): GivenDsl {
    koin.declareMock<Beeper> {
        every { beep(any()) } answers {
            log.debug { "TEST beep for reason=[${arg<String>(0)}]" }
        }
    }
    koin.get<ProbabilityInitializer>().registerAll()
    if (initAssets) {
        koin.get<User>().initAssets()
    }
    return GivenDsl(koin).apply(code)
}

@GameDsl
class GivenDsl(override val koin: KoinTest) : KoinTest by koin, GameKoinTestContext {

    fun mockHappeningDescriptorRepoReturns(vararg descriptors: HappeningDescriptor) {
        koin.declareMock<HappeningDescriptorRepo> {
            every { all } answers { descriptors.toList() }
        }
    }

    inline fun <reified A : Asset> setOwned(amount: Z) {
        val asset = user.all.findOrSet<A>()
        asset.ownedForTest = amount
    }

    inline fun <reified A : Asset> changeOwned(amount: Z) {
        val ownable = user.all.findOrSet<A>()
        ownable.ownedForTest += amount
    }

    inline fun <reified A : Asset> ListX<in A>.findOrSet(): A = findOrNull<A>() ?: createAssetInstance()

    inline fun <reified A : Asset> createAssetInstance(): A {
        val asset = A::class.primaryConstructor!!.call()
        user.enable(asset)
        return asset
    }

    fun probability(code: ProbabilityDsl.() -> Unit) {
        ProbabilityDsl(koin).code()
    }
}

@TestEngineDsl
class ProbabilityDsl(private val koin: KoinTest) : ProbalityProviderAddSourceAndCalc,
    ProbalitySelectorAddSourceAndSelector {
    var providers = ProvidersChangeDsl(this)
    var selectors = SelectorsChangeDsl(this)

    override operator fun plus(
        sourceAndCalc: Pair<ProbabilityProviderSource, ProbabilityCalculator>
    ): ProvidersChangeDsl {
        val probabilities = koin.get<Probabilities>() as ProbabilitiesImpl
        probabilities.providerHandles.updateCalc(sourceAndCalc.first, sourceAndCalc.second)
        return providers
    }

    override fun plus(
        sourceAndCalc: Pair<ProbabilitySelectorSource, ProbabilitySelector<out Any>>
    ): SelectorsChangeDsl {
        val probabilities = koin.get<Probabilities>() as ProbabilitiesImpl
        probabilities.selectorHandles.updateSelect(sourceAndCalc.first, sourceAndCalc.second)
        return selectors
    }
}

interface ProbalityProviderAddSourceAndCalc {
    operator fun plus(sourceAndCalc: Pair<ProbabilityProviderSource, ProbabilityCalculator>): ProvidersChangeDsl
}

class ProvidersChangeDsl(delegate: ProbalityProviderAddSourceAndCalc) : ProbalityProviderAddSourceAndCalc by delegate

interface ProbalitySelectorAddSourceAndSelector {
    operator fun plus(sourceAndCalc: Pair<ProbabilitySelectorSource, ProbabilitySelector<out Any>>): SelectorsChangeDsl
}

class SelectorsChangeDsl(delegate: ProbalitySelectorAddSourceAndSelector) :
    ProbalitySelectorAddSourceAndSelector by delegate


fun MutableMap<ProbabilityProviderSource, ProbabilityProviderHandle<Any>>.updateCalc(
    source: ProbabilityProviderSource, calc: ProbabilityCalculator
) {
    val providerProvider = this[source]!!
    this[source] = providerProvider.copy(calculator = calc)
}

fun MutableMap<ProbabilitySelectorSource, ProbabilitySelectorHandle<Any>>.updateSelect(
    source: ProbabilitySelectorSource, newSelector: ProbabilitySelector<out Any>
) {
    val selectorSelector = this[source]!!
    this[source] = selectorSelector.withSelector(newSelector) // copy didnt work properly; generics issue
}

@Suppress("TestFunctionName")
infix fun GivenDsl.When(code: WhenHomePageDsl.() -> Unit) = WhenHomePageDsl(WhenDslImpl(koin)).apply(code)

interface WhenDsl : KoinTest, GameKoinTestContext {
    fun input(key: KeyInput)
    fun selectPrompt(searchLabel: String)
}

@GameDsl
class WhenDslImpl(override val koin: KoinTest) : WhenDsl, KoinTest by koin, GameKoinTestContext {
    private val log = logger {}

    override fun input(key: KeyInput) {
        log.debug { "Input ${key::class.simpleName} (page = ${page::class.simpleName})" }
        page.onKeyPressed(key.asKeyPressed)
    }

    override fun selectPrompt(searchLabel: String) {
        val optionIndex = pageAs<PromptGamePage>().indexOfOption(searchLabel)
        input(KeyInput.byNr(optionIndex))
    }
}

@Suppress("TestFunctionName")
infix fun WhenDsl.Then(code: ThenDsl.() -> Unit) {
    ThenDsl(koin).apply(code)
}

@GameDsl
class ThenDsl(override val koin: KoinTest) : KoinTest by koin, GameKoinTestContext {

    inline fun <reified A : Asset> shouldOwn(expectedAmount: Z) {
        val asset = user.all.find<A>()
        asset.owned shouldBeEqual expectedAmount
    }

    fun shouldRaiseWarning(containsMessage: String) {
        val warnings = koin.get<CollectingWarningListener>().warnings
        withClue({ "Expected warnings to contain [$containsMessage] but was:\n${warnings.map { it.message }}" }) {
            warnings.any { it.message.contains(containsMessage, ignoreCase = true) } shouldBeEqual true
        }
    }
}
