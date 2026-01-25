package com.github.seepick.derbauer2.game.happening

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun happeningModule() = module {
    singleOf(::HappeningPage)
    singleOf(::HappeningController)
    singleOf(::HappeningTurner)
}
