package com.github.seepick.derbauer2.game.tech

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
