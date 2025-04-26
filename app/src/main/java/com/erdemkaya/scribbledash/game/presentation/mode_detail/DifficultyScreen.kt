package com.erdemkaya.scribbledash.game.presentation.mode_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashNavBar
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashScaffold
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashTopBar
import com.erdemkaya.scribbledash.game.presentation.mode_detail.components.DifficultyIcon
import com.erdemkaya.scribbledash.ui.theme.BackgroundGradEnd
import com.erdemkaya.scribbledash.ui.theme.BackgroundGradStart

@Composable
fun DifficultyScreen(
    modifier: Modifier = Modifier, navHostController: NavHostController
) {
    ScribbleDashScaffold(topAppBar = {
        ScribbleDashTopBar(
            title = "", modifier = modifier, showIcon = true, onClickBack = {
                navHostController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            })
    }, bottomBar = {
        ScribbleDashNavBar(
            currentScreen = navHostController.currentDestination?.route!!,
            navHostController = navHostController,
            modifier = modifier
        )
    }, content = { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            BackgroundGradStart, BackgroundGradEnd
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Start drawing!",
                    style = MaterialTheme.typography.displayMedium,
                )
                Text(
                    text = "Choose a difficulty setting", style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                    DifficultyIcon(
                        icon = ImageVector.vectorResource(R.drawable.beginner),
                        description = "Beginner",
                        modifier = Modifier.padding(top = 8.dp),
                        onClick = { navHostController.navigate("draw")}
                    )
                    DifficultyIcon(
                        icon = ImageVector.vectorResource(R.drawable.challenging),
                        description = "Challenging",
                        onClick = { navHostController.navigate("draw")}
                    )
                    DifficultyIcon(
                        icon = ImageVector.vectorResource(R.drawable.master),
                        description = "Master",
                        modifier = Modifier.padding(top = 8.dp),
                        onClick = { navHostController.navigate("draw")}
                    )
                }
            }
        }
    })
}