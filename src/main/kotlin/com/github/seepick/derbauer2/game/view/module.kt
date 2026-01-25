package com.github.seepick.derbauer2.game.view

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun gameViewModule() = module {
    singleOf(::HomePage)
    singleOf(::GameRenderer)
    singleOf(::BuildingsPage)
}
