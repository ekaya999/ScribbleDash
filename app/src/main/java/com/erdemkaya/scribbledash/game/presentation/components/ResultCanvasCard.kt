package com.erdemkaya.scribbledash.game.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ResultCanvasCard(
    title: String,
    pathModel: PathModel,
    rotation: Float,
    topPadding: Dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .rotate(rotation)
        )
        Spacer(modifier = Modifier.height(topPadding))
        Card(
            modifier = Modifier
                .size(140.dp)
                .rotate(rotation)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = Color.Yellow.copy(0.5f)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val normalizedPath = normalizePathToCanvas(
                    pathModel.path, pathModel.bounds, size
                )
                drawPath(
                    path = normalizedPath.asComposePath(),
                    color = Color.Black,
                    style = Stroke(width = 5f)
                )
            }
        }
    }
}

