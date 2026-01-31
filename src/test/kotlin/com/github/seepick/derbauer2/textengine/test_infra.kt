package com.github.seepick.derbauer2.textengine

import com.github.seepick.derbauer2.textengine.prompt.SelectOption
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.string

fun Arb.Companion.selectOption() = arbitrary {
    SelectOption(label = string(1..5).bind(), onSelected = {})
}

fun Renderer.renderAndToFullString(textmap: Textmap): String {
    render(textmap)
    return textmap.toFullString()
}
