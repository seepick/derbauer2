package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.common.StrictDouble
import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User

fun <S : Stat<SD>, SD : StrictDouble> User.addStat(stat: S, value: SD): S {
    add(stat)
    stat.changeTo(value)
    return stat
}

object EmptyGlobalPreStatModifierRepo : GlobalPreStatModifierRepo {
    override fun getAll() = emptyList<PreStatModifier>()
}

object EmptyGlobalPostStatModifierRepo : GlobalPostStatModifierRepo {
    override fun getAll() = emptyList<PostStatModifier>()
}

class PreStatModifierEntity(
    private val modifier: PreStatModifier,
    override val label: String = "PreStatModifierEntity.label",
) : PreStatModifier by modifier, Entity {
    override fun deepCopy() = this
}

class PostStatModifierEntity(
    private val modifier: PostStatModifier,
    override val label: String = "PostStatModifierEntity.label",
) : PostStatModifier by modifier, Entity {
    override fun deepCopy() = this
}

operator fun PreStatModifier.Companion.invoke(statClass: StatKClass, changeAmount: Double): PreStatModifier =
    PreStatModifier { _, checkStatClass -> if (checkStatClass == statClass) changeAmount else null }

operator fun PreStatModifier.Companion.invoke(stat: Stat<out StrictDouble>, changeAmount: Double) =
    PreStatModifier(stat::class, changeAmount)

operator fun PostStatModifier.Companion.invoke(statClass: StatKClass, changeAmount: Double): PostStatModifier =
    PostStatModifier { _, _, checkStatClass -> if (checkStatClass == statClass) changeAmount else null }

operator fun PostStatModifier.Companion.invoke(stat: Stat<out StrictDouble>, changeAmount: Double) =
    PostStatModifier(stat::class, changeAmount)
