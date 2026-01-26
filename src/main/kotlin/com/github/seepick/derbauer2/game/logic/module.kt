package com.github.seepick.derbauer2.game.logic

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun logicModule() = module {
    singleOf(::User)
}
