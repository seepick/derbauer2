package com.github.seepick.derbauer2.game.stat

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.turn.TurnReport

/** Executed in [com.github.seepick.derbauer2.game.turn.Turner] BEFORE the resource changes are happening. */
fun interface PreStatModifier {
    fun calcModifierOrNull(user: User, statClass: StatKClass): Double?

    companion object // for extensions
}

fun interface GlobalPreStatModifierRepo {
    fun getAll(): List<PreStatModifier>
}

class GlobalPreStatModifierRepoImpl(
    private val modifiers: List<PreStatModifier>,
) : GlobalPreStatModifierRepo {
    override fun getAll() = modifiers
}

/** Executed in [com.github.seepick.derbauer2.game.turn.Turner] AFTER the resource changes are happening. */
fun interface PostStatModifier {
    fun calcModifierOrNull(report: TurnReport, user: User, statClass: StatKClass): Double?

    companion object // for extensions
}

fun interface GlobalPostStatModifierRepo {
    fun getAll(): List<PostStatModifier>
}

class GlobalPostStatModifierRepoImpl(
    private val modifiers: List<PostStatModifier>,
) : GlobalPostStatModifierRepo {
    override fun getAll() = modifiers
}
