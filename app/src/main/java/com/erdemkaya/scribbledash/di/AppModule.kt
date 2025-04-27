package com.erdemkaya.scribbledash.di

import com.erdemkaya.scribbledash.game.presentation.DrawViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::DrawViewModel)
}