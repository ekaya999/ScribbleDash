package com.erdemkaya.scribbledash.game.presentation.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.game.presentation.components.enums.GameMode
import com.erdemkaya.scribbledash.game.presentation.components.ui.ResultCanvasCard
import com.erdemkaya.scribbledash.game.presentation.models.PathModel

@Composable
fun ResultComparisonSection(
    examplePath: PathModel, userPath: PathModel, mode: GameMode,
    score: Int, activePen: String, activeCanvas: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        val penColor = getPenColorByName(activePen)
        val tintColor = when (penColor) {
            is PenColor.Solid -> penColor.color
            else -> Color.Black
        }
        val canvasColor = getCanvasColorByName(activeCanvas)
        var canvasSpecial = false
        var backgroundBox = Color.White
        when (canvasColor) {
            is CanvasColor.Solid -> {
                backgroundBox = canvasColor.color
                canvasSpecial = false
            }

            else -> {
                backgroundBox = Color.White
                canvasSpecial = true
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val paddingMode = if (mode == GameMode.ONE_ROUND) 16.dp else 0.dp
            ResultCanvasCard(
                title = "Example",
                pathModel = examplePath,
                rotation = -10f,
                topPadding = 0.dp,
            )
            ResultCanvasCard(
                title = "Drawing",
                pathModel = userPath,
                rotation = 10f,
                topPadding = paddingMode,
                containerColor = backgroundBox,
                penColor = tintColor,
                specialCanvas = canvasSpecial,
                activeCanvas = activeCanvas
            )
        }
        if (mode == GameMode.ENDLESS) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.White, shape = RoundedCornerShape(100)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = if (score >= 70) ImageVector.vectorResource(R.drawable.check) else ImageVector.vectorResource(
                            R.drawable.cross
                        ),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(56.dp)
                    )
                }
            }
        }
    }
}