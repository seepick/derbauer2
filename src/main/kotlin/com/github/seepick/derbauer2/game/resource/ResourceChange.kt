package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.zz
import kotlin.reflect.KClass

fun buildResourceChanges(code: ResourceChanges.Builder.() -> Unit) = ResourceChanges.build(code)

fun List<ResourceChange>.toChanges(): ResourceChanges = buildResourceChanges {
    addAll(this@toChanges)
}

data class ResourceChange(
    val resource: Resource,
    val changeAmount: Zz,
) {
    constructor(resource: Resource, changeAmount: Z) : this(resource, changeAmount.asZz)

    operator fun plus(other: ResourceChange): ResourceChange {
        require(other.resource::class == this.resource::class)
        return ResourceChange(resource, changeAmount + other.changeAmount)
    }
}

class ResourceChanges private constructor(
    val changes: List<ResourceChange>,
) {
    init {
        val distinct = changes.map { it.resource::class }.distinct()
        require(distinct.size == changes.size) {
            "Must not contain multiple changes for the same resource!"
        }
    }

    fun merge(other: ResourceChanges) = buildResourceChanges {
        changes.forEach { add(it.resource, it.changeAmount) }
        other.changes.forEach { add(it.resource, it.changeAmount) }
    }

    companion object {
        fun build(code: Builder.() -> Unit): ResourceChanges {
            val builder = Builder()
            code(builder)
            return builder.build()
        }
    }

    class Builder {

        private val changes = mutableMapOf<KClass<out Resource>, ResourceChange>()

        fun add(newChange: ResourceChange) {
            val oldChange = changes.getOrPut(newChange.resource::class) { ResourceChange(newChange.resource, 0.zz) }
            changes[newChange.resource::class] = oldChange + newChange
        }

        fun addAll(newChanges: Iterable<ResourceChange>) {
            newChanges.forEach { add(it) }
        }

        fun add(resource: Resource, change: Zz) {
            add(ResourceChange(resource, change))
        }

        fun add(resource: Resource, change: Z) {
            add(resource, change.asZz)
        }

        fun build() = ResourceChanges(changes.entries.map { it.value })
    }
}

