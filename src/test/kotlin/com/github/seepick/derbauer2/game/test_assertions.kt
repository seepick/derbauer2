package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.logic.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceReport
import com.github.seepick.derbauer2.game.resource.ResourceReportLine
import io.kotest.matchers.collections.shouldContain

fun ResourceReport.shouldContainLine(resource: Resource, amount: Zz) {
    lines.shouldContainLine(resource to amount)
}

infix fun List<ResourceReportLine>.shouldContainLine(resourceAndAmount: Pair<Resource, Zz>) {
    shouldContain(ResourceReportLine(resourceAndAmount.first, resourceAndAmount.second))
}
