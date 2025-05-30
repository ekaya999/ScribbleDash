package com.erdemkaya.scribbledash.game.presentation

import androidx.compose.ui.geometry.Offset
import com.erdemkaya.scribbledash.game.presentation.components.enums.Difficulty
import com.erdemkaya.scribbledash.game.presentation.components.enums.GameMode
import com.erdemkaya.scribbledash.game.presentation.models.PathModel

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
    data object OnSpeedGameEnded : DrawingAction
    data class OnSetActivePen(val name: String) : DrawingAction
    data class OnSetActiveCanvasColor(val name: String) : DrawingAction
    data class OnPurchasedPenSet(val name: String, val price: Int) : DrawingAction
    data class OnPurchasedCanvasColorSet(val name: String, val price: Int) : DrawingAction
    data class OnCoinsEarned(val coins: Int) : DrawingAction
    data object OnCoinsUpdate : DrawingAction
    data class ManipulateDataStore(val price: Int) : DrawingAction
}