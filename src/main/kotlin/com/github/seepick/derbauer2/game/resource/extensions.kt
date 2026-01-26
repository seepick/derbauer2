package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.Z
import com.github.seepick.derbauer2.game.logic.availableOf
import com.github.seepick.derbauer2.game.logic.z
import kotlin.reflect.KClass

fun <R : Resource> User.resource(type: KClass<R>): R =
    (resources.findOrNull(type) as R?) ?: errorNotFoundEntity(type, resources)

fun <R : Resource> User.resourceOrNull(type: KClass<R>): R? = resources.findOrNull(type) as R?

val User.resources get() = all.filterIsInstance<Resource>()

context(user: User)
val ResourceReference.resource get() = user.resource(resourceClass)

fun User.isAbleToStore(resource: StorableResource, amount: Z) =
    amount <= availableOf(resource)

fun User.hasAtLeast(resourceClass: KClass<out Resource>, amount: Z) =
    resource(resourceClass).owned >= amount

fun User.hasAtLeast(resource: Resource, amount: Z) =
    resource.owned >= amount

fun User.capResourceAmount(resource: Resource, amount: Z) =
    if (resource is StorableResource) {
        amount.value.coerceAtMost(availableOf(resource).value).z
    } else amount
