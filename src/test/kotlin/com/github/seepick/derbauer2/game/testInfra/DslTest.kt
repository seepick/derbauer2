package com.github.seepick.derbauer2.game.testInfra

import com.github.seepick.derbauer2.game.core.CollectingWarningListener
import com.github.seepick.derbauer2.game.gameModule
import com.github.seepick.derbauer2.game.view.textengineModule
import io.kotest.core.spec.Extendable
import io.kotest.koin.KoinExtension
import io.mockk.mockkClass
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest

// (also) marker interface for easier lookup (find implementation)
interface DslTest : KoinTest

fun dslTestModule() = module {
    singleOf(::CollectingWarningListener)
}

/** Does NOT initialize starting game assets; clean state for tests + more stability (independence). */
fun Extendable.installDslExtension() {
    extension(
        KoinExtension(
            modules = listOf(
                textengineModule(),
                gameModule(this::class),
                dslTestModule(),
            ),
            mockProvider = { mockkClass(it, relaxed = true) }
        )
    )
}
