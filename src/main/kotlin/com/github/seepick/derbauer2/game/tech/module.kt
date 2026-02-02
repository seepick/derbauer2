package com.github.seepick.derbauer2.game.tech

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun techModule() = module {
    singleOf(::TechPage)
    single {
        TechTree(
            all = TechType.entries.map { type ->
                type.treeItemBuilder()
            }
        )
    }
}
