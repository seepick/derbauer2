package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.happening.happenings.DefaultHappeningRefRegistry
import com.github.seepick.derbauer2.game.happening.happenings.HappeningRefRegistry
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun happeningModule() = module {
    singleOf(::HappeningPage)
    singleOf(::HappeningMultiView)
    singleOf(::HappeningTurner)
    single { DefaultHappeningRefRegistry } bind HappeningRefRegistry::class
}
