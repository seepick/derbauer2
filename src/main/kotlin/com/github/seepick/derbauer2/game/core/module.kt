package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.getKoinBeansByType
import com.github.seepick.derbauer2.textengine.TextengineStateRepository
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass

fun coreModule(prefStatePath: KClass<*>) = module {
    single { User() }
    single { WarningBus(listeners = getKoinBeansByType<WarningListener>()) }

    // textengine requirements
    single { PreferencesStateRepository(prefStatePath) } bind TextengineStateRepository::class
}
