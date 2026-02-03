package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.Percent
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

@Suppress("MagicNumber")
fun User.storageUsage(resource: StorableResource): Percent =
    (resource.owned.value.toDouble() / resource.totalStorage.value.toDouble()).`%`

fun User.storageUsage(resourceClass: KClass<out StorableResource>) =
    storageUsage(findResource(resourceClass))

inline fun <reified SR : StorableResource> User.storageUsage() =
    storageUsage(findResource(SR::class))

context(user: User)
val StorableResource.storageUsage: Percent get() = user.storageUsage(this)

fun User.totalStorageFor(resource: StorableResource) =
    totalStorageFor(resource::class)

context(user: User)
val StorableResource.totalStorage: Z get() = user.totalStorageFor(this)

inline fun <reified SR : StorableResource> User.freeStorageFor(resource: SR) =
    freeStorageFor(resource::class)

inline fun <reified SR : StorableResource> User.freeStorageFor() =
    freeStorageFor(SR::class)

inline fun <reified SR : StorableResource> User.freeStorageFor(resoureClass: KClass<out SR>) =
    totalStorageFor(resoureClass) - findResource(resoureClass).owned

context(user: User)
val StorableResource.freeStorage: Z get() = user.freeStorageFor(this)

fun User.totalStorageFor(resourceClass: KClass<out StorableResource>) =
    all
        .filterIsInstance<StoresResource>()
        .filter { it.storableResourceClass == resourceClass }
        .sumOf { it.totalStorageAmount.value }.z
