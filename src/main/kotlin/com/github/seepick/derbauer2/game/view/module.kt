package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.turn.ReportPage
import com.github.seepick.derbauer2.textengine.textengineModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun viewModule() = module {
    singleOf(::HomePage)
    singleOf(::GameRenderer)
    singleOf(::ReportPage)
    singleOf(::TxResultHandler)
    singleOf(::GameOverPage)
}

fun textengineModule() = textengineModule(HomePage::class)
