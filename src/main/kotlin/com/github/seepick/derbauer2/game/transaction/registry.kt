package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.common.ClasspathScanner
import com.github.seepick.derbauer2.game.core.global

interface TxValidatorRegistry {
    fun getAll(): List<TxValidator>
}

object ClasspathScanTxValidatorRegistry : TxValidatorRegistry {
    private val validators by lazy {
        ClasspathScanner.global.scanObjectsImplementing(TxValidator::class)
    }

    override fun getAll() = validators
}
