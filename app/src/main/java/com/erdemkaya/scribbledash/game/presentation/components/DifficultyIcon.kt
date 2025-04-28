package com.erdemkaya.scribbledash.game.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun DifficultyIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    description: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = Color.Unspecified,
            modifier = modifier
                .size(75.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(50.dp),
                    spotColor = Color.Yellow.copy(.5f)
                )
                .clickable { onClick() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Preview
@Composable
fun DifficultyIconPreview() {
    ScribbleDashTheme {
        DifficultyIcon(
            icon = ImageVector.vectorResource(R.drawable.beginner), description = "Beginner"
        )
    }
}