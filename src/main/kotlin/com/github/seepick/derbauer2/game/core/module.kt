package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.getAllCustom
import com.github.seepick.derbauer2.game.transaction.TxValidator
import org.koin.dsl.module

fun coreModule() = module {
    single {
        User(
            txValidators = getAllCustom<TxValidator>(),
        )
    }
}
