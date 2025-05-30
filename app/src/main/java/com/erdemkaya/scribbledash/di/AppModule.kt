package com.erdemkaya.scribbledash.di

import com.erdemkaya.scribbledash.game.presentation.DrawViewModel
import com.erdemkaya.scribbledash.game.presentation.components.ui.CoinsDataStore
import com.erdemkaya.scribbledash.game.presentation.components.ui.HighScoreDataStore
import com.erdemkaya.scribbledash.game.presentation.components.ui.PurchasedCanvasDataStore
import com.erdemkaya.scribbledash.game.presentation.components.ui.PurchasedPenDataStore
import com.erdemkaya.scribbledash.game.presentation.components.utils.DrawingLoader
import com.erdemkaya.scribbledash.game.presentation.components.utils.SvgParser
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::DrawViewModel)
    single { SvgParser(get()) }
    single { DrawingLoader(get()) }
    single { HighScoreDataStore(get()) }
    single { PurchasedPenDataStore(get()) }
    single { PurchasedCanvasDataStore(get()) }
    single { CoinsDataStore(get()) }
}