package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.core.User
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
    constructor(resource: Resource, changeAmount: Z) : this(resource, changeAmount.asZz)
}

class ResourceReport(
    val lines: List<ResourceReportLine>,
) {
    companion object {
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
        add(resource, change.asZz)
    }

    fun add(resource: Resource, change: Zz) {
        val line = changes.getOrPut(resource::class) { ResourceReportLine(resource, change) }
        line.changeAmount += change
    }

    fun build() =
        ResourceReport(changes.entries.map { it.value })
}
