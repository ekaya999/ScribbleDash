package com.erdemkaya.scribbledash.game.presentation

import androidx.activity.compose.BackHandler
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
import com.erdemkaya.scribbledash.game.presentation.components.enums.Difficulty
import com.erdemkaya.scribbledash.game.presentation.components.enums.GameMode
import com.erdemkaya.scribbledash.game.presentation.components.ui.DrawCounter
import com.erdemkaya.scribbledash.game.presentation.components.ui.HighScoreCard
import com.erdemkaya.scribbledash.game.presentation.components.utils.ResultComparisonSection
import com.erdemkaya.scribbledash.ui.theme.Pink
import com.erdemkaya.scribbledash.ui.theme.Success
import com.erdemkaya.scribbledash.ui.theme.onBackgroundVariant
import kotlin.math.roundToInt

val oopsFeedbackList = listOf(
    R.string.feedback_oops_1,
    R.string.feedback_oops_2,
    R.string.feedback_oops_3,
    R.string.feedback_oops_4,
    R.string.feedback_oops_5,
    R.string.feedback_oops_6,
    R.string.feedback_oops_7,
    R.string.feedback_oops_8,
    R.string.feedback_oops_9,
    R.string.feedback_oops_10,
)

val goodFeedbackList = listOf(
    R.string.feedback_good_1,
    R.string.feedback_good_2,
    R.string.feedback_good_3,
    R.string.feedback_good_4,
    R.string.feedback_good_5,
    R.string.feedback_good_6,
    R.string.feedback_good_7,
    R.string.feedback_good_8,
    R.string.feedback_good_9,
    R.string.feedback_good_10,
)

val woohooFeedbackList = listOf(
    R.string.feedback_woohoo_1,
    R.string.feedback_woohoo_2,
    R.string.feedback_woohoo_3,
    R.string.feedback_woohoo_4,
    R.string.feedback_woohoo_5,
    R.string.feedback_woohoo_6,
    R.string.feedback_woohoo_7,
    R.string.feedback_woohoo_8,
    R.string.feedback_woohoo_9,
    R.string.feedback_woohoo_10,
)

