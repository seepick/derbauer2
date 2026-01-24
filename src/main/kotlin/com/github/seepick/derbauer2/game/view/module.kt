package com.github.seepick.derbauer2.game.view

import org.koin.dsl.module

fun gameViewModule() = module {
    single { HomePage(get(), get()) }
    single { GameRenderer(get()) }
    single { BuildingsPage(get(), get()) }
}
