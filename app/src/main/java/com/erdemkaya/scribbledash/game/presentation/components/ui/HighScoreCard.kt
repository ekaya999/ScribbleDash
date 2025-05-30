package com.erdemkaya.scribbledash.game.presentation.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun HighScoreCard(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    brush = Brush.linearGradient(listOf(Color(0xFFFADA35), Color(0xFFFF9600))),
                    shape = RoundedCornerShape(100),
                )
                .border(
                    width = 3.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(100)
                )
                .padding(start = 32.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "New High Score",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp, lineHeight = 18.sp
                ),
                color = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .offset(x = (-16).dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Preview
@Composable
private fun HighScoreCardPreview() {
    ScribbleDashTheme {
        HighScoreCard(imageVector = ImageVector.vectorResource(R.drawable.high_score))
    }
}