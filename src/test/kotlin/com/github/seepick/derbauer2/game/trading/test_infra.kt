package com.github.seepick.derbauer2.game.trading

import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.resource.Resource
import kotlin.reflect.KClass

operator fun ResourcesTradedAction.Companion.invoke(vararg requests: TradeRequest) =
    ResourcesTradedAction(requests.toList())

operator fun ResourcesTradedAction.Companion.invoke(vararg requests: Pair<KClass<out Resource>, Zz>) =
    ResourcesTradedAction(requests.map { TradeRequest(it.first, it.second) })
