package com.github.seepick.derbauer2.textengine.audio

import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.github.seepick.derbauer2.textengine.compose.Tooltip
import org.koin.compose.viewmodel.koinViewModel

@Suppress("FunctionName")
@Composable
fun SoundToggleButton(
    viewModel: AudioViewModel = koinViewModel(),
) {
    Tooltip(viewModel.soundButtonTooltip) {
        IconButton(onClick = {
            viewModel.onToggleSoundEnabled()
        }) {
            Text(if (viewModel.isSoundEnabled) "🔔" else "🔕")
        }
    }
}
