package com.erdemkaya.scribbledash.game.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashNavBar
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashScaffold
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashTopBar
import com.erdemkaya.scribbledash.game.presentation.components.enums.PenAndCanvasCategory
import com.erdemkaya.scribbledash.game.presentation.components.ui.ShoppingCard

@Composable
fun ShopScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onAction: (DrawingAction) -> Unit,
    state: DrawingState,
) {
    val penList = listOf<Pair<String, PenAndCanvasCategory>>(
        Pair("MidnightBlack", PenAndCanvasCategory.BASIC),
        Pair("CrimsonRed", PenAndCanvasCategory.BASIC),
        Pair("SunshineYellow", PenAndCanvasCategory.BASIC),
        Pair("OceanBlue", PenAndCanvasCategory.BASIC),
        Pair("EmeraldGreen", PenAndCanvasCategory.BASIC),
        Pair("FlameOrange", PenAndCanvasCategory.BASIC),
        Pair("RoseQuartz", PenAndCanvasCategory.PREMIUM),
        Pair("RoyalPurple", PenAndCanvasCategory.PREMIUM),
        Pair("TealDream", PenAndCanvasCategory.PREMIUM),
        Pair("GoldenGlow", PenAndCanvasCategory.PREMIUM),
        Pair("CoralReef", PenAndCanvasCategory.PREMIUM),
        Pair("MajesticIndigo", PenAndCanvasCategory.PREMIUM),
        Pair("CopperAura", PenAndCanvasCategory.PREMIUM),
        Pair("RainbowPenBrush", PenAndCanvasCategory.LEGENDARY),
    )

    val canvasList = listOf<Pair<String, PenAndCanvasCategory>>(
        Pair("White", PenAndCanvasCategory.BASIC),
        Pair("LightGray", PenAndCanvasCategory.BASIC),
        Pair("PaleBeige", PenAndCanvasCategory.BASIC),
        Pair("SoftPowderBlue", PenAndCanvasCategory.BASIC),
        Pair("LightSageGreen", PenAndCanvasCategory.BASIC),
        Pair("PalePeach", PenAndCanvasCategory.BASIC),
        Pair("SoftLavender", PenAndCanvasCategory.BASIC),
        Pair("FadedOlive", PenAndCanvasCategory.PREMIUM),
        Pair("MutedMauve", PenAndCanvasCategory.PREMIUM),
        Pair("DustyBlue", PenAndCanvasCategory.PREMIUM),
        Pair("SoftKhaki", PenAndCanvasCategory.PREMIUM),
        Pair("MutedCoral", PenAndCanvasCategory.PREMIUM),
        Pair("PaleMint", PenAndCanvasCategory.PREMIUM),
        Pair("SoftLilac", PenAndCanvasCategory.PREMIUM),
        Pair("WoodTextureResId", PenAndCanvasCategory.LEGENDARY),
        Pair("VintageNotebookResId", PenAndCanvasCategory.LEGENDARY),
    )

    val pricedPenList = penList.map { (name, category) ->
        val price = when (category) {
            PenAndCanvasCategory.BASIC -> 20
            PenAndCanvasCategory.PREMIUM -> 120
            PenAndCanvasCategory.LEGENDARY -> 999
        }
        Triple(name, category, price)
    }

    val pricedCanvasList = canvasList.map { (name, category) ->
        val price = when (category) {
            PenAndCanvasCategory.BASIC -> 80
            PenAndCanvasCategory.PREMIUM -> 150
            PenAndCanvasCategory.LEGENDARY -> 250
        }
        Triple(name, category, price)
    }

    var selectedTab by remember {
        mutableStateOf("Pen")
    }

    ScribbleDashScaffold(topAppBar = {
        ScribbleDashTopBar(
            title = "Shop",
            showTitle = true,
            modifier = modifier,
            showIcon = true,
            homeScreen = true,
            availableCoins = state.coinsAvailable
        )
    }, bottomBar = {
        ScribbleDashNavBar(
            currentScreen = navHostController.currentDestination?.route!!,
            navHostController = navHostController,
            modifier = modifier
        )
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val selectedTabColor = MaterialTheme.colorScheme.surfaceContainerHigh
                val unselectedTabColor = Color(0xFFEEE7E0).copy(alpha = .5f)
                Surface(
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                    color = if (selectedTab == "Pen") selectedTabColor else unselectedTabColor,
                    tonalElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .zIndex(1f)
                        .clickable {
                            selectedTab = "Pen"
                        }
                ) {
                    Text(
                        text = "Pen",
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black
                    )
                }
                Spacer(Modifier.width(8.dp))
                Surface(
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                    color = if (selectedTab == "Canvas") selectedTabColor else unselectedTabColor,
                    tonalElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f)
                        .clickable {
                            selectedTab = "Canvas"
                        }
                ) {
                    Text(
                        text = "Canvas",
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black
                    )
                }

            }
            Surface(
                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier = Modifier.fillMaxSize()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(if (selectedTab == "Pen") pricedPenList else pricedCanvasList) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            ShoppingCard(
                                name = item.first,
                                price = item.third,
                                category = item.second.toString(),
                                purchased = if (selectedTab == "Pen") item.first in state.purchasedPens else item.first in state.purchasedCanvasColors,
                                active = if (selectedTab == "Pen") item.first == state.activePen else item.first == state.activeCanvasColor,
                                penOrCanvas = selectedTab,
                                onClick = {
                                    if (selectedTab == "Pen") {
                                        if (item.first !in state.purchasedPens) {
                                            if (state.coinsAvailable >= item.third) {
                                                onAction(
                                                    DrawingAction.OnPurchasedPenSet(
                                                        item.first, item.third
                                                    )
                                                )
                                            } else {
                                                // for Testing purposes
                                                onAction(DrawingAction.ManipulateDataStore(450))
                                                Toast.makeText(
                                                    navHostController.context,
                                                    "Not enough coins",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {
                                            onAction(DrawingAction.OnSetActivePen(item.first))
                                        }
                                    } else {
                                        if (item.first !in state.purchasedCanvasColors) {
                                            if (state.coinsAvailable >= item.third) {
                                                onAction(
                                                    DrawingAction.OnPurchasedCanvasColorSet(
                                                        item.first, item.third
                                                    )
                                                )
                                            } else {
                                                Toast.makeText(
                                                    navHostController.context,
                                                    "Not enough coins",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {
                                            onAction(DrawingAction.OnSetActiveCanvasColor(item.first))
                                        }

                                    }
                                })
                        }
                    }
                }
            }
        }
    })
}