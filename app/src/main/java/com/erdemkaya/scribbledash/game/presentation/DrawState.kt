package com.erdemkaya.scribbledash.game.presentation

import android.graphics.Path
import android.graphics.RectF
import androidx.compose.runtime.Immutable
import com.erdemkaya.scribbledash.game.presentation.components.Difficulty
import com.erdemkaya.scribbledash.game.presentation.components.PathData
import com.erdemkaya.scribbledash.game.presentation.components.PathModel

@Immutable
data class DrawingState(
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val undoPaths: List<PathData> = emptyList(),
    val redoPaths: List<PathData> = emptyList(),
    val drawings: List<PathModel> = emptyList(),
    val examplePath: PathModel = PathModel(path = Path(), bounds = RectF()),
    val score: Int = 0,
    val resultExample: PathModel? = null,
    val resultUser: PathModel? = null,
    val difficulty: Difficulty = Difficulty.Beginner
)