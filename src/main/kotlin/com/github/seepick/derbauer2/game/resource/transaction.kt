package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.core.TxOwnable
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndLabelPlural
import com.github.seepick.derbauer2.game.transaction.TxResult
import com.github.seepick.derbauer2.game.transaction.TxValidator
import com.github.seepick.derbauer2.game.transaction.TxValidatorType
import com.github.seepick.derbauer2.game.transaction.execTx
import com.github.seepick.derbauer2.game.transaction.merge

fun User.execTx(changes: ResourceChanges) =
    execTx(changes.changes.map { line ->
        TxOwnable(
            ownableClass = line.resourceClass,
            amount = line.changeAmount,
        )
    })

object ResourceTxValidator : TxValidator {
    override val type = TxValidatorType.Resource

    override fun validateTx(user: User) =
        with(user) {
            resources.filterIsInstance<StorableResource>().map { resource ->
                if (resource.owned > totalStorageFor(resource)) {
                    TxResult.Fail.InsufficientResources("Not enough storage for ${resource.emojiAndLabelPlural}")
                } else TxResult.Success
            }.merge()
        }
}
