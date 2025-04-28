package com.erdemkaya.scribbledash.game.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashScaffold
import com.erdemkaya.scribbledash.core.presentation.ScribbleDashTopBar
import com.erdemkaya.scribbledash.game.presentation.components.ResultComparisonSection
import com.erdemkaya.scribbledash.ui.theme.onBackgroundVariant

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

    ScribbleDashScaffold(topAppBar = {
        ScribbleDashTopBar(
            title = "", modifier = modifier, showIcon = true, onClickBack = {
                onAction(DrawingAction.OnClearCanvasClick)
                navHostController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            })
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "${state.score}%",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            state.resultExample?.let {
                state.resultUser?.let { it1 ->
                    ResultComparisonSection(
                        examplePath = it, userPath = it1
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            val scoreHeadline: String = when (state.score) {
                in (0..39) -> "Oops"
                in (40..69) -> "Meh"
                in (70..79) -> "Good"
                in (80..89) -> "Great"
                else -> "Woohoo!"
            }

            Text(
                text = scoreHeadline,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            val feedbackText = stringResource(randomFeedbackId)

            Text(
                text = feedbackText,
                style = MaterialTheme.typography.bodyMedium,
                color = onBackgroundVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onAction(DrawingAction.OnClearCanvasClick)
                    navHostController.navigate("difficulty")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(5.dp, Color.White)
            ) {
                Text(
                    text = "TRY AGAIN",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
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