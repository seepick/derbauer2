package com.github.seepick.derbauer2.textengine

import com.github.seepick.derbauer2.textengine.audio.Beeper
import com.github.seepick.derbauer2.textengine.audio.RealBeeper
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass

fun textengineModule(initPageClass: KClass<out Page>, textmapSize: MatrixSize) = module {
    single { CurrentPage(initPageClass) }
    single { Textmap(textmapSize) }
    single { RealBeeper }.bind<Beeper>()
}
