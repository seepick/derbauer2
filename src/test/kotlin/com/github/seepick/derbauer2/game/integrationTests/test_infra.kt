package com.github.seepick.derbauer2.game.integrationTests

import com.github.seepick.derbauer2.game.DerBauer2
import com.github.seepick.derbauer2.game.gameModule
import com.github.seepick.derbauer2.textengine.mainWindowMatrixSize
import com.github.seepick.derbauer2.textengine.textengineModule
import io.kotest.core.spec.Extendable
import io.kotest.koin.KoinExtension
import io.mockk.mockkClass
import org.koin.test.KoinTest
import kotlin.reflect.KClass

fun Extendable.gameKoinExtension(){
    extension(
        // doc: https://insert-koin.io/docs/reference/koin-test/testing/
        KoinExtension(
            modules = listOf(
                textengineModule(DerBauer2.initPageClass, mainWindowMatrixSize),
                gameModule()
            ),
            mockProvider = { mockkClass(it, relaxed = true) }
        )
    )
}

fun <T> KoinTest.get(kClass: KClass<*>): T =
    getKoin().get(kClass)
