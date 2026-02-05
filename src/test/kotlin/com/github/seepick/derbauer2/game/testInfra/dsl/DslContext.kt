package com.github.seepick.derbauer2.game.testInfra.dsl

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.testInfra.get
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import io.kotest.matchers.types.shouldBeInstanceOf
import org.koin.test.KoinTest
import org.koin.test.get

interface DslContext {
    val koin: KoinTest
    val user: User get() = koin.get()
    val page: Page get() = koin.get(koin.get<CurrentPage>().pageClass)

    fun printPage() {
        val textmap = koin.get<Textmap>()
        textmap.reset()
        page.invalidate()
        page.render(textmap)
        println(textmap.toFullString())
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified P : Page> DslContext.pageAs(): P =
    page.shouldBeInstanceOf<P>()
