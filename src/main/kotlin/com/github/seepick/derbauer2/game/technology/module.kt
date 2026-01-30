package com.github.seepick.derbauer2.game.technology

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun technologyModule() = module {
    singleOf(::TechnologyPage)
}
