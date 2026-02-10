package com.github.seepick.derbauer2.game.tech

fun interface TechRefRegistry {
    fun getAll(): List<TechRef>
}

object DefaultTechRefRegistry : TechRefRegistry {
    override fun getAll() = listOf(
        AgricultureTech.Ref,
        IrrigationTech.Ref,
        CapitalismTech.Ref,
        // ...
        // ..
        // .
    )
}
