package com.github.seepick.derbauer2.game.transaction

import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf

fun TxResult.shouldBeSuccess() {
    this shouldBeEqual TxResult.Success
}

fun TxResult.shouldBeFail(vararg reasonContains: String) {
    this.shouldBeInstanceOf<TxResult.Fail>().also {
        reasonContains.forEach { expected ->
            it.reason shouldContain expected
        }
    }
}
