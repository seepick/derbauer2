package com.github.seepick.derbauer2.game.common

import java.util.Locale
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.max

/** Indicating the annotated code was entirely or at least mostly written by an LLM. */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.FILE)
annotation class AiGenerated

@OptIn(ExperimentalContracts::class)
fun ifDo(condition: Boolean, execution: () -> Unit): Boolean {
    contract {
        returns(true) implies condition
        callsInPlace(execution, InvocationKind.AT_MOST_ONCE)
    }
    if (condition) execution()
    return condition
}

@OptIn(ExperimentalContracts::class)
fun <T> List<T>.ifNotEmpty(code: (List<T>) -> Unit) {
    contract {
        callsInPlace(code, InvocationKind.AT_MOST_ONCE)
    }
    if (isNotEmpty()) {
        code(this)
    }
}

fun <T, R> List<T>.requireUniqueBy(prefixMessage: String? = null, fieldExtractor: (T) -> R) {
    val distinctFields = map(fieldExtractor).distinct()
    require(this.size == distinctFields.size) {
        val duplicates = distinctFields.filter { field -> this.count { fieldExtractor(it) == field } > 1 }
        (if (prefixMessage != null) "$prefixMessage\n" else "") + "Found duplicates for field: $duplicates"
    }
}

@AiGenerated
fun Double.toFormatted(): String {
    val absValue = abs(this)
    val decimals = if (absValue == 0.0) 2 else max(2, -floor(log10(absValue)).toInt() + 1)
    return String.format(Locale.ENGLISH, "%.${decimals}f", this)
}
