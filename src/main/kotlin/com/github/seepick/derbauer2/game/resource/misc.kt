package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.emoji
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.stat.Happiness
import com.github.seepick.derbauer2.game.stat.findStat
import com.github.seepick.derbauer2.game.stat.hasStat
import com.github.seepick.derbauer2.game.view.GameRenderer
import kotlin.reflect.KClass

interface ResourceReference {
    val resourceClass: KClass<out Resource>
}

private const val SEPARATOR = GameRenderer.RESOURCE_INFO_OWNED_SEPARATOR

fun Resource.toInfoBarString(user: User): String =
    if (this is Citizen && user.hasStat(Happiness::class)) {
        user.findStat(Happiness::class).emoji.value + " "
    } else {
        ""
    } + when (this) {
        is StorableResource -> "$emojiAndOwned $SEPARATOR ${user.totalStorageFor(this)}"
        is Land -> "$emojiSpaceOrEmpty${user.totalLandUse} $SEPARATOR $owned"
        else -> emojiAndOwned
    }

private val goldEmoji = "💰️".emoji
@Suppress("ObjectPropertyName", "NonAsciiCharacters")
val Emoji.Companion.`gold 💰` get() = goldEmoji
