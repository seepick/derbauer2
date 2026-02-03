package com.github.seepick.derbauer2.game.testInfra

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Ownable
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldContainIgnoringCase
import org.koin.test.KoinTest
import kotlin.reflect.KClass

// marker interface for easier lookup (find implementation)
interface PageTest

var Ownable.ownedForTest
    get() = owned
    set(value) {
        _setOwnedInternal = value
    }

@Suppress("VariableMinLength")
val Double.z get() = toLong().z

@Suppress("VariableMinLength")
val Double.zz get() = toLong().zz

fun <T> KoinTest.get(klass: KClass<*>): T =
    getKoin().get(klass)

fun String?.shouldContainInAnyOrder(vararg strings: String, ignoreCase: Boolean = false) {
    this.shouldNotBeNull()
    strings.forEach { string ->
        if (ignoreCase) {
            this.shouldContainIgnoringCase(string)
        } else {
            this.shouldContain(string)
        }
    }
}
