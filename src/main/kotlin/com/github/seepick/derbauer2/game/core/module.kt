package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.DerBauer2
import com.github.seepick.derbauer2.game.common.getAllCustom
import com.github.seepick.derbauer2.game.transaction.TxValidator
import com.github.seepick.derbauer2.textengine.TextengineStateRepository
import org.koin.dsl.bind
import org.koin.dsl.module

fun coreModule() = module {
    single { User(txValidators = getAllCustom<TxValidator>()) }
    single { WarningBus(listeners = getAllCustom<WarningListener>()) }

    // textengine requirements
    single { PreferencesStateRepository(DerBauer2::class) } bind TextengineStateRepository::class
}
