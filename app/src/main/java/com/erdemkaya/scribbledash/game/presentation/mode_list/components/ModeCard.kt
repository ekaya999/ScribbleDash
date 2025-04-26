package com.erdemkaya.scribbledash.game.presentation.mode_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.ui.theme.BorderGreen
import com.erdemkaya.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ModeCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    Card(
        modifier = modifier.height(100.dp).fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(15),
        border = BorderStroke(7.dp, BorderGreen),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, contentColor = contentColor
        )
    ) {
        Row(
            modifier = modifier.fillMaxSize()
        ) {
            Row(modifier = modifier.fillMaxWidth(.6f).fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                    maxLines = 2,
                )
            }
            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize().padding(bottom = 2.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview
@Composable
private fun ModeCardPreview() {
    ScribbleDashTheme {
        ModeCard(
            title = "One Round Wonder",
            icon = ImageVector.vectorResource(id = R.drawable.one_round_wonder),
        )
    }
}