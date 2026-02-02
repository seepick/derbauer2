package com.github.seepick.derbauer2.game.testInfra.dsl

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.CollectingWarningListener
import io.kotest.assertions.withClue
import io.kotest.matchers.equals.shouldBeEqual
import org.koin.test.KoinTest
import org.koin.test.get

@TestDsl
class ThenDsl(override val koin: KoinTest) : KoinTest by koin, DslContext {

    inline fun <reified A : Asset> shouldOwn(expectedAmount: Z) {
        val asset = user.all.find<A>()
        asset.owned shouldBeEqual expectedAmount
    }

    fun shouldRaiseWarning(containsMessage: String) {
        val warnings = koin.get<CollectingWarningListener>().warnings
        withClue({ "Expected warnings to contain [$containsMessage] but was:\n${warnings.map { it.message }}" }) {
            warnings.any { it.message.contains(containsMessage, ignoreCase = true) } shouldBeEqual true
        }
    }
}
