package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.availableOf
import com.github.seepick.derbauer2.game.logic.units
import kotlin.reflect.KClass

class ResourceTurner(
    private val user: User,
) {
    // TODO don't execute, just return; someone else will execute it (Interaction?!)
    fun executeAndReturnReport() = buildResourceReport {
        user.all.filterIsInstance<ProducesResource>().forEach { producer ->
            val resource = user.resource(producer.producingResourceType)
            val producing = producer.resourceProductionAmount

            val added = if (resource is StorableResource) {
                // FIXME centralize max storage logic in single place!
                producing.single.coerceAtMost(user.availableOf(resource).single).units
            } else {
                producing
            }
            // TODO test if added == 0 (should not be contained in report!)
            resource.owned += added
            add(resource, added)
        }
    }
}

data class ResourceChange(
    val resource: Resource,
    var change: Units,
)

class ResourceReport(
    val changes: List<ResourceChange>, // TODO needs to be sorted in a deterministic way (centrally/globally)
) {
    companion object {
        val empty = ResourceReport(emptyList())
        fun builder() = ResourceReportBuilder()
    }

    fun merge(other: ResourceReport) = buildResourceReport {
        changes.forEach { add(it.resource, it.change) }
        other.changes.forEach { add(it.resource, it.change) }
    }
}

fun buildResourceReport(code: ResourceReportBuilder.() -> Unit): ResourceReport {
    val builder = ResourceReport.builder()
    code(builder)
    return builder.build()
}

class ResourceReportBuilder {
    private val changes = mutableMapOf<KClass<out Resource>, ResourceChange>()

    fun add(resource: Resource, change: Units) {
        changes.putIfAbsent(resource::class, ResourceChange(resource, 0.units))
        changes[resource::class]!!.change += change
    }

    fun build(): ResourceReport {
        return ResourceReport(changes.entries.map { it.value })
    }
}
