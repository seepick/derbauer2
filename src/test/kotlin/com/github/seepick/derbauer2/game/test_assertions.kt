package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.logic.Units
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceReportLine
import io.kotest.matchers.collections.shouldContain

infix fun List<ResourceReportLine>.shouldContainChange(pair: Pair<Resource, Units>) {
    this.shouldContain(ResourceReportLine(pair.first, pair.second))
}
