package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.logic.User

val User.resources get() = all.filterIsInstance<Resource>()
