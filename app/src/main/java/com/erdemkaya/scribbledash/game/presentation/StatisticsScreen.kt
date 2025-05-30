package com.erdemkaya.scribbledash.game.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashNavBar
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashScaffold
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashTopBar
import com.erdemkaya.scribbledash.game.presentation.components.ui.StatisticsCard


@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier, navHostController: NavHostController, state: DrawingState
) {
    ScribbleDashScaffold(topAppBar = {
        ScribbleDashTopBar(
            title = "Statistics", modifier = modifier, showIcon = false, onClickBack = {
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
        Column(modifier = Modifier.padding(paddingValues)) {
            StatisticsCard(
                icon = ImageVector.vectorResource(R.drawable.stat_hourglass),
                text = "Highest Speed Draw accuracy %",
                value = state.speedDrawAvgHighScore,
                percentage = true
            )
            StatisticsCard(
                icon = ImageVector.vectorResource(R.drawable.stat_bolt),
                text = "Most Meh+ drawings in Speed Draw",
                value = state.speedDrawSuccessfulDrawHighScore,
                percentage = false
            )
            StatisticsCard(
                icon = ImageVector.vectorResource(R.drawable.star_background),
                text = "Highest Endless Mode accuracy %",
                value = state.endlessDrawAvgHighScore,
                percentage = true
            )
            StatisticsCard(
                icon = ImageVector.vectorResource(R.drawable.palette),
                text = "Most drawings completed in Endless Mode",
                value = state.endlessDrawSuccessfulDrawHighScore,
                percentage = false
            )
        }
    })
}