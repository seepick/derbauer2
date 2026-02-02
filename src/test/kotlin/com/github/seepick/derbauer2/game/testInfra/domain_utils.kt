package com.github.seepick.derbauer2.game.testInfra

import com.github.seepick.derbauer2.game.building.BuildingTxValidator
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Ownable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.ResourceTxValidator

// TODO somehow make aggregating future-proof; by default empty validators;
fun User(): User = User(txValidators = listOf(ResourceTxValidator, BuildingTxValidator))

var Ownable.ownedForTest
    get() = owned
    set(value) {
        _setOwnedInternal = value
    }

@Suppress("VariableMinLength")
val Double.z get() = toLong().z
