package com.erdemkaya.scribbledash.game.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashScaffold
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashTopBar
import com.erdemkaya.scribbledash.game.presentation.components.ui.DrawCounter
import com.erdemkaya.scribbledash.game.presentation.components.ui.HighScoreCard
import com.erdemkaya.scribbledash.ui.theme.Pink
import com.erdemkaya.scribbledash.ui.theme.onBackgroundVariant
import kotlin.math.roundToInt

@Composable
fun FinalResultScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    state: DrawingState,
    onAction: (DrawingAction) -> Unit
) {
    val randomFeedbackId = remember(state.score) {
        getRandomFeedback(state.score)
    }

    var highScoreEndlessAvg by remember {
        mutableStateOf(false)
    }

    var highScoreEndlessCount by remember {
        mutableStateOf(false)
    }

    val scoreAvg = if (state.speedDrawAvg > 0) {
        (state.speedDrawAvg.toDouble() / state.speedDrawCount).roundToInt()
    } else 0

    if (state.endlessDrawAvgHighScore < scoreAvg) {
        onAction(DrawingAction.UpdateHighScore(scoreAvg, "endlessAvg"))
        highScoreEndlessAvg = true
    }

    if (state.endlessDrawSuccessfulDrawHighScore < state.successfulDrawings) {
        onAction(DrawingAction.UpdateHighScore(state.successfulDrawings, "endlessCount"))
        highScoreEndlessCount = true
    }

    ScribbleDashScaffold(topAppBar = {
        ScribbleDashTopBar(
            title = "",
            modifier = modifier,
            showIcon = true,
            onClickBack = {
                onAction(DrawingAction.OnCoinsUpdate)
                onAction(DrawingAction.OnClearCanvasClick)
                onAction(DrawingAction.OnClearStatisticsClick)
                navHostController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            },
        )
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Game over!", style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 24.sp, lineHeight = 28.sp
                ), color = onBackgroundVariant
            )
            Spacer(Modifier.height(32.dp))

            val scoreHeadline: String = when (state.score) {
                in (0..39) -> "Oops"
                in (40..69) -> "Meh"
                in (70..79) -> "Good"
                in (80..89) -> "Great"
                else -> "Woohoo!"
            }
            val feedbackText = stringResource(randomFeedbackId)

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .heightIn(min = 150.dp)
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.background
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 100.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "$scoreAvg%",
                                    style = MaterialTheme.typography.displayLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                        }

                        Text(
                            text = scoreHeadline,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = feedbackText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = onBackgroundVariant,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            DrawCounter(
                                count = state.successfulDrawings,
                                imageVector = if (highScoreEndlessCount) ImageVector.vectorResource(
                                    R.drawable.draw_count_outlined
                                ) else ImageVector.vectorResource(
                                    R.drawable.draw_count
                                ),
                                backgroundColor = if (highScoreEndlessCount) Pink else MaterialTheme.colorScheme.surfaceContainerLow,
                                highScore = highScoreEndlessCount
                            )
                            DrawCounter(
                                count = state.coinsEarned,
                                imageVector = ImageVector.vectorResource(R.drawable.coin),
                                coinsEarned = true
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        if (highScoreEndlessCount) {
                            Text(
                                text = "NEW HIGH!",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                if (highScoreEndlessAvg) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-16).dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        HighScoreCard(
                            imageVector = ImageVector.vectorResource(R.drawable.high_score)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onAction(DrawingAction.OnCoinsUpdate)
                    onAction(DrawingAction.OnClearCanvasClick)
                    onAction(DrawingAction.OnClearStatisticsClick)
                    navHostController.navigate("draw")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(5.dp, Color.White)
            ) {
                Text(
                    text = "DRAW AGAIN",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    })
}