package com.github.seepick.derbauer2.game

import com.github.seepick.derbauer2.game.logic.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.ResourceReportLine
import io.kotest.matchers.collections.shouldContain

infix fun List<ResourceReportLine>.shouldContainChange(pair: Pair<Resource, Zz>) {
    this.shouldContain(ResourceReportLine(pair.first, pair.second))
}
