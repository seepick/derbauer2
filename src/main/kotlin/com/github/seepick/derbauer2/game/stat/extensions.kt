package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

val User.stats get() = ListX(all.filterIsInstance<Stat>())

fun User.hasStat(statClass: KClass<out Stat>): Boolean =
    stats.findOrNull(statClass) != null

@Suppress("UNCHECKED_CAST")
fun <S : Stat> User.findStat(statClass: KClass<S>): S = stats.find(statClass) as S
