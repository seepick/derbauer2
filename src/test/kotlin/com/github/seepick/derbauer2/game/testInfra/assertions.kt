package com.github.seepick.derbauer2.game.testInfra

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceChange
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import io.kotest.matchers.collections.shouldContain

fun ResourceChanges.shouldContainChange(resource: Resource, amount: Zz) {
    changes.shouldContainChange(resource to amount)
}

infix fun List<ResourceChange>.shouldContainChange(resourceAndAmount: Pair<Resource, Zz>) {
    shouldContain(ResourceChange(resourceAndAmount.first, resourceAndAmount.second))
}
