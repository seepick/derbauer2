package com.github.seepick.derbauer2.game.testInfra

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.ResourceReport
import io.kotest.matchers.collections.shouldContain

fun ResourceReport.shouldContainLine(resource: Resource, amount: Zz) {
    lines.shouldContainLine(resource to amount)
}

infix fun List<ResourceChange>.shouldContainLine(resourceAndAmount: Pair<Resource, Zz>) {
    shouldContain(ResourceChange(resourceAndAmount.first, resourceAndAmount.second))
}
