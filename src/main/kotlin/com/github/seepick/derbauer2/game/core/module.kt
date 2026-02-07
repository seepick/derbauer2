package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.getKoinBeansByType
import com.github.seepick.derbauer2.game.transaction.TxValidatorRepo
import com.github.seepick.derbauer2.textengine.TextengineStateRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass

fun coreModule(prefStatePath: KClass<*>) = module {
    single { User(txValidators = get<TxValidatorRepo>().validators) }
    single { WarningBus(listeners = getKoinBeansByType<WarningListener>()) }

    // textengine requirements
    single { PreferencesStateRepository(prefStatePath) } bind TextengineStateRepository::class
    single { ActionBusImpl(listeners = getKoinBeansByType<ActionBusListener>()) } bind ActionBus::class
    singleOf(::ActionsCollector)
}
