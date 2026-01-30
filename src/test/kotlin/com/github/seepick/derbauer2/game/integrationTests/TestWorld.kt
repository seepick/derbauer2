package com.github.seepick.derbauer2.game.integrationTests

import com.github.seepick.derbauer2.game.building.House
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.core.Asset
import com.github.seepick.derbauer2.game.core.HasLabel
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.gameModule
import com.github.seepick.derbauer2.game.integrationTests.testInfra.KeyInput
import com.github.seepick.derbauer2.game.ownedForTest
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.Page
import com.github.seepick.derbauer2.textengine.textengineModule
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.koin.core.Koin
import org.koin.dsl.koinApplication
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

// FIXME provide by impl code; reflectively preferrably (enum idea, reverse lookup, switch exhaustion yay)
val assetsAndData: List<Pair<KClass<out Asset>, HasLabel>> = listOf(
    Gold::class to Gold.Data,
    Citizen::class to Citizen.Data,
    Food::class to Food.Data,
    Land::class to Land.Data,
    House::class to House.Data
)

fun lookupAssetBy(name: String): KClass<out Asset> {
    val (kclass, _) = assetsAndData.firstOrNull {
        it.second.labelSingular.equals(name, ignoreCase = true) ||
                it.second.labelPlural.equals(name, ignoreCase = true)
    } ?: error("No asset found with name '$name'")
    return kclass
}

/**
 * Shared state between step definition classes (managed by pico DI container).
 */
class TestWorld {
    private val log = logger {}

    private val user: User
    private val koin: Koin
    val page: Page get() = koin.get(koin.get<CurrentPage>().pageClass)

    init {
        log.debug { "NEW" }
        koin = koinApplication {
            modules(gameModule(), textengineModule())
            // TODO disable beeper
//            allowOverride(true)
        }.koin
        user = koin.get<User>()
    }

    fun enterKeyOnCurrentPage(key: KeyInput) {
        log.debug { "Input ${key::class.simpleName} (page = ${page::class.simpleName})" }
        page.onKeyPressed(key.asKeyPressed)
    }

    fun getOwnedFor(assetClass: KClass<out Asset>): Int =
        user.all.findAs<Asset>(assetClass).owned.value.toInt()

    fun setOwnedFor(assetClass: KClass<out Asset>, amount: Int) {
        if (!user.hasEntity(assetClass)) {
            log.trace { "Adding new" }
            user.enable(assetClass::primaryConstructor.call()!!.call())
        }
        user.all.findAs<Asset>(assetClass).ownedForTest = amount.z
    }

}
