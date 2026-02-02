package com.github.seepick.derbauer2

import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.feature.FeatureDescriptorType
import com.github.seepick.derbauer2.game.feature.enableFeature
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.startApp
import com.github.seepick.derbauer2.game.testInfra.ownedForTest

object DerBauer2DevApp {
    init {
        System.setProperty("derbauer2.devMode", "true")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        startApp(
            isDevMode = true,
            version = "0.0.0-DEV",
            prefStatePath = DerBauer2DevApp::class,
            postInit = { koin ->
                val user = koin.get<User>()
                user.findResource<Gold>().ownedForTest = 1000.z
                user.enableFeature(FeatureDescriptorType.Technology)
                user.enableFeature(FeatureDescriptorType.Trading)
                user.enableFeature(FeatureDescriptorType.TradeLand)
//                val agriTech = koin.get<TechTree>().filterResearchableItems().single { it.type == TechType.AGRICULTURE }
//                user.researchTech(agriTech)
            }
        )
    }
}
