package com.github.seepick.derbauer2.game.tech

import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val log = logger {}

fun techModule() = module {
    singleOf(::TechPage)
    single { DefaultTechItemRepo } bind TechItemRepo::class
    single { TechTree(all = get<TechItemRepo>().items) }
}
