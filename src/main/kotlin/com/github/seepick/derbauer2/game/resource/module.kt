package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.transaction.TxValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun resourceModule() = module {
    single { ResourceTxValidator() } bind TxValidator::class
    singleOf(::ResourceProducingResourceTurnStep)
}
