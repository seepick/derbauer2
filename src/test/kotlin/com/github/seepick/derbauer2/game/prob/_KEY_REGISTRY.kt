package com.github.seepick.derbauer2.game.prob

import com.github.seepick.derbauer2.game.citizen.birthKey
import com.github.seepick.derbauer2.game.citizen.eatKey
import com.github.seepick.derbauer2.game.citizen.taxKey

private val allDiffuserKeys = listOf(
    ProbDiffuserKey.taxKey,
    ProbDiffuserKey.eatKey,
    ProbDiffuserKey.birthKey,
)

val ProbDiffuserKey.Companion.all get() = allDiffuserKeys
