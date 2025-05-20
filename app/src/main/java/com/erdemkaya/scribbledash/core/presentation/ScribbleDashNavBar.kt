package com.erdemkaya.scribbledash.core.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.erdemkaya.scribbledash.core.presentation.util.ScribbleDashNavBarItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScribbleDashNavBar(
    currentScreen: String, navHostController: NavHostController,
    modifier: Modifier
) {
    val items = listOf(
        ScribbleDashNavBarItem.Statistics,
        ScribbleDashNavBarItem.Home,
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentScreen == item.screenRoute,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = Color.Transparent
                ),
                onClick = {
                    navHostController.navigate(item.screenRoute)
                }, icon = {
                    Icon(
                        painter = painterResource(item.icon), contentDescription = item.screenRoute
                    )
                })
        }
    }

}