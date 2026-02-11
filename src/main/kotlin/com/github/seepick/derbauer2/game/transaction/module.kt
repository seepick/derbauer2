package com.github.seepick.derbauer2.game.transaction

import org.koin.dsl.bind
import org.koin.dsl.module

fun transactionModule() = module {
    single { ClasspathScanTxValidatorRegistry } bind TxValidatorRegistry::class
}
