package com.github.seepick.derbauer2.game.core

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.Z
import com.github.seepick.derbauer2.game.common.Zz
import com.github.seepick.derbauer2.game.common.z
import com.github.seepick.derbauer2.game.common.zz
import com.github.seepick.derbauer2.game.resource.Citizen
import com.github.seepick.derbauer2.game.resource.Food
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.Resource
import kotlin.reflect.KClass

private val emojiMap = mapOf(
    Food::class to Food.Data.emojiOrNull.value,
    Gold::class to Gold.Data.emojiOrNull.value,
    Citizen::class to Citizen.Data.emojiOrNull.value,
    Land::class to Land.Data.emojiOrNull.value,
)

private const val DEFAULT_EMOJI = "❌"
val KClass<out Resource>.maybeEmojiOrSimpleName: String get() = emojiMap[this] ?: simpleName ?: DEFAULT_EMOJI

interface HasLabels {
    val labelSingular: String
    val labelPlural: String get() = labelSingular + "s"
    fun labelFor(unsignedAmount: Z) = if (unsignedAmount == 1.z) labelSingular else labelPlural
    fun labelFor(signedAmount: Zz) = if (signedAmount == 1.zz) labelSingular else labelPlural
}

interface HasEmoji {
    val emojiOrNull: Emoji? get() = null
    val emojiSpaceOrEmpty: String get() = emojiOrNull?.let { "$it " } ?: ""
}

val <T> T.emojiAndLabelSingular: String where T : HasLabels, T : HasEmoji
    get() = "$emojiSpaceOrEmpty$labelSingular"

val <T> T.emojiAndLabelPlural: String where T : HasLabels, T : HasEmoji
    get() = "$emojiSpaceOrEmpty$labelPlural"

fun <T> T.emojiAndLabelFor(unsignedAmount: Z) where T : HasLabels, T : HasEmoji =
    "$emojiSpaceOrEmpty${labelFor(unsignedAmount)}"

fun <T> T.emojiAndLabelFor(signedAmount: Zz) where T : HasLabels, T : HasEmoji =
    "$emojiSpaceOrEmpty${labelFor(signedAmount)}"

