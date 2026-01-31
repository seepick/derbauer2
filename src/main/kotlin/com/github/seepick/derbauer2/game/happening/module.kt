package com.github.seepick.derbauer2.game.happening

import com.github.seepick.derbauer2.game.happening.happenings.DefaultHappeningDescriptorRepo
import com.github.seepick.derbauer2.game.happening.happenings.HappeningDescriptorRepo
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun happeningModule() = module {
    singleOf(::HappeningPage)
    singleOf(::HappeningMultiView)
    // have to be created at start to register providers/selectors
    single(createdAtStart = true) { HappeningTurner(get(), get(), get()) }
    single { DefaultHappeningDescriptorRepo } bind HappeningDescriptorRepo::class
}
