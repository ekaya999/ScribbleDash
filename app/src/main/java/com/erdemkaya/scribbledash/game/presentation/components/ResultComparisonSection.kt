package com.erdemkaya.scribbledash.game.presentation.components

import android.graphics.Path
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultComparisonSection(
    examplePath: Path,
    userPath: Path
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ResultCanvasCard(title = "Example", path = examplePath)
        ResultCanvasCard(title = "Drawing", path = userPath)
    }
}