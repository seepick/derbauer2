package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.resource.AgricultureTechTreeItem
import com.github.seepick.derbauer2.game.resource.CapitalismTechTreeItem
import com.github.seepick.derbauer2.game.resource.ResourceChanges

enum class TechType(val treeItemBuilder: () -> TechTreeItem) {
    AGRICULTURE(::AgricultureTechTreeItem), // +food production
    CAPITALISM(::CapitalismTechTreeItem),
//    POTTERY,     // enables granaries
//    HOUSES,      // upgrade from tents
}

interface Tech : Entity, TechStaticData {
    // check end turn, enable if not yet enabled
    // used as precondition filter for actions/etc.
    override val labelSingular get() = label
}

interface TechStaticData {
    val label: String
    val type: TechType
    val requirements: Set<TechType>
    val costs: ResourceChanges
}
