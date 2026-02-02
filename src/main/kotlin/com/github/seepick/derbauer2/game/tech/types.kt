package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.resource.AgricultureTechTreeItem
import com.github.seepick.derbauer2.game.resource.CapitalismTechTreeItem
import com.github.seepick.derbauer2.game.resource.IrrigationTechTreeItem
import com.github.seepick.derbauer2.game.resource.JunkFoodTechTreeItem
import com.github.seepick.derbauer2.game.resource.ResourceChanges
import com.github.seepick.derbauer2.game.resource.WarfareTechTreeItem

enum class TechType(val treeItemBuilder: () -> TechTreeItem) {
    AGRICULTURE(::AgricultureTechTreeItem), // +food production
    IRRIGATION(::IrrigationTechTreeItem), // +food production // TODO refactor to a lvl II upgrade
    CAPITALISM(::CapitalismTechTreeItem), // +tax
    JUNK_FOOD(::JunkFoodTechTreeItem), // -food eaten (+happiness?)
    WARFARE(::WarfareTechTreeItem), // -food eaten (+happiness?)
//    POTTERY,     // enables granaries
    // ???  +food storage
//    HOUSES,      // upgrade from tents; replaces building? then auto-upgrade necessary (keep UI items low as ascend)
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
