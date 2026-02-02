package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

fun buildResourceChanges(code: ResourceChanges.Builder.() -> Unit): ResourceChanges {
    val builder = ResourceChanges.Builder()
    builder.code()
    return builder.build()
}

fun resourceChangesOf(changesChanges: List<ResourceChanges>): ResourceChanges =
    when (changesChanges.size) {
        0 -> ResourceChanges.empty
        1 -> changesChanges.first()
        else ->
            changesChanges.reduce { acc, resourceChanges ->
                acc.merge(resourceChanges)
            }
    }

fun List<ResourceChange>.toSingleChangesObject(): ResourceChanges = buildResourceChanges {
    addAll(this@toSingleChangesObject)
}

data class ResourceChange(
    val resourceClass: KClass<out Resource>,
    val changeAmount: Zz,
) {
    constructor(resource: Resource, changeAmount: Zz) : this(resource::class, changeAmount)
    constructor(resourceClass: KClass<out Resource>, changeAmount: Z) : this(resourceClass, changeAmount.zz)
    constructor(resource: Resource, changeAmount: Z) : this(resource::class, changeAmount.zz)

    operator fun plus(other: ResourceChange): ResourceChange {
        require(other.resourceClass::class == this.resourceClass::class)
        return ResourceChange(resourceClass, changeAmount + other.changeAmount)
    }
}


fun List<ResourceChange>.requireAllNonNegative() {
    val negativeChanges = filter { it.changeAmount < 0.zz }
    require(negativeChanges.isEmpty()) {
        "All resource changes must be non-negative, but found negative changes: $negativeChanges"
    }
}

class ResourceChanges private constructor(
    val changes: List<ResourceChange>,
) {
    init {
        val distinct = changes.map { it.resourceClass }.distinct()
        require(distinct.size == changes.size) {
            "Must not contain multiple changes for the same resource!\n" + "Found duplicates for: ${
                distinct.filter { rc -> changes.count { it.resourceClass == rc } > 1 }
            }"
        }
    }

    fun requireAllNonNegative() {
        changes.requireAllNonNegative()
    }

    fun merge(other: ResourceChanges) = buildResourceChanges {
        changes.forEach { add(it.resourceClass, it.changeAmount) }
        other.changes.forEach { add(it.resourceClass, it.changeAmount) }
    }

    fun invertSig() = buildResourceChanges {
        changes.forEach { add(it.resourceClass, -it.changeAmount) }
    }

    companion object {
        val empty = buildResourceChanges { }
        operator fun invoke(changes: List<ResourceChange>) = buildResourceChanges {
            addAll(changes)
        }
    }

    @Suppress("MethodOverloading")
    class Builder {

        private val changesByResourceClass = mutableMapOf<KClass<out Resource>, ResourceChange>()

        fun add(newChange: ResourceChange) {
            val oldChange = changesByResourceClass.getOrPut(newChange.resourceClass) {
                ResourceChange(
                    newChange.resourceClass, 0.zz
                )
            }
            changesByResourceClass[newChange.resourceClass] = oldChange + newChange
        }

        fun addAll(newChanges: Iterable<ResourceChange>) {
            newChanges.forEach { add(it) }
        }

        fun add(resourceClass: KClass<out Resource>, change: Zz) {
            add(ResourceChange(resourceClass, change))
        }

        fun add(resource: Resource, change: Zz) {
            add(resource::class, change)
        }

        fun add(resourceClass: KClass<out Resource>, change: Z) {
            add(resourceClass, change.zz)
        }

        fun add(resource: Resource, change: Z) {
            add(resource::class, change.zz)
        }

        fun build() = ResourceChanges(changesByResourceClass.entries.map { it.value })
    }
}

context(user: User)
fun ResourceChanges.toTextmapRendering() = changes.joinToString(", ") { change ->
    "${user.findResource(change.resourceClass).emojiSpaceOrEmpty}${change.changeAmount}"
}
