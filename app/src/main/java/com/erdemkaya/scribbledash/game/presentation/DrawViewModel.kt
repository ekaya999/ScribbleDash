package com.erdemkaya.scribbledash.game.presentation

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemkaya.scribbledash.game.presentation.components.DrawingLoader
import com.erdemkaya.scribbledash.game.presentation.components.PathData
import com.erdemkaya.scribbledash.game.presentation.components.PathModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DrawViewModel(
    private val drawingLoader: DrawingLoader
) : ViewModel() {
    private val _state = MutableStateFlow(DrawingState())
    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), DrawingState()
    )

    private val _drawings = MutableStateFlow<List<PathModel>>(emptyList())
    val drawings: StateFlow<List<PathModel>> = _drawings

    init {
        loadDrawings()
    }

    private fun loadDrawings() {
        viewModelScope.launch {
            _drawings.value = drawingLoader.loadAllDrawings()
        }
        println("Paths: " + drawings.value[0].path.getBounds())
    }

    fun Path.getBounds(): android.graphics.RectF {
        val bounds = android.graphics.RectF()
        this.computeBounds(bounds, true)
        return bounds
    }

    fun onAction(action: DrawingAction) {
        when (action) {
            DrawingAction.OnClearCanvasClick -> onClearCanvasClick()
            is DrawingAction.OnDraw -> onDraw(action.offset)
            DrawingAction.OnNewPathStart -> onNewPathStart()
            DrawingAction.OnPathEnd -> onPathEnd()
            DrawingAction.OnUndoClick -> onUndo()
            DrawingAction.OnRedoClick -> onRedo()
        }
    }

    private fun onPathEnd() {
        val currentPathData :PathData = _state.value.currentPath ?: return
        _state.update {
            it.copy(
                currentPath = null,
                paths = it.paths + currentPathData,
                undoPaths = (it.undoPaths + currentPathData).takeLast(5),
                redoPaths = emptyList()

            )
        }
    }

    private fun onNewPathStart() {
        _state.update {
            it.copy(
                currentPath = PathData(
                    path = emptyList()
                )
            )
        }
    }

    private fun onDraw(offset: Offset) {
        val currentPathData = state.value.currentPath ?: return
        _state.update {
            it.copy(
                currentPath = currentPathData.copy(
                    path = currentPathData.path + offset
                )
            )
        }
    }

    private fun onClearCanvasClick() {
        _state.update {
            it.copy(
                currentPath = null,
                paths = emptyList(),
                undoPaths = emptyList(),
                redoPaths = emptyList()
            )
        }
    }

    private fun onUndo() {
        val paths = _state.value.paths
        val undoPaths = _state.value.undoPaths

        if (paths.isNotEmpty()) {
            val lastPath = paths.last()
            _state.update {
                it.copy(
                    paths = paths.dropLast(1),
                    undoPaths = undoPaths.dropLast(1),
                    redoPaths = (it.redoPaths + lastPath).takeLast(5)
                )
            }
        }
    }

    private fun onRedo() {
        val redoPaths = _state.value.redoPaths
        if (redoPaths.isNotEmpty()) {
            val lastRedo = redoPaths.last()
            _state.update {
                it.copy(
                    paths = it.paths + lastRedo,
                    undoPaths = it.undoPaths + lastRedo,
                    redoPaths = redoPaths.dropLast(1)
                )
            }
        }
    }
}