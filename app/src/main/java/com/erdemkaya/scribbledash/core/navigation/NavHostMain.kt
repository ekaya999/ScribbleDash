package com.erdemkaya.scribbledash.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erdemkaya.scribbledash.game.presentation.mode_detail.DifficultyScreen
import com.erdemkaya.scribbledash.game.presentation.mode_detail.DrawScreen
import com.erdemkaya.scribbledash.game.presentation.mode_detail.DrawViewModel
import com.erdemkaya.scribbledash.game.presentation.mode_list.HomeScreen


@Composable
fun NavHostMain() {

    val navController = rememberNavController()
    val drawViewModel = viewModel<DrawViewModel>()
    val drawState by drawViewModel.state.collectAsState()

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
                currentPath = drawState.currentPath,
                undoPaths = drawState.undoPaths,
                redoPaths = drawState.redoPaths,
                onAction = drawViewModel::onAction)
        }
    }
}
