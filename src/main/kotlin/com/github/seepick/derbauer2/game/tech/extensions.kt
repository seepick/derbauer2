package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.common.ListX
import com.github.seepick.derbauer2.game.core.User
import kotlin.reflect.KClass

val User.tech get() = ListX(all.filterIsInstance<Tech>())

fun User.hasTech(techClass: KClass<out Tech>): Boolean =
    tech.findOrNull(techClass) != null
