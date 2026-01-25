package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.HomePage
import com.github.seepick.derbauer2.game.turn.ReportPage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun viewModule() = module {
    singleOf(::HomePage)
    singleOf(::GameRenderer)
    singleOf(::ReportPage)
}
