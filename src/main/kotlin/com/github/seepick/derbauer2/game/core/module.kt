package com.github.seepick.derbauer2.game.core

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun coreModule() = module {
    singleOf(::User)
}
