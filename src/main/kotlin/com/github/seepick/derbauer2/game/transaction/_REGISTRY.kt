package com.github.seepick.derbauer2.game.transaction

import com.github.seepick.derbauer2.game.building.BuildingTxValidator
import com.github.seepick.derbauer2.game.resource.ResourceTxValidator

interface TxValidatorRepo {
    val validators: List<TxValidator>
}

object DefaultTxValidatorRepo : TxValidatorRepo {
    override val validators = listOf(
        BuildingTxValidator,
        ResourceTxValidator,
    )
}
