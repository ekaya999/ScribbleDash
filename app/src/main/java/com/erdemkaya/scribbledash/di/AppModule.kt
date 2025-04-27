package com.erdemkaya.scribbledash.di

import com.erdemkaya.scribbledash.game.presentation.DrawViewModel
import com.erdemkaya.scribbledash.game.presentation.components.DrawingLoader
import com.erdemkaya.scribbledash.game.presentation.components.SvgParser
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::DrawViewModel)
    single { SvgParser(get()) }
    single { DrawingLoader(get()) }
}