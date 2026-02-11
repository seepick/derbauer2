package com.github.seepick.derbauer2.textengine

import com.github.seepick.derbauer2.textengine.audio.AudioViewModel
import com.github.seepick.derbauer2.textengine.audio.RealSoundPlayer
import com.github.seepick.derbauer2.textengine.audio.SoundPlayService
import com.github.seepick.derbauer2.textengine.audio.SoundPlayer
import com.github.seepick.derbauer2.textengine.audio.SoundWarningListener
import com.github.seepick.derbauer2.textengine.mainWindow.MainWin
import com.github.seepick.derbauer2.textengine.textmap.Textmap
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.reflect.KClass

fun textengineModule(initPageClass: KClass<out Page>) = module {
    single { CurrentPage(initPageClass) }
    single { Textmap(MainWin.matrixSize) }
    singleOf(::SoundWarningListener)
    singleOf(::RealSoundPlayer) bind SoundPlayer::class
    singleOf(::SoundPlayService)
    viewModelOf(::AudioViewModel)
}
