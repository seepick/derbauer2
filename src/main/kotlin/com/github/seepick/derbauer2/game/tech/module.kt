package com.github.seepick.derbauer2.game.tech

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun techModule() = module {
    singleOf(::TechPage)
    singleOf(::TechService)
    single { DefaultTechItemRepo } bind TechItemRepo::class
    single { TechTree(techs = get<TechItemRepo>().items, user = get()) }
}
