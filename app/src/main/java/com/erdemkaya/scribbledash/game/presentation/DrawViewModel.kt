package com.erdemkaya.scribbledash.game.presentation

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemkaya.scribbledash.game.presentation.components.enums.Difficulty
import com.erdemkaya.scribbledash.game.presentation.components.enums.GameMode
import com.erdemkaya.scribbledash.game.presentation.components.ui.CoinsDataStore
import com.erdemkaya.scribbledash.game.presentation.components.ui.HighScoreDataStore
import com.erdemkaya.scribbledash.game.presentation.components.ui.PurchasedCanvasDataStore
import com.erdemkaya.scribbledash.game.presentation.components.ui.PurchasedPenDataStore
import com.erdemkaya.scribbledash.game.presentation.components.utils.DrawingLoader
import com.erdemkaya.scribbledash.game.presentation.models.PathData
import com.erdemkaya.scribbledash.game.presentation.models.PathModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DrawViewModel(
    private val drawingLoader: DrawingLoader,
    private val dataStore: HighScoreDataStore,
    private val dataStorePen: PurchasedPenDataStore,
    private val dataStoreCanvas: PurchasedCanvasDataStore,
    private val dataStoreCoins: CoinsDataStore,
) : ViewModel() {
    private val _state = MutableStateFlow(DrawingState())
    val state = _state.onStart {
        loadDrawings()
        loadHighScores()
        loadPurchasedPens()
        loadPurchasedCanvas()
        loadCoins()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), DrawingState()
    )

    private val _drawings = MutableStateFlow<List<PathModel>>(emptyList())
    val drawings: StateFlow<List<PathModel>> = _drawings

    private fun loadDrawings() {
        viewModelScope.launch {
            _drawings.value = drawingLoader.loadAllDrawings()
        }
    }

    private fun loadHighScores() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    speedDrawAvgHighScore = dataStore.speedDrawAvg.first(),
                    speedDrawSuccessfulDrawHighScore = dataStore.speedDrawCount.first(),
                    endlessDrawAvgHighScore = dataStore.endlessDrawAvg.first(),
                    endlessDrawSuccessfulDrawHighScore = dataStore.endlessDrawCount.first()
                )
            }
        }
    }

    private fun loadPurchasedPens() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    purchasedPens = dataStorePen.purchasedPens.first(), activePen = "MidnightBlack"
                )
            }
        }
        if ("MidnightBlack" !in state.value.purchasedPens) {
            updatePurchasedPenSet("MidnightBlack", 0)
        }
    }

    private fun loadPurchasedCanvas() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    purchasedCanvasColors = dataStoreCanvas.purchasedCanvas.first(),
                    activeCanvasColor = "White"
                )
            }
        }
        if ("White" !in state.value.purchasedCanvasColors) {
            updatePurchasedCanvasColorSet("White", 0)
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    coinsAvailable = dataStoreCoins.coins.first()
                )
            }
        }
    }

    fun onAction(action: DrawingAction) {
        when (action) {
            DrawingAction.OnClearCanvasClick -> onClearCanvasClick()
            is DrawingAction.OnDraw -> onDraw(action.offset)
            DrawingAction.OnNewPathStart -> onNewPathStart()
            DrawingAction.OnPathEnd -> onPathEnd()
            DrawingAction.OnUndoClick -> onUndo()
            DrawingAction.OnRedoClick -> onRedo()
            is DrawingAction.OnExampleSet -> setCurrentExamplePath(action.exampleSet)
            is DrawingAction.OnDoneClick -> onDone(
                action.example,
                action.user,
                action.score,
                action.successfulDrawCount,
                action.speedDrawCount
            )

            is DrawingAction.OnDifficultySet -> setDifficulty(action.difficulty)
            is DrawingAction.OnGameModeSet -> setGameMode(action.mode)
            is DrawingAction.UpdateHighScore -> updateHighScore(action.newScore, action.key)
            DrawingAction.OnClearDataStore -> clearDataStore()
            DrawingAction.OnClearStatisticsClick -> onClearStatisticsClick()
            DrawingAction.OnSpeedGameEnded -> onSpeedGameEnded()
            is DrawingAction.OnPurchasedPenSet -> updatePurchasedPenSet(action.name, action.price)
            is DrawingAction.OnSetActivePen -> updateActivePen(action.name)
            is DrawingAction.ManipulateDataStore -> manipulateDataStore(action.price)
            is DrawingAction.OnSetActiveCanvasColor -> updateActiveCanvasColor(action.name)
            is DrawingAction.OnPurchasedCanvasColorSet -> updatePurchasedCanvasColorSet(
                action.name,
                action.price
            )

            is DrawingAction.OnCoinsEarned -> updateCoinsEarned(action.coins)
            DrawingAction.OnCoinsUpdate -> updateCoins()
        }
    }

    private fun onSpeedGameEnded() {
        _state.update {
            it.copy(
                speedGameEnded = true
            )
        }
    }

    private fun onClearStatisticsClick() {
        _state.update {
            it.copy(
                speedDrawAvg = 0,
                speedDrawCount = 0,
                successfulDrawings = 0,
                speedGameEnded = false,
                coinsEarned = 0
            )
        }
    }

    private fun onPathEnd() {
        val currentPathData: PathData = _state.value.currentPath ?: return
        _state.update {
            it.copy(
                currentPath = null,
                paths = it.paths + currentPathData,
                undoPaths = (it.undoPaths + currentPathData).takeLast(5),
                redoPaths = emptyList(),
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
                redoPaths = emptyList(),
                resultExample = null,
                resultUser = null,
                speedGameEnded = false
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

    private fun setCurrentExamplePath(exampleSet: PathModel) {
        _state.update {
            it.copy(
                examplePath = exampleSet
            )
        }
    }

    private fun onDone(
        example: PathModel, user: PathModel, score: Int, drawCount: Int, speedDrawCount: Int
    ) {
        _state.update {
            it.copy(
                resultExample = example,
                resultUser = user,
                score = score,
                successfulDrawings = drawCount,
                speedDrawCount = speedDrawCount,
                speedDrawAvg = it.speedDrawAvg + score
            )
        }
    }


    private fun setDifficulty(difficulty: Difficulty) {
        _state.update {
            it.copy(
                difficulty = difficulty
            )
        }
    }


    private fun setGameMode(mode: GameMode) {
        _state.update {
            it.copy(
                mode = mode
            )
        }
    }

    private fun updateHighScore(newScore: Int, key: String) {
        when (key) {
            "speedAvg" -> {
                viewModelScope.launch {
                    dataStore.saveSpeedDrawAvgHighScore(newScore)
                }
                _state.update {
                    it.copy(
                        speedDrawAvgHighScore = newScore
                    )
                }
            }

            "speedCount" -> {
                viewModelScope.launch {
                    dataStore.saveSpeedDrawCountHighScore(newScore)
                }
                _state.update {
                    it.copy(
                        speedDrawSuccessfulDrawHighScore = newScore
                    )
                }
            }

            "endlessAvg" -> {
                viewModelScope.launch {
                    dataStore.saveEndlessDrawAvgHighScore(newScore)
                }
                _state.update {
                    it.copy(
                        endlessDrawAvgHighScore = newScore
                    )
                }
            }

            "endlessCount" -> {
                viewModelScope.launch {
                    dataStore.saveEndlessDrawCountHighScore(newScore)
                }
                _state.update {
                    it.copy(
                        endlessDrawSuccessfulDrawHighScore = newScore
                    )
                }
            }
        }
    }

    private fun clearDataStore() {
        viewModelScope.launch {
            dataStore.clearDataStore()
        }
        _state.update {
            it.copy(
                speedDrawAvgHighScore = 0,
                speedDrawSuccessfulDrawHighScore = 0,
                endlessDrawAvgHighScore = 0,
                endlessDrawSuccessfulDrawHighScore = 0
            )
        }
    }

    private fun updatePurchasedPenSet(name: String, price: Int) {
        viewModelScope.launch {
            dataStorePen.savePurchasedPen(name)
            dataStoreCoins.updateCoins(state.value.coinsAvailable - price)
        }
        _state.update {
            it.copy(
                purchasedPens = it.purchasedPens + name,
                activePen = name,
                coinsAvailable = it.coinsAvailable - price
            )
        }
    }

    private fun updatePurchasedCanvasColorSet(name: String, price: Int) {
        viewModelScope.launch {
            dataStoreCanvas.savePurchasedCanvas(name)
            dataStoreCoins.updateCoins(state.value.coinsAvailable - price)
        }
        _state.update {
            it.copy(
                purchasedCanvasColors = it.purchasedCanvasColors + name,
                activeCanvasColor = name,
                coinsAvailable = it.coinsAvailable - price
            )
        }
    }

    private fun updateActivePen(name: String) {
        _state.update {
            it.copy(
                activePen = name
            )
        }
    }

    private fun updateActiveCanvasColor(name: String) {
        _state.update {
            it.copy(
                activeCanvasColor = name
            )
        }
    }

    private fun updateCoins() {
        viewModelScope.launch {
            dataStoreCoins.updateCoins(_state.value.coinsEarned)
        }
        _state.update {
            it.copy(
                coinsAvailable = it.coinsAvailable + it.coinsEarned
            )
        }
    }

    private fun updateCoinsEarned(coins: Int) {
        _state.update {
            it.copy(
                coinsEarned = it.coinsEarned + coins
            )
        }
    }

    private fun manipulateDataStore(price: Int) {
        viewModelScope.launch {
            dataStoreCoins.updateCoins(price)
        }
        _state.update {
            it.copy(
                coinsAvailable = price
            )
        }
    }

}