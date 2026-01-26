package com.github.seepick.derbauer2.textengine

import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass

fun textengineModule(initPageClass: KClass<out Page>, textmapSize: MatrixSize) = module {
    single { CurrentPage(initPageClass) }
    single { Textmap(textmapSize.cols, textmapSize.rows) }
    single { RealBeeper }.bind<Beeper>()
}
