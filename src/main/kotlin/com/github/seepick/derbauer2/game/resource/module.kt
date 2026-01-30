package com.github.seepick.derbauer2.game.resource

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun resourceModule() = module {
    single { ResourceTxValidator }
    singleOf(::ResourceTurner)
}