@Composable
fun DrawResultScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    state: DrawingState,
    onAction: (DrawingAction) -> Unit
) {
    BackHandler {
        onAction(DrawingAction.OnClearCanvasClick)
        navHostController.navigate("difficulty")
    }

    val randomFeedbackId = remember(state.score) {
        getRandomFeedback(state.score)
    }

    var highScoreSpeedAvg by remember {
        mutableStateOf(false)
    }

    var highScoreSpeedCount by remember {
        mutableStateOf(false)
    }

    val scoreAvg = if (state.speedDrawAvg > 0) {
        (state.speedDrawAvg.toDouble() / state.speedDrawCount).roundToInt()
    } else 0

    if (state.speedDrawAvgHighScore < scoreAvg && state.speedGameEnded) {
        onAction(DrawingAction.UpdateHighScore(scoreAvg, "speedAvg"))
        highScoreSpeedAvg = true
    }

    if (state.speedDrawSuccessfulDrawHighScore < state.successfulDrawings && state.speedGameEnded) {
        onAction(DrawingAction.UpdateHighScore(state.successfulDrawings, "speedCount"))
        highScoreSpeedCount = true
    }

    var coinsEarned = 0.0

    ScribbleDashScaffold(topAppBar = {
        ScribbleDashTopBar(
            title = "",
            modifier = modifier,
            showIcon = true,
            onClickBack = {
                onAction(DrawingAction.OnCoinsEarned(coinsEarned.roundToInt()))
                onAction(DrawingAction.OnCoinsUpdate)
                onAction(DrawingAction.OnClearStatisticsClick)
                onAction(DrawingAction.OnClearCanvasClick)
                navHostController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            },
            showTitle = state.mode == GameMode.ENDLESS,
            drawCount = if (state.mode == GameMode.ENDLESS) state.successfulDrawings else 0
        )
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            val text = if (state.mode == GameMode.SPEED) {
                "$scoreAvg%"
            } else {
                "${state.score}%"
            }

            if (state.mode == GameMode.SPEED) {
                Text(
                    text = "Time's up!", style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 24.sp, lineHeight = 28.sp
                    ), color = onBackgroundVariant
                )
                Spacer(Modifier.height(32.dp))
            }

            if (state.mode == GameMode.ONE_ROUND || state.mode == GameMode.ENDLESS) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))

                state.resultExample?.let {
                    state.resultUser?.let { it1 ->
                        ResultComparisonSection(
                            examplePath = it,
                            userPath = it1,
                            mode = state.mode,
                            score = state.score,
                            activePen = state.activePen,
                            activeCanvas = state.activeCanvasColor
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }


            val scoreHeadline: String = when (state.score) {
                in (0..39) -> "Oops"
                in (40..69) -> "Meh"
                in (70..79) -> "Good"
                in (80..89) -> "Great"
                else -> "Woohoo!"
            }
            val feedbackText = stringResource(randomFeedbackId)

            val coinsBase = when (state.score) {
                in (0..0) -> 0
                in (1..39) -> 1
                in (40..69) -> 2
                in (70..79) -> 4
                else -> 6
            }

            val coinsMultiplication: Double = when (state.difficulty) {
                Difficulty.Beginner -> .5
                Difficulty.Challenging -> 1.0
                Difficulty.Master -> 1.75
            }

            coinsEarned = coinsBase * coinsMultiplication
            if (coinsEarned < 1) coinsEarned = 1.0

            if (state.mode == GameMode.ONE_ROUND || state.mode == GameMode.ENDLESS) {
                Text(
                    text = feedbackText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = onBackgroundVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(4.dp))
                DrawCounter(
                    count = coinsEarned.roundToInt(),
                    imageVector = ImageVector.vectorResource(R.drawable.coin),
                    coinsEarned = true
                )
            } else if (state.mode == GameMode.SPEED) {
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
                                        text = text,
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
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                DrawCounter(
                                    count = state.successfulDrawings,
                                    imageVector = if (highScoreSpeedCount) ImageVector.vectorResource(
                                        R.drawable.draw_count_outlined
                                    ) else ImageVector.vectorResource(
                                        R.drawable.draw_count
                                    ),
                                    backgroundColor = if (highScoreSpeedCount) Pink else MaterialTheme.colorScheme.surfaceContainerLow,
                                    highScore = highScoreSpeedCount
                                )
                                DrawCounter(
                                    count = coinsEarned.roundToInt(),
                                    imageVector = ImageVector.vectorResource(R.drawable.coin),
                                    coinsEarned = true
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            if (highScoreSpeedCount) {
                                Text(
                                    text = "NEW HIGH!",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    if (highScoreSpeedAvg) {
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
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onAction(DrawingAction.OnCoinsEarned(coinsEarned.roundToInt()))
                    onAction(DrawingAction.OnClearCanvasClick)

                    if (state.mode != GameMode.ENDLESS) {
                        onAction(DrawingAction.OnCoinsUpdate)
                        onAction(DrawingAction.OnClearStatisticsClick)
                    }

                    if (state.mode == GameMode.SPEED) {
                        navHostController.navigate("draw")
                    } else if (state.mode == GameMode.ENDLESS) {
                        navHostController.navigate("final")
                    } else {
                        navHostController.navigate("difficulty")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(5.dp, Color.White)
            ) {
                if (state.mode == GameMode.SPEED) {
                    Text(
                        text = "DRAW AGAIN",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )
                } else if (state.mode == GameMode.ENDLESS) {
                    Text(
                        text = "FINISH",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )
                } else {
                    Text(
                        text = "TRY AGAIN",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
            if (state.mode == GameMode.ENDLESS && state.score >= 70) {
                Button(
                    onClick = {
                        onAction(DrawingAction.OnCoinsEarned(coinsEarned.roundToInt()))
                        onAction(DrawingAction.OnClearCanvasClick)
                        navHostController.navigate("draw")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Success),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(5.dp, Color.White)
                ) {
                    Text(
                        text = "NEXT DRAWING",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    })
}

fun getRandomFeedback(score: Int): Int {
    val feedbackList = when {
        score < 70 -> oopsFeedbackList
        score < 90 -> goodFeedbackList
        else -> woohooFeedbackList
    }
    return feedbackList.random()
}