package com.github.seepick.derbauer2.game.interaction

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun interactionModule() = module {
    singleOf(::Interaction)
}
