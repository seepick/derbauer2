package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

val User.resources get() = ListX(all.filterIsInstance<Resource>())

val User.gold get() = findResource(Gold::class).owned
val User.food get() = findResource(Food::class).owned
val User.citizen get() = findResource(Citizen::class).owned
val User.land get() = findResource(Land::class).owned

@Suppress("UNCHECKED_CAST")
fun <R : Resource> User.findResource(resourceClass: KClass<R>): R =
    resources.find(resourceClass) as R

inline fun <reified R : Resource> User.findResource(): R =
    resources.find<R>()

@Suppress("UNCHECKED_CAST")
fun <R : Resource> User.findResourceOrNull(resourceClass: KClass<R>): R? =
    resources.findOrNull(resourceClass) as? R?

inline fun <reified R : Resource> User.findResourceOrNull(): R? =
    resources.findOrNull<R>()

inline fun <reified SR : StorableResource> User.isAbleToStore(resource: SR, amount: Z) =
    amount <= freeStorageFor(resource)

fun User.hasAtLeast(resourceClass: KClass<out Resource>, amount: Z) =
    findResource(resourceClass).owned >= amount

