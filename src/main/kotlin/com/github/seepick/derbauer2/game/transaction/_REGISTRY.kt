package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.BuildingTxValidator
import com.github.seepick.derbauer2.game.resource.ResourceTxValidator

interface TxValidatorRegistry {
    val validators: List<TxValidator>
}

object DefaultTxValidatorRegistry : TxValidatorRegistry {
    override val validators = listOf(
        BuildingTxValidator,
        ResourceTxValidator,
        // ...
        // ..
        // .
    )
}
