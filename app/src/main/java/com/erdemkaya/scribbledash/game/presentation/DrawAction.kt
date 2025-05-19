package com.erdemkaya.scribbledash.game.presentation

import androidx.compose.ui.geometry.Offset
import com.erdemkaya.scribbledash.game.presentation.components.Difficulty
import com.erdemkaya.scribbledash.game.presentation.components.GameMode
import com.erdemkaya.scribbledash.game.presentation.components.PathModel

sealed interface DrawingAction {
    data object OnNewPathStart : DrawingAction
    data class OnDraw(val offset: Offset) : DrawingAction
    data object OnPathEnd : DrawingAction
    data object OnClearCanvasClick : DrawingAction
    data object OnClearStatisticsClick : DrawingAction
    data object OnUndoClick : DrawingAction
    data object OnRedoClick : DrawingAction
    data class OnExampleSet(val exampleSet: PathModel) : DrawingAction
    data class OnDoneClick(
        val example: PathModel,
        val user: PathModel,
        val score: Int,
        val successfulDrawCount: Int,
        val speedDrawCount: Int
    ) : DrawingAction
    data class OnDifficultySet(val difficulty: Difficulty) : DrawingAction
    data class OnGameModeSet(val mode: GameMode) : DrawingAction
    data class UpdateHighScore(val newScore: Int, val key: String) : DrawingAction
    data object OnClearDataStore : DrawingAction
}