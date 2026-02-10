package com.github.seepick.derbauer2.game.trade

import com.github.seepick.derbauer2.game.common.Emoji
import com.github.seepick.derbauer2.game.common.emoji
import com.github.seepick.derbauer2.game.core.Texts
import com.github.seepick.derbauer2.game.core.User
import com.github.seepick.derbauer2.game.core.emojiSpaceOrEmpty
import com.github.seepick.derbauer2.game.resource.findResource
import com.github.seepick.derbauer2.game.view.BackButton
import com.github.seepick.derbauer2.game.view.GameRenderer
import com.github.seepick.derbauer2.game.view.HomePage
import com.github.seepick.derbauer2.game.view.PromptGamePage
import com.github.seepick.derbauer2.game.view.SecondaryBackButton
import com.github.seepick.derbauer2.game.view.TxResultHandler
import com.github.seepick.derbauer2.textengine.CurrentPage
import com.github.seepick.derbauer2.textengine.prompt.DynamicLabel
import com.github.seepick.derbauer2.textengine.prompt.Options
import com.github.seepick.derbauer2.textengine.prompt.PromptProvider
import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import com.github.seepick.derbauer2.textengine.prompt.SelectPrompt

class TradePage(
    currentPage: CurrentPage,
    gameRenderer: GameRenderer,
    tradePromptBuilder: TradePromptBuilder,
) : PromptGamePage(
    buttons = listOf(
        BackButton {
            currentPage.pageClass = HomePage::class
        },
        SecondaryBackButton {
            currentPage.pageClass = HomePage::class
        },
    ),
    gameRenderer = gameRenderer,
    promptBuilder = tradePromptBuilder,
    contentRenderer = { textmap ->
        textmap.line(Texts.tradePage)
    }
)

class TradePromptBuilder(
    private val resultHandler: TxResultHandler,
    private val user: User,
    private val tradeService: TradeService,
) : PromptProvider {

    override fun buildPrompt() =
        tradeService.buildOptions().toSingleSelectPrompt()

    private fun TradeCompoundRequests.toSingleSelectPrompt() = SelectPrompt(
        options = Options.Singled(
            items = options
                .sortedByViewOrder(user)
                .map { option ->
                    SelectOption(
                        label = option.buildLabel(),
                        onSelected = { resultHandler.handle(tradeService.trade(option)) },
                    )
                }
        )
    )

    private fun TradeCompoundRequest.buildLabel() = DynamicLabel {
        val targetResource = user.findResource(target.resoureClass)
        val operationLabel = "${operation.label} ${target.amount} " +
                "${targetResource.emojiSpaceOrEmpty}${targetResource.labelSingular}"
        operationLabel + " for " + buildCounterLabel(counters)
    }

    private fun buildCounterLabel(counters: List<TradeAmount>) =
        counters.joinToString(" and ") { counter ->
            val counterResource = user.findResource(counter.resoureClass)
            "${counter.amount} ${counterResource.emojiSpaceOrEmpty}${
                counterResource.labelFor(counter.amount)
            }"
        }
}

fun List<TradeCompoundRequest>.sortedByViewOrder(user: User): List<TradeCompoundRequest> =
    sortedBy { user.findResource(it.target.resoureClass).viewOrder }

private val tradeEmoji = "💸".emoji
@Suppress("ObjectPropertyName", "NonAsciiCharacters")
val Emoji.Companion.`trade 💸` get() = tradeEmoji
