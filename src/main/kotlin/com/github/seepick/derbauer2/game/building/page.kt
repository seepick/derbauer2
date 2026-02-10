package com.github.seepick.derbauer2.game.building

import com.github.seepick.derbauer2.game.core.Texts
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiSpaceOrEmpty
import com.github.seepick.derbauer2.game.resource.Gold
import com.github.seepick.derbauer2.game.resource.Land
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.view.BackButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.game.view.SecondaryBackButton
import com.github.seepick.derbauer2.game.view.TxResultHandler
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.EmptyPagePromptProvider
import com.github.seepick.derbauer2.textengine.prompt.OptionLabel
import com.github.seepick.derbauer2.textengine.prompt.Options
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt

@Suppress("LongParameterList") // it's okay ;)
class BuildingPage(
    private val user: User,
    private val currentPage: CurrentPage,
    gameRenderer: GameRenderer,
    private val resultHandler: TxResultHandler,
    private val buildingService: BuildingService,
) : PromptGamePage(
    gameRenderer = gameRenderer, promptBuilder = {
        if (user.buildings.isEmpty()) {
            EmptyPagePromptProvider("Not a single thing to build, pah ☹️")
        } else {
            SelectPrompt(
                options = Options.Tabled(user.buildings.map { building ->
                    SelectOption(
                        label = OptionLabel.Table(
                            listOf(
                                "Build ${building.labelSingular}",
                                // once more generic, use the Resource.ViewOrder to sort properly
                                "${user.findResource<Gold>().emojiSpaceOrEmpty}${building.costsGold}",
                                "${user.findResource<Land>().emojiSpaceOrEmpty}${building.landUse}",
                                "(${building.owned})",
                            )
                        ),
                        onSelected = {
                            resultHandler.handle(buildingService.build(building::class))
                        },
                    )
                })
            )
        }
    }, buttons = listOf(
        BackButton {
            currentPage.pageClass = HomePage::class
        },
        SecondaryBackButton {
            currentPage.pageClass = HomePage::class
        },
    ), contentRenderer = { textmap ->
        textmap.line(Texts.buildPage)
    })
