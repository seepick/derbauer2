package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.BuildingReference
import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.units
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceReference
import com.github.seepick.derbauer2.game.transaction.TxResult.Fail
import com.github.seepick.derbauer2.game.transaction.TxResult.Success
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.math.abs
import kotlin.reflect.KClass

sealed interface Tx {

    data class TxResource(
        override val resourceClass: KClass<out Resource>,
        val operation: TxOperation,
        val amount: Units,
    ) : Tx, ResourceReference {
        constructor(resourceClass: KClass<out Resource>, amount: Units) : this(
            resourceClass = resourceClass,
            operation = if (amount >= 0) TxOperation.INCREASE else TxOperation.DECREASE,
            amount = abs(amount.single).units
        )
        init {
            require(amount >= 0) { "Amount must be non-negative: $amount" }
        }
    }

    data class TxBuild(
        override val buildingClass: KClass<out Building>,
    ) : Tx, BuildingReference

}

enum class TxOperation {
    INCREASE,
    DECREASE,
}

sealed interface TxResult {

    object Success : TxResult

    sealed interface Fail : TxResult {
        val reason: String

        data class InsufficientResources(override val reason: String = "Insufficient resources") : Fail
    }

}

@OptIn(ExperimentalContracts::class)
fun TxResult.errorOnFail() {
    contract { returns() implies (this@errorOnFail is Success) }
    if (this is Fail) {
        error("Transaction failed: $reason")
    }
}
