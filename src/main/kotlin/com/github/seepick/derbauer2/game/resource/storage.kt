package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.`%`
import com.github.seepick.derbauer2.game.common.Percent
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

context(user: User)
val StorableResource.freeStorage: Z get() = user.freeStorageFor(this)

context(user: User)
val StorableResource.totalStorage: Z get() = user.totalStorageFor(this)

context(user: User)
@Suppress("MagicNumber")
val StorableResource.storageUsage: Percent get() = (totalStorage.value.toDouble() / owned.value.toDouble() / 100.0).`%`

fun User.totalStorageFor(resource: StorableResource) =
    totalStorageFor(resource::class)

fun User.freeStorageFor(resource: StorableResource) =
    totalStorageFor(resource::class) - resource.owned

fun User.totalStorageFor(resourceClass: KClass<out StorableResource>) =
    all
        .filterIsInstance<StoresResource>()
        .filter { it.storableResourceClass == resourceClass }
        .sumOf { it.totalStorageAmount.value }.z
