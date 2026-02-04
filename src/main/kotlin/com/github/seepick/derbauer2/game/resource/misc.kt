package com.github.seepick.derbauer2.game.resource

import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.view.GameRenderer
import kotlin.reflect.KClass

interface ResourceReference {
    val resourceClass: KClass<out Resource>
}

private const val separator = GameRenderer.RESOURCE_INFO_OWNED_SEPARATOR

fun Resource.toInfoBarString(user: User): String =
    when (this) {
        is StorableResource -> "$emojiAndOwned $separator ${user.totalStorageFor(this)}"
        is Land -> "$emojiSpaceOrEmpty${user.totalLandUse} $separator $owned"
        else -> emojiAndOwned
    }
