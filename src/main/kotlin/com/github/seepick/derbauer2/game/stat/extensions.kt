package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.common.StrictDouble
import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

val User.stats get() = ListX(all.filterIsInstance<Stat<out StrictDouble>>())

val User.happiness get() = findStat(Happiness::class).value

fun User.hasStat(statClass: StatKClass): Boolean =
    stats.findOrNull(statClass) != null

@Suppress("UNCHECKED_CAST")
fun <S : Stat<out StrictDouble>> User.findStat(statClass: KClass<S>): S = stats.find(statClass) as S

@Suppress("UNCHECKED_CAST")
fun <S : Stat<out StrictDouble>> User.findStatOrNull(statClass: KClass<S>): S? = stats.findOrNull(statClass) as? S?
