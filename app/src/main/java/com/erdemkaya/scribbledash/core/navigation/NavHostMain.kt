package com.erdemkaya.scribbledash.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erdemkaya.scribbledash.game.presentation.DifficultyScreen
import com.erdemkaya.scribbledash.game.presentation.DrawResultScreen
import com.erdemkaya.scribbledash.game.presentation.DrawScreen
import com.erdemkaya.scribbledash.game.presentation.DrawViewModel
import com.erdemkaya.scribbledash.game.presentation.FinalResultScreen
import com.erdemkaya.scribbledash.game.presentation.HomeScreen
import com.erdemkaya.scribbledash.game.presentation.ShopScreen
import com.erdemkaya.scribbledash.game.presentation.StatisticsScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun NavHostMain(
    drawViewModel: DrawViewModel = koinViewModel()
) {

    val navController = rememberNavController()
    val drawState by drawViewModel.state.collectAsStateWithLifecycle()
    val drawingsState by drawViewModel.drawings.collectAsStateWithLifecycle()

    NavHost(
        navController = navController, startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                modifier = Modifier,
                navHostController = navController,
                onAction = drawViewModel::onAction,
                state = drawState
            )
        }
        composable("difficulty") {
            DifficultyScreen(
                modifier = Modifier, navHostController = navController,
                onAction = drawViewModel::onAction
            )
        }
        composable("draw") {
            DrawScreen(
                modifier = Modifier,
                navHostController = navController,
                paths = drawState.paths,
                drawings = drawingsState,
                currentPath = drawState.currentPath,
                undoPaths = drawState.undoPaths,
                redoPaths = drawState.redoPaths,
                difficulty = drawState.difficulty,
                gameMode = drawState.mode,
                successfulDrawCount = drawState.successfulDrawings,
                onAction = drawViewModel::onAction,
                speedDrawCount = drawState.speedDrawCount,
                activePen = drawState.activePen,
                activeCanvas = drawState.activeCanvasColor
            )
        }
        composable("result") {
            DrawResultScreen(
                modifier = Modifier,
                navHostController = navController,
                state = drawState,
                onAction = drawViewModel::onAction
            )
        }
        composable("statistics") {
            StatisticsScreen(
                modifier = Modifier,
                navHostController = navController,
                state = drawState
            )
        }
        composable("final") {
            FinalResultScreen(
                modifier = Modifier,
                navHostController = navController,
                state = drawState,
                onAction = drawViewModel::onAction
            )
        }
        composable("shop") {
            ShopScreen(
                modifier = Modifier,
                navHostController = navController,
                state = drawState,
                onAction = drawViewModel::onAction
            )
        }
    }
}
