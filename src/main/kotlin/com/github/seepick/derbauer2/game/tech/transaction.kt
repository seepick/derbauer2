package com.github.seepick.derbauer2.game.tech

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.transaction.TxResult

fun User.research(item: TechTreeItem): TxResult {
    require(item.state is TechState.Unresearched)
//    execTx(item.costs.toTxs())
    val tech = item.buildTech()
    item.state = TechState.Researched(tech)
    enable(tech)
    return TxResult.Success
}
