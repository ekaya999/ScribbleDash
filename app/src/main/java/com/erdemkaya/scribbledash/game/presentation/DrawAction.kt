package com.erdemkaya.scribbledash.game.presentation

import androidx.compose.ui.geometry.Offset

sealed interface DrawingAction {
    data object OnNewPathStart : DrawingAction
    data class OnDraw(val offset: Offset) : DrawingAction
    data object OnPathEnd : DrawingAction
    data object OnClearCanvasClick : DrawingAction
    data object OnUndoClick : DrawingAction
    data object OnRedoClick : DrawingAction
}