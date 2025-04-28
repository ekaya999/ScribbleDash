package com.erdemkaya.scribbledash.game.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultComparisonSection(
    examplePath: PathModel,
    userPath: PathModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ResultCanvasCard(title = "Example", pathModel = examplePath, rotation = -10f, 0.dp)
        ResultCanvasCard(title = "Drawing", pathModel = userPath, rotation = 10f, 16.dp)
    }
}