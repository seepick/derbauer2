package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.StrictDouble
import com.github.seepick.derbauer2.game.core.Entity
import kotlin.reflect.KClass

interface Stat<SD : StrictDouble> : Entity {
    val value: SD
    fun change(amount: Double)
}

typealias StatKClass = KClass<out Stat<out StrictDouble>>
