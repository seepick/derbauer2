package com.github.seepick.derbauer2

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.DerBauer2SysProp
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.addFeature
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.startApp
import com.github.seepick.derbauer2.game.tech.TechnologyFeature
import com.github.seepick.derbauer2.game.testInfra.ownedForTest
import com.github.seepick.derbauer2.game.trade.TradeFeature
import com.github.seepick.derbauer2.game.trade.TradeLandFeature

object DerBauer2DevApp {
    init {
        System.setProperty(DerBauer2SysProp.DEV_MODE.key, "true")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        startApp(
            isDevMode = true,
            version = "0.0.0-DEV",
            prefStorageFqn = DerBauer2DevApp::class,
            postInit = { koin ->
                val user = koin.get<User>()
                user.findResource<Gold>().ownedForTest = 1000.z
                user.addFeature(TechnologyFeature())
                user.addFeature(TradeFeature())
                user.addFeature(TradeLandFeature())
//                val agr = koin.get<TechTree>().filterResearchableItems().single { it.type == TechType.AGRICULTURE }
//                user.researchTech(agr)
            }
        )
    }
}
