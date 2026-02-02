package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.transaction.TxValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun buildingModule() = module {
    singleOf(::BuildingsPage)
    single { BuildingTxValidator() } bind TxValidator::class
}
