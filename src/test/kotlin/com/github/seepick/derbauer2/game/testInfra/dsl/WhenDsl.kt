package com.github.seepick.derbauer2.game.testInfra.dsl

import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.textengine.KeyInput
import com.github.seepick.derbauer2.textengine.indexOfOption
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.test.KoinTest

interface WhenDsl : KoinTest, DslContext {
    fun input(key: KeyInput)
    fun selectPrompt(searchLabel: String)
}

@TestDsl
class WhenDslImpl(override val koin: KoinTest) : WhenDsl, KoinTest by koin, DslContext {
    private val log = logger {}

    override fun input(key: KeyInput) {
        log.debug { "Input ${key::class.simpleName} (page = ${page::class.simpleName})" }
        page.onKeyPressed(key.asKeyPressed)
    }

    override fun selectPrompt(searchLabel: String) {
        printPage()
        val optionIndex = pageAs<PromptGamePage>().prompt.indexOfOption(searchLabel)
        input(KeyInput.byNr(optionIndex))
    }
}

@Suppress("TestFunctionName")
infix fun WhenDsl.Then(code: ThenDsl.() -> Unit) {
    ThenDsl(koin).apply(code)
}
