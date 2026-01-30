package com.github.seepick.derbauer2.game.integrationTests.testInfra

import com.github.seepick.derbauer2.game.gameModule
import com.github.seepick.derbauer2.textengine.compose.textengineModule
import io.kotest.core.spec.Extendable
import io.kotest.koin.KoinExtension
import io.mockk.mockkClass
import org.koin.test.KoinTest
import kotlin.reflect.KClass

fun Extendable.installGameKoinExtension() {
    extension(
        KoinExtension(
            modules = listOf(
                textengineModule(),
                gameModule()
            ),
            mockProvider = { mockkClass(it, relaxed = true) }
        )
    )
}

fun <T> KoinTest.get(kClass: KClass<*>): T =
    getKoin().get(kClass)
