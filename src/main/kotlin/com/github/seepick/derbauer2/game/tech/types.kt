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
    IRRIGATION(::IrrigationTechTreeItem), // +food production
    CAPITALISM(::CapitalismTechTreeItem), // +tax
    JUNK_FOOD(::JunkFoodTechTreeItem), // -food eaten (+happiness?)
    WARFARE(::WarfareTechTreeItem), // -food eaten (+happiness?)
}

interface Tech : Entity, TechStaticData {
    override val labelSingular get() = label
}

interface TechStaticData {
    val label: String
    val type: TechType
    val requirements: Set<TechType>
    val costs: ResourceChanges
}
