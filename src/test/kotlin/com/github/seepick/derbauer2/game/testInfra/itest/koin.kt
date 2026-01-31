package com.github.seepick.derbauer2.game.testInfra.itest

import com.github.seepick.derbauer2.game.core.Warning
import com.github.seepick.derbauer2.game.core.WarningListener
import com.github.seepick.derbauer2.game.gameModule
import com.github.seepick.derbauer2.game.view.textengineModule
import io.kotest.core.spec.Extendable
import io.kotest.koin.KoinExtension
import io.mockk.mockkClass
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.reflect.KClass

class CollectingWarningListener : WarningListener {
    val warnings = mutableListOf<Warning>()
    override fun onWarning(warning: Warning) {
        warnings += warning
    }
}

fun itestModule() = module {
    singleOf(::CollectingWarningListener)
}

fun Extendable.installGameKoinExtension() {
    extension(
        KoinExtension(
            modules = listOf(
                textengineModule(),
                gameModule(),
                itestModule(),
            ),
            mockProvider = { mockkClass(it, relaxed = true) }
        )
    )
}

fun <T> KoinTest.get(kClass: KClass<*>): T =
    getKoin().get(kClass)
