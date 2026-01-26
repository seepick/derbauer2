package com.github.seepick.derbauer2.game.building

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun buildingModule() = module {
    singleOf(::BuildingsPage)
}
