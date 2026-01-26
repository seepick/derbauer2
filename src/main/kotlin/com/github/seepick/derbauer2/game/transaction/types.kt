package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.BuildingReference
import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceReference
import kotlin.reflect.KClass

sealed interface TxRequest {
    data class TxResource(
        override val resourceClass: KClass<out Resource>,
        val operation: TxOperation,
        val amount: Units,
    ) : TxRequest, ResourceReference

    data class TxBuild(
        override val buildingClass: KClass<out Building>,
    ) : TxRequest, BuildingReference
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