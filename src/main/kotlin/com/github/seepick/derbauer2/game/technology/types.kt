package com.github.seepick.derbauer2.game.technology

import com.github.seepick.derbauer2.game.core.Entity

interface Technology : Entity {
    // check end turn, enable if not yet enabled
    // used as precondition filter for actions/etc.
}
