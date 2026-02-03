package com.github.seepick.derbauer2.game.citizen

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun citizenModule() = module {
    singleOf(::CitizenTurnStep)
    singleOf(::TaxesTurnStep)
}
