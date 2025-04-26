package com.erdemkaya.scribbledash.game.presentation.models

import androidx.annotation.DrawableRes
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.game.domain.Mode

data class ModeUi (
    val id: String,
    val name: String,
    val image: String,
    @DrawableRes val iconRes: Int
)

fun Mode.toModeUi(): ModeUi {
    return ModeUi(
        id = id,
        name = name,
        image = image,
        iconRes = R.drawable.one_round_wonder
    )
}