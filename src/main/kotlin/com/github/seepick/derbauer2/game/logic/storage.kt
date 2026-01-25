package com.github.seepick.derbauer2.game.logic

import kotlin.reflect.KClass

fun User.storageCapacityFor(resource: StorableResource): Units =
    storageCapacityFor(resource::class)

fun User.storageCapacityFor(resource: KClass<out StorableResource>): Units =
    buildings
        .filterIsInstance<StoresResource>()
        .filter { it.storableResource == resource }
        .sumOf { it.totalCapacity.single }.units
