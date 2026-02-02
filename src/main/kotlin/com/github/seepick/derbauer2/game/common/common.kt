package com.github.seepick.derbauer2.game.common

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

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
