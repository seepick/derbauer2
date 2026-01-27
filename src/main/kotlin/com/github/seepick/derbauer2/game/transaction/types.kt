package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.BuildingReference
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceReference
import com.github.seepick.derbauer2.game.transaction.TxResult.Fail
import com.github.seepick.derbauer2.game.transaction.TxResult.Success
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.reflect.KClass

sealed interface Tx {

    data class TxResource(
        override val resourceClass: KClass<out Resource>,
        val operation: TxOperation,
        val amount: Z,
    ) : Tx, ResourceReference {
        constructor(resourceClass: KClass<out Resource>, amount: Zz) : this(
            resourceClass = resourceClass,
            // could introduce TxOperation.NONE but for now just use INCREASE with 0 amount
            operation = if (amount >= 0) TxOperation.INCREASE else TxOperation.DECREASE,
            amount = amount.asUnsigned()
        )
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
        val message: String

        data class InsufficientResources(override val message: String = "Insufficient resources") : Fail
    }
}

@OptIn(ExperimentalContracts::class)
fun TxResult.errorOnFail() {
    contract { returns() implies (this@errorOnFail is Success) }
    if (this is Fail) {
        error("Transaction failed: $message")
    }
}
