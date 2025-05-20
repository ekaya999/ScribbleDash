package com.erdemkaya.scribbledash.core.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.game.presentation.components.DrawCounter
import com.erdemkaya.scribbledash.game.presentation.components.GameMode
import com.erdemkaya.scribbledash.ui.theme.ScribbleDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScribbleDashTopBar(
    modifier: Modifier = Modifier,
    title: String,
    showIcon: Boolean = false,
    showTitle: Boolean = false,
    onClickBack: () -> Unit = {},
    homeScreen: Boolean = false,
    countdownTime: Int = 120,
    drawCount: Int = 0,
    gameMode: GameMode = GameMode.ONE_ROUND
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isWarning = countdownTime < 31
    val ringColor =
        if (isWarning) MaterialTheme.colorScheme.onSurface else Color.Transparent
    val textColor =
        if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground


    TopAppBar(
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            if (showTitle) {
                if (gameMode == GameMode.ONE_ROUND) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = title,
                            style = if (homeScreen) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.labelLarge.copy(
                                fontSize = 24.sp,
                                lineHeight = 28.sp
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                } else if (gameMode == GameMode.SPEED || gameMode == GameMode.ENDLESS) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        DrawCounter(
                            drawCount,
                            imageVector = ImageVector.vectorResource(R.drawable.draw_count),
                            modifier = Modifier
                        )
                    }
                }
            }
        },
        actions = {
            if (showIcon) {
                if (homeScreen) {

                } else {
                    IconButton(
                        onClick = {
                            onClickBack()
                        }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.close_circle),
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        },
        navigationIcon = {
            if (gameMode == GameMode.SPEED) {
                Box(
                    modifier = modifier.size(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidthOuter = 8f
                        val radiusOuter = size.minDimension / 2 - strokeWidthOuter

                        drawCircle(
                            color = ringColor,
                            radius = radiusOuter,
                            style = Stroke(width = strokeWidthOuter)
                        )
                    }
                    Card(
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val minutes = countdownTime / 60
                            val seconds = countdownTime % 60
                            Text(
                                text = "%d:%02d".format(minutes, seconds),
                                color = textColor,
                                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 14.sp)
                            )
                        }
                    }
                }
            }
        })
}


@Preview
@Composable
fun MyTopAppBarPreview() {
    ScribbleDashTheme {
        ScribbleDashTopBar(
            title = "ScribbleDash",
            showIcon = true,
            showTitle = true,
            homeScreen = true,
            countdownTime = 30,
            drawCount = 5,
            gameMode = GameMode.SPEED
        )
    }
}