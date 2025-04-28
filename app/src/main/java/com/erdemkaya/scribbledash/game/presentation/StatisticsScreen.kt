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
import com.erdemkaya.scribbledash.game.presentation.components.StatisticsCard


@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
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
                text = "Nothing to track...for now",
                value = 0,
                percentage = true
            )
            StatisticsCard(
                icon = ImageVector.vectorResource(R.drawable.stat_bolt),
                text = "Nothing to track...for now",
                value = 0,
                percentage = false
            )
        }
    })
}