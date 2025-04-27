package com.erdemkaya.scribbledash.game.presentation

import androidx.compose.runtime.Immutable
import com.erdemkaya.scribbledash.game.presentation.components.PathData

@Immutable
data class DrawingState(
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val undoPaths: List<PathData> = emptyList(),
    val redoPaths: List<PathData> = emptyList()
)