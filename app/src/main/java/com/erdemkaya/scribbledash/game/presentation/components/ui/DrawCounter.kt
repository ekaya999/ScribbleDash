package com.erdemkaya.scribbledash.game.presentation.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun DrawCounter(
    count: Int,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    highScore: Boolean = false,
    coinsEarned: Boolean = false
) {
    Box(
        modifier = modifier
            .height(30.dp)
            .width(73.dp), contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Spacer(Modifier.width(4.dp))
            Row(
                modifier = Modifier
                    .width(60.dp)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(100)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            )
            {
                Spacer(Modifier.width(4.dp))
                val text = if (coinsEarned) "+" else ""
                Text(
                    text = text + "$count", style = MaterialTheme.typography.headlineSmall,
                    color = if (highScore) Color.White else MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart),
            imageVector = imageVector,
            contentDescription = null,
            tint = Color.Unspecified
        )
    }

}

@Preview
@Composable
private fun DrawCounterPreview() {
    ScribbleDashTheme {
        DrawCounter(count = 1, imageVector = ImageVector.vectorResource(R.drawable.draw_count))
    }

}