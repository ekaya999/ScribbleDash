package com.erdemkaya.scribbledash.game.presentation.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

@Composable
fun countdownTimer(
    totalSeconds: Int = 120, start: Boolean = false,
    drawMode: Boolean = false
): State<Int> {
    val timeLeft = remember { mutableIntStateOf(totalSeconds) }

    LaunchedEffect(start, drawMode) {
        while (timeLeft.intValue > 0 && start && drawMode) {
            delay(1000L)
            timeLeft.intValue--
        }
    }

    return timeLeft
}