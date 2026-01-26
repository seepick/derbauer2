package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.availableOf
import kotlin.reflect.KClass

val User.resources get() = all.filterIsInstance<Resource>()

fun User.isAbleToStore(resource: StorableResource, amount: Units): Boolean =
    amount <= availableOf(resource)

fun User.hasAtLeast(resource: KClass<out Resource>, amount: Units): Boolean =
    resource(resource).owned >= amount

fun User.hasAtLeast(resource: Resource, amount: Units): Boolean =
    resource.owned >= amount
