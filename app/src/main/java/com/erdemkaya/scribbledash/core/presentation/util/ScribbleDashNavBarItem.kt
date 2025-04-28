package com.erdemkaya.scribbledash.core.presentation.util

import com.erdemkaya.scribbledash.R

sealed class ScribbleDashNavBarItem(
    var icon: Int,
    var screenRoute: String
) {
    object Home: ScribbleDashNavBarItem(R.drawable.home, "home")
    object Statistics : ScribbleDashNavBarItem(R.drawable.chart, "statistics")
}