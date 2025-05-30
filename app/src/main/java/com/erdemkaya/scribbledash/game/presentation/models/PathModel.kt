package com.erdemkaya.scribbledash.game.presentation.models

import android.graphics.Path
import android.graphics.RectF


data class PathModel(
    val path: Path,
    val bounds: RectF
)