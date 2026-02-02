package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.BuildingTxValidator
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.ResourceTxValidator

enum class TxValidatorType(val validator: TxValidator) {
    Building(BuildingTxValidator),
    Resource(ResourceTxValidator);

    companion object {
        // must be lazy, as entries are not initialized at object creation time :-/
        val all by lazy { TxValidatorType.entries.map { it.validator } }
    }
}

interface TxValidator {
    val type: TxValidatorType
    fun validateTx(user: User): TxResult
}
