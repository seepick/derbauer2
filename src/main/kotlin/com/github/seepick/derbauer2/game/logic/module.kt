package com.github.seepick.derbauer2.game.logic

import org.koin.dsl.module

fun gameLogicModule() = module {
    single { Game() }
    single { get<Game>().user }
}
