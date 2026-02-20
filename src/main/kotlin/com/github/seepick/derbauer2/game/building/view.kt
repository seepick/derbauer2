package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.common.emoji.Emoji
import com.github.seepick.derbauer2.common.emoji.emoji
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.toEffects
import com.github.seepick.derbauer2.game.core.toFormattedString
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.view.BackButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.game.view.SecondaryBackButton
import com.github.seepick.derbauer2.game.view.Texts
import com.github.seepick.derbauer2.game.view.TxResultHandler
import com.github.seepick.derbauer2.game.view.ViewOrder
import com.github.seepick.derbauer2.game.view.emojiSpaceOrEmpty
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.EmptyPagePromptProvider
import com.github.seepick.derbauer2.textengine.prompt.OptionLabel
import com.github.seepick.derbauer2.textengine.prompt.Options
import com.github.seepick.derbauer2.textengine.prompt.Prompt
import com.github.seepick.derbauer2.textengine.prompt.PromptProvider
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt
import java.util.concurrent.atomic.AtomicInteger

@Suppress("LongParameterList") // it's okay ;)
class BuildingPage(
    private val currentPage: CurrentPage,
    gameRenderer: GameRenderer,
    promptProvider: BuildPromptProvider,
) : PromptGamePage(
    gameRenderer = gameRenderer,
    promptProvider = promptProvider,
    buttons = listOf(
        BackButton {
            currentPage.current = HomePage::class
        },
        SecondaryBackButton {
            currentPage.current = HomePage::class
        },
    ), contentRenderer = { textmap ->
        textmap.line(Texts.buildPage)
    })

class BuildPromptProvider(
    private val resultHandler: TxResultHandler,
    private val user: User,
    private val buildingService: BuildingService,
) : PromptProvider {

    override fun providePrompt(): Prompt =
        if (user.buildings.isEmpty()) {
            EmptyPagePromptProvider("Not a single thing to build, pah ☹️")
        } else {
            SelectPrompt(
                options = Options.Tabled(
                    items = buildingService.buildOptions()
                        .sortedByViewOrder()
                        .map { option ->
                            SelectOption(
                                label = option.buildLabel(),
                                onSelected = {
                                    val txResult = buildingService.build(option.building::class)
                                    resultHandler.handle(txResult)
                                },
                            )
                        }
                )
            )
        }

    private fun BuildOption.buildLabel() = OptionLabel.Table(
        listOf(
            "Build ${building.labelSingular}",
            // once more generic, use the ViewOrder to sort properly
            "${Gold.Data.emojiSpaceOrEmpty}${building.costsGold}",
            "${Land.Data.emojiSpaceOrEmpty}${building.landUse}",
            "(${building.owned})",
            building.toEffects().toFormattedString()
        )
    )
}

private fun List<BuildOption>.sortedByViewOrder(): List<BuildOption> =
    sortedBy { it.building.viewOrder }

val ViewOrder.Companion.Building get() = BuildingOrder

object BuildingOrder {
    private val counter = AtomicInteger()

    val Tent = counter.incrementAndGet()
    val Field = counter.incrementAndGet()
    val Granary = counter.incrementAndGet()
    val Theater = counter.incrementAndGet()
    val School = counter.incrementAndGet()
}

private val buildEmoji = "🛠️".emoji
@Suppress("ObjectPropertyName", "NonAsciiCharacters")
val Emoji.Companion.`build 🛠️` get() = buildEmoji
