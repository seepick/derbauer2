package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.building.Building
import com.github.seepick.derbauer2.game.building.BuildingTxValidator
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Ownable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceTxValidator
import com.github.seepick.derbauer2.game.resource.resource

fun User(): User = User(txValidators = listOf(ResourceTxValidator, BuildingTxValidator))

val User.gold
    get() = resource(Gold::class).owned

val User.food
    get() = resource(Food::class).owned

fun <R : Resource> User.enableAndSet(resource: R, amount: Z): R {
    enable(resource)
    // by-pass validation via transaction
    resource._setOwnedInternal = amount
    return resource
}

fun <B : Building> User.enableAndSet(building: B, amount: Z): B {
    enable(building)
    // by-pass validation via transaction
    building._setOwnedInternal = amount
    return building
}

var Ownable.ownedForTest
    get() = owned
    set(value) {
        _setOwnedInternal = value
    }

@Suppress("VariableMinLength")
val Double.z get() = toLong().z
