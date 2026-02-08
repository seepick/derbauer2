package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.resource.AgricultureTechRef
import com.github.seepick.derbauer2.game.resource.CapitalismTechRef
import com.github.seepick.derbauer2.game.resource.IrrigationTechRef

interface TechItemRepo {
    val items: List<TechRef>
}

object DefaultTechItemRepo : TechItemRepo {
    override val items = listOf(
        AgricultureTechRef,
        IrrigationTechRef,
        CapitalismTechRef,
        // ...
        // ..
        // .
    )
}
