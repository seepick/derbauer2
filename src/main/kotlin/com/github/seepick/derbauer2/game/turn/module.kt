package com.github.seepick.derbauer2.game.turn

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun turnModule() = module {
    singleOf(::Turner)
}
