package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.User
import com.github.seepick.derbauer2.game.logic.Z
import com.github.seepick.derbauer2.game.logic.Zz
import com.github.seepick.derbauer2.game.logic.zz
import kotlin.reflect.KClass

class ResourceTurner(
    private val user: User,
) {
    fun buildTurnReport() = buildResourceReport {
        user.all.filterIsInstance<ProducesResource>().forEach { producer ->
            val resource = user.resource(producer.producingResourceClass)
            val producingAmount = if (producer is ProducesResourceOwnable) {
                producer.totalProducingResourceAmount
            } else producer.producingResourceAmount
            val adjustedProducingAmount = user.capResourceAmount(resource, producingAmount)
            add(resource, adjustedProducingAmount)
        }
    }
}

data class ResourceReportLine(
    val resource: Resource,
    var changeAmount: Zz,
) {
    constructor( resource: Resource, changeAmount: Z) : this(resource, changeAmount.asZ)
}

class ResourceReport(
    val lines: List<ResourceReportLine>, // TODO needs to be sorted in a deterministic way (centrally/globally)
) {
    companion object {
        val empty = ResourceReport(emptyList())
        fun builder() = ResourceReportBuilder()
    }

    fun merge(other: ResourceReport) = buildResourceReport {
        lines.forEach { add(it.resource, it.changeAmount) }
        other.lines.forEach { add(it.resource, it.changeAmount) }
    }
}

fun buildResourceReport(code: ResourceReportBuilder.() -> Unit): ResourceReport {
    val builder = ResourceReport.builder()
    code(builder)
    return builder.build()
}

class ResourceReportBuilder {
    private val changes = mutableMapOf<KClass<out Resource>, ResourceReportLine>()

    fun add(resource: Resource, change: Z) {
        add(resource, change.asZ)
    }

    fun add(resource: Resource, change: Zz) {
        changes.putIfAbsent(resource::class, ResourceReportLine(resource, 0.zz))
        changes[resource::class]!!.changeAmount += change
    }

    fun build(): ResourceReport {
        return ResourceReport(changes.entries.map { it.value })
    }
}
