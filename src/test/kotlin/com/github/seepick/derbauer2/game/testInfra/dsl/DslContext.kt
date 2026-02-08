package com.github.seepick.derbauer2.game.testInfra.dsl

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.testInfra.get
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.matchers.types.shouldBeInstanceOf
import org.koin.test.KoinTest
import org.koin.test.get

private val log = logger {}

interface DslContext {

    val koin: KoinTest
    val user: User get() = koin.get()
    val page: Page get() = koin.get(koin.get<CurrentPage>().pageClass)
    val turn: Int get() = koin.get<CurrentTurn>().current.number

    fun logPage() {
        val textmap = koin.get<Textmap>()
        textmap.clear()
        page.invalidate()
        page.render(textmap)
        log.info { "Page ${page::class.simpleName}: ${textmap.toFullString()}" }
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified P : Page> DslContext.pageAs(): P =
    page.shouldBeInstanceOf<P>()
