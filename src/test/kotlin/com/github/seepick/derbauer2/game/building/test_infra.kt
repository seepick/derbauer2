package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.core.User

fun <B : Building> User.addAndSet(building: B, amount: Z): B {
    add(building)
    // by-pass validation via transaction
    building._setOwnedInternal = amount
    return building
}
