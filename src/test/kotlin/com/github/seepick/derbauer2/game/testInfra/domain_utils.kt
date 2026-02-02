package com.github.seepick.derbauer2.game.testInfra

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.core.Ownable

var Ownable.ownedForTest
    get() = owned
    set(value) {
        _setOwnedInternal = value
    }

@Suppress("VariableMinLength")
val Double.z get() = toLong().z

@Suppress("VariableMinLength")
val Double.zz get() = toLong().zz
