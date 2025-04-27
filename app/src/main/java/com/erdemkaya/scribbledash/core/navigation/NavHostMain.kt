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
import com.erdemkaya.scribbledash.game.presentation.HomeScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun NavHostMain(
    drawViewModel: DrawViewModel = koinViewModel()
) {

    val navController = rememberNavController()
    val drawState by drawViewModel.state.collectAsStateWithLifecycle()
    val drawingsState by drawViewModel.drawings.collectAsStateWithLifecycle()

    val examplePath = drawViewModel.resultExamplePath.value
    val userPath = drawViewModel.resultUserPath.value

    NavHost(
        navController = navController, startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                modifier = Modifier,
                navHostController = navController,
            )
        }
        composable("difficulty") {
            DifficultyScreen(
                modifier = Modifier,
                navHostController = navController)
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
                onAction = drawViewModel::onAction)
        }
        composable("result/{score}") {
            val score = it.arguments?.getString("score")?.toIntOrNull() ?: 0
            if (examplePath != null && userPath != null) {
                DrawResultScreen(
                    modifier = Modifier,
                    navHostController = navController,
                    result = score,
                    examplePath = examplePath,
                    userPath = userPath
                )
            }
        }
    }
}
