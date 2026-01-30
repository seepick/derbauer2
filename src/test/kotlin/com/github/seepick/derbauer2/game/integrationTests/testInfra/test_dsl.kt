package com.github.seepick.derbauer2.game.integrationTests.testInfra

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.initAssets
import com.github.seepick.derbauer2.game.ownedForTest
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.audio.Beeper
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.koin.test.mock.declareMock

@DslMarker
annotation class GameDsl

interface GameKoinTestContext {
    val koin: KoinTest
    val user: User get() = koin.get()
    val page: Page get() = koin.get(koin.get<CurrentPage>().pageClass)
    @Suppress("UNCHECKED_CAST")
    fun <P : Page> pageAs() = page as P
}

@Suppress("TestFunctionName")
context(koin: KoinTest)
fun Given(code: GivenDsl.() -> Unit): GivenDsl {
    koin.declareMock<Beeper> {
        every { beep(any()) } answers {
            println("TEST beep for reason=[${arg<String>(0)}]")
        }
    }
    val user by koin.inject<User>()
    user.initAssets()
    return GivenDsl(koin).apply(code)
}

@GameDsl
class GivenDsl(override val koin: KoinTest) : KoinTest by koin, GameKoinTestContext {
    inline fun <reified A : Asset> setOwned(amount: Z) {
        val ownable = user.all.find<A>()
        ownable.ownedForTest = amount
    }

    inline fun <reified A : Asset> changeOwned(amount: Z) {
        val ownable = user.all.find<A>()
        ownable.ownedForTest += amount
    }
}

@Suppress("TestFunctionName")
infix fun GivenDsl.When(code: WhenDsl.() -> Unit) =
    WhenDsl(koin).apply(code)

@GameDsl
class WhenDsl(override val koin: KoinTest) : KoinTest by koin, GameKoinTestContext {
    private val log = logger {}

    fun input(key: KeyInput) {
        log.debug { "Input ${key::class.simpleName} (page = ${page::class.simpleName})" }
        page.onKeyPressed(key.asKeyPressed)
    }

    fun selectPrompt(searchLabel: String) {
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
}
