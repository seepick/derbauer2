package com.github.seepick.derbauer2.game.logic

import org.koin.dsl.module

fun logicModule() = module {
    single { User() }
}
