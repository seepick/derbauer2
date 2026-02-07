package com.github.seepick.derbauer2.game.stat

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun statModule() = module {
    single { StatTurnStep(user = get()) }
    singleOf(::HappinessCitizenModifier)
}
