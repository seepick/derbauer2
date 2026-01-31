package com.github.seepick.derbauer2.textengine

import com.github.seepick.derbauer2.textengine.audio.Beeper
import com.github.seepick.derbauer2.textengine.audio.BeepingWarningListener
import com.github.seepick.derbauer2.textengine.audio.RealBeeper
import com.github.seepick.derbauer2.textengine.compose.MainWin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass

fun textengineModule(initPageClass: KClass<out Page>) = module {
    single { CurrentPage(initPageClass) }
    single { Textmap(MainWin.matrixSize) }
    single { RealBeeper }.bind<Beeper>()
    singleOf(::BeepingWarningListener)
}
