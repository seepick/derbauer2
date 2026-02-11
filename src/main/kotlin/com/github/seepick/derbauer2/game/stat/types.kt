package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.StrictDouble
import com.github.seepick.derbauer2.game.core.Entity
import kotlin.reflect.KClass

interface Stat<SD : StrictDouble> : Entity, StatData {
    val currentValue: SD
    fun changeBy(amount: Double)
    fun changeTo(value: SD)
}

typealias StatKClass = KClass<out Stat<out StrictDouble>>
