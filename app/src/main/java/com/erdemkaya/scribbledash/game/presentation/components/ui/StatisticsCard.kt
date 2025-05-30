package com.erdemkaya.scribbledash.game.presentation.components.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.erdemkaya.scribbledash.ui.theme.onBackgroundVariant

@Composable
fun StatisticsCard(
    icon: ImageVector,
    text: String,
    value: Int,
    percentage: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = text, style = MaterialTheme.typography.bodySmall,
                color = onBackgroundVariant
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = if (percentage) "${value.coerceIn(0, 9999)}%" else "${
                    value.coerceIn(
                        0,
                        9999
                    )
                }", style = MaterialTheme.typography.headlineLarge,
                color = onBackgroundVariant
            )
        }
    }
}