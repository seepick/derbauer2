package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.StrictDouble
import com.github.seepick.derbauer2.game.core.User

fun <S : Stat<SD>, SD : StrictDouble> User.addStat(stat: S, value: SD): S {
    add(stat)
    stat.changeTo(value)
    return stat
}

object EmptyGlobalStatModifierRepo : GlobalStatModifierRepo {
    override val all = emptyList<StatModifier>()
}
