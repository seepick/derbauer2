package com.github.seepick.derbauer2.engine

import org.koin.dsl.module
import kotlin.reflect.KClass

fun engineModule(initPage: KClass<out Page>, textmapSize: MatrixSize) = module {
    single { CurrentPage(initPage) }
    single { Textmap(textmapSize.cols, textmapSize.rows) }
}
