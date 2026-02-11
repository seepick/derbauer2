package com.github.seepick.derbauer2.game.view

import com.github.seepick.derbauer2.game.core.Entity
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiAndOwned
import com.github.seepick.derbauer2.game.feature.hasFeature
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.Resource
import com.github.seepick.derbauer2.game.resource.StorableResource
import com.github.seepick.derbauer2.game.resource.findResourceOrNull
import com.github.seepick.derbauer2.game.resource.totalLandUse
import com.github.seepick.derbauer2.game.resource.totalStorageFor
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.stat.findStat
import com.github.seepick.derbauer2.game.stat.hasStat
import com.github.seepick.derbauer2.game.turn.CurrentTurn
import com.github.seepick.derbauer2.game.turn.SeasonFeature
import com.github.seepick.derbauer2.textengine.keyboard.KeyPressed
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import com.github.seepick.derbauer2.textengine.textmap.emptyLine
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

private const val RESOURCE_INFO_SEPARATOR = "|"
private const val RESOURCE_INFO_OWNED_SEPARATOR = "/"

class GameRenderer(
    private val user: User,
    private val turn: CurrentTurn,
) {
    private val MetaOption.formatted get() = "${key.label}: $label"
    private val defaultDisplayedResources: List<KClass<out Resource>> =
        listOf(Gold::class, Food::class, Land::class, Citizen::class)

    fun render(
        textmap: Textmap,
        promptIndicator: String,
        metaOptions: List<MetaOption> = emptyList(),
        content: (Textmap) -> Unit,
    ) {
        textmap.aligned(renderResourcesInfo(), renderTurnInfo())
        textmap.hr()
        textmap.emptyLine()
        content(textmap)
        textmap.fillVertical(minus = 2)
        textmap.hr()
        textmap.aligned(
            left = "[$promptIndicator]> ▉",
            right = metaOptions.filter { it.renderHint }
                .joinToString("   ") { it.formatted },
        )
    }

    private fun renderResourcesInfo() =
        defaultDisplayedResources
            .mapNotNull { user.findResourceOrNull(it) }
            .sortIfViewOrder()
            .joinToString(" $RESOURCE_INFO_SEPARATOR ") { it.toInfoBarString(user) }

    private fun renderTurnInfo() =
        if (user.hasFeature<SeasonFeature>()) {
            turn.toSeasonedString()
        } else {
            "Turn ${turn.number}"
        }
}

inline fun <reified E : Entity> List<E>.sortIfViewOrder(): List<E> =
    if (E::class.isSubclassOf(ViewOrder::class)) {
        sortedBy { (it as ViewOrder).viewOrder }
    } else {
        this
    }

fun Resource.toInfoBarString(user: User): String =
    if (this is Citizen && user.hasStat(Happiness::class)) {
        user.findStat(Happiness::class).currentEmoji.string + " "
    } else {
        ""
    } + when (this) {
        is StorableResource -> "$emojiAndOwned $RESOURCE_INFO_OWNED_SEPARATOR ${user.totalStorageFor(this)}"
        is Land -> "$emojiSpaceOrEmpty${user.totalLandUse} $RESOURCE_INFO_OWNED_SEPARATOR $owned"
        else -> emojiAndOwned
    }

interface MetaOption {
    val key: KeyPressed.Command // ENTER
    val label: String // Buy Building
    val renderHint: Boolean
}

data class MetaOptionImpl(
    override val key: KeyPressed.Command,
    override val label: String,
    override val renderHint: Boolean = true,
) : MetaOption
