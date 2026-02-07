package com.github.seepick.derbauer2.game.feature

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.emoji
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.view.ContinueButton
import com.github.seepick.derbauer2.game.view.MultiView
import com.github.seepick.derbauer2.game.view.MultiViewSubPage
import com.github.seepick.derbauer2.game.view.NotificationPage
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.`page 📄`
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import com.github.seepick.derbauer2.textengine.textmap.emptyLine
import com.github.seepick.derbauer2.textengine.textmap.multiLine

private val featureEmoji = "🔓".emoji
@Suppress("ObjectPropertyName", "NonAsciiCharacters")
val Emoji.Companion.`feature 🔓` get() = featureEmoji

class FeatureMultiView(user: User, currentPage: CurrentPage) : MultiView<FeatureInfo>(
    user = user,
    currentPage = currentPage,
    targetPageClass = FeatureViewPage::class,
)

class FeatureViewPage(
    private val multiView: FeatureMultiView,
) : NotificationPage(
    title = "Feature Unlocked",
    emoji = { Emoji.`page 📄` },
    contentRenderer = { multiView.current().render(it) },
    asciiArt = { multiView.current().asciiArt },
    button = ContinueButton { multiView.continueNextOrFinish() })

data class FeatureInfo(private val feature: Feature) : MultiViewSubPage {
    override val asciiArt = feature.asciiArt

    override fun render(textmap: Textmap) {
        textmap.line(">> ${feature.label} <<")
        textmap.emptyLine()
        textmap.multiLine(feature.multilineDescription)
    }

    override fun execute(user: User) {
        user.add(feature)
        feature.mutate(user)
    }
}
