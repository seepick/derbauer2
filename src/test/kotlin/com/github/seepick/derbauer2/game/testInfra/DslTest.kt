package com.github.seepick.derbauer2.game.testInfra

import com.github.seepick.derbauer2.game.core.CollectingWarningListener
import com.github.seepick.derbauer2.game.gameModule
import com.github.seepick.derbauer2.game.prob.Probs
import com.github.seepick.derbauer2.game.prob.ProbsStub
import com.github.seepick.derbauer2.game.stat.EmptyGlobalStatModifierRepo
import com.github.seepick.derbauer2.game.stat.GlobalStatModifierRepo
import com.github.seepick.derbauer2.game.view.textengineModule
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import io.kotest.core.spec.Extendable
import io.kotest.koin.KoinExtension
import io.mockk.mockkClass
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest

// (also) marker interface for easier lookup (find implementation)
interface DslTest : KoinTest

private val log = logger {}

fun dslTestModule() = module {
    log.trace {
        "Declaring additional/overriding test koin beans: " +
                "CollectingWarningListener, ProbsStub, EmptyGlobalStatModifierRepo"
    }
    singleOf(::CollectingWarningListener)

    // override real ProbsImpl ones:
    single { ProbsStub() } bind Probs::class
    single { EmptyGlobalStatModifierRepo } bind GlobalStatModifierRepo::class
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
