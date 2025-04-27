package com.erdemkaya.scribbledash.game.presentation

import androidx.compose.runtime.Immutable
import com.erdemkaya.scribbledash.game.presentation.components.PathData
import com.erdemkaya.scribbledash.game.presentation.components.PathModel

@Immutable
data class DrawingState(
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val undoPaths: List<PathData> = emptyList(),
    val redoPaths: List<PathData> = emptyList(),
    val drawings: List<PathModel> = emptyList()
)