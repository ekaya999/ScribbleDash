package com.erdemkaya.scribbledash.game.presentation

import android.graphics.Path
import android.graphics.RectF
import androidx.compose.runtime.Immutable
import com.erdemkaya.scribbledash.game.presentation.components.enums.Difficulty
import com.erdemkaya.scribbledash.game.presentation.components.enums.GameMode
import com.erdemkaya.scribbledash.game.presentation.models.PathData
import com.erdemkaya.scribbledash.game.presentation.models.PathModel

@Immutable
data class DrawingState(
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val undoPaths: List<PathData> = emptyList(),
    val redoPaths: List<PathData> = emptyList(),
    val drawings: List<PathModel> = emptyList(),
    val examplePath: PathModel = PathModel(path = Path(), bounds = RectF()),
    val score: Int = 0,
    val successfulDrawings: Int = 0,
    val resultExample: PathModel? = null,
    val resultUser: PathModel? = null,
    val difficulty: Difficulty = Difficulty.Beginner,
    val mode: GameMode = GameMode.ONE_ROUND,
    val speedDrawAvg: Int = 0,
    val speedDrawCount: Int = 0,
    val speedDrawAvgHighScore: Int = 0,
    val speedDrawSuccessfulDrawHighScore: Int = 0,
    val endlessDrawAvgHighScore: Int = 0,
    val endlessDrawSuccessfulDrawHighScore: Int = 0,
    val speedGameEnded: Boolean = false,
    val coinsAvailable: Int = 300,
    val purchasedPens: Set<String> = emptySet(),
    val purchasedCanvasColors: Set<String> = emptySet(),
    val activePen: String = "MidnightBlack",
    val activeCanvasColor: String = "White",
    val coinsEarned: Int = 0
)
