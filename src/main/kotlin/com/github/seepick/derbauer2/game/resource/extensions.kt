package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

val User.resources get() = ListX(all.filterIsInstance<Resource>())

@Suppress("UNCHECKED_CAST")
fun <R : Resource> User.resource(type: KClass<R>): R =
    resources.find(type) as R

inline fun <reified R : Resource> User.resource(): R =
    resources.find<R>()


@Suppress("UNCHECKED_CAST")
fun <R : Resource> User.resourceOrNull(type: KClass<R>): R? =
    resources.findOrNull(type) as R?

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
