package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.resource.AgricultureTechItem
import com.github.seepick.derbauer2.game.resource.CapitalismTechItem
import com.github.seepick.derbauer2.game.resource.IrrigationTechItem
import com.github.seepick.derbauer2.game.resource.JunkFoodTechItem
import com.github.seepick.derbauer2.game.resource.WarfareTechItem

interface TechItemRepo {
    val items: List<TechItem>
}

object DefaultTechItemRepo : TechItemRepo {
    override val items = listOf(
        AgricultureTechItem,
        IrrigationTechItem,
        CapitalismTechItem,
        JunkFoodTechItem,
        WarfareTechItem,
    )
}
