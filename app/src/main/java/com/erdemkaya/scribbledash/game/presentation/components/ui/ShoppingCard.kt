package com.erdemkaya.scribbledash.game.presentation.components.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.game.presentation.components.enums.PenAndCanvasCategory
import com.erdemkaya.scribbledash.game.presentation.components.utils.CanvasColor
import com.erdemkaya.scribbledash.game.presentation.components.utils.PenColor
import com.erdemkaya.scribbledash.game.presentation.components.utils.getCanvasColorByName
import com.erdemkaya.scribbledash.game.presentation.components.utils.getPenColorByName
import com.erdemkaya.scribbledash.ui.theme.BorderGreen
import com.erdemkaya.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ShoppingCard(
    name: String,
    price: Int,
    category: String,
    purchased: Boolean,
    active: Boolean,
    penOrCanvas: String,
    onClick: () -> Unit = {}
) {
    val borderColor = when {
        active -> BorderGreen
        else -> Color.Transparent
    }

    val backgroundColor = when (category) {
        PenAndCanvasCategory.BASIC.toString() -> Color.White
        PenAndCanvasCategory.PREMIUM.toString() -> MaterialTheme.colorScheme.secondary
        PenAndCanvasCategory.LEGENDARY.toString() -> MaterialTheme.colorScheme.tertiary
        else -> Color.White
    }

    val priceTextColor = when (category) {
        PenAndCanvasCategory.BASIC.toString() -> Color.Black
        PenAndCanvasCategory.PREMIUM.toString() -> Color.White
        PenAndCanvasCategory.LEGENDARY.toString() -> Color.White
        else -> Color.White
    }

    val categoryTextColor = when (category) {
        PenAndCanvasCategory.BASIC.toString() -> MaterialTheme.colorScheme.onSurface
        PenAndCanvasCategory.PREMIUM.toString() -> MaterialTheme.colorScheme.onPrimary.copy(alpha = .8f)
        PenAndCanvasCategory.LEGENDARY.toString() -> MaterialTheme.colorScheme.onPrimary.copy(alpha = .8f)
        else -> Color.White
    }

    val penExampleToShown = when (name) {
        "RainbowPenBrush" -> R.drawable.pen_example2
        "WoodTextureResId" -> R.drawable.woodtexture
        "VintageNotebookResId" -> R.drawable.vintagenotebook
        else -> R.drawable.pen_example
    }

    Box {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
            )
        ) {
            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val penColor = getPenColorByName(name)
                val canvasColor = getCanvasColorByName(name)
                val backgroundBox = when (canvasColor) {
                    is CanvasColor.Solid -> canvasColor.color
                    else -> Color.White
                }

                val tintColor = when (penColor) {
                    is PenColor.Solid -> penColor.color
                    else -> Color.Unspecified
                }
                Text(
                    text = category,
                    style = MaterialTheme.typography.labelSmall,
                    color = categoryTextColor,
                    modifier = Modifier.padding(4.dp)
                )
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.surfaceContainerLow,
                            RoundedCornerShape(12.dp)
                        ), colors = CardDefaults.cardColors(
                        containerColor = (if (penOrCanvas == "Pen") Color.White else backgroundBox)
                    )
                ) {

                    Box(
                        modifier = Modifier
                            .size(width = 80.dp, height = 60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (name != "WoodTextureResId" && name != "VintageNotebookResId") {
                            Icon(
                                imageVector = ImageVector.vectorResource(penExampleToShown),
                                contentDescription = null,
                                tint = if (penOrCanvas == "Pen") tintColor else Color.Transparent,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(2.dp)
                            )
                        } else {
                            Image(
                                bitmap = ImageBitmap.imageResource(penExampleToShown),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        if (!purchased) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.lock),
                                contentDescription = null,
                                tint = Color.Unspecified.copy(alpha = .3f),

                                )
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                if (purchased) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.shop),
                        contentDescription = null,
                        tint = categoryTextColor
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.coin),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = price.toString(),
                            color = priceTextColor,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
            }
        }
        if (active) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 8.dp, y = (-12).dp)
                    .background(Color.White, CircleShape)
                    .size(28.dp)
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.check),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun ShoppingCardPreview() {
    ScribbleDashTheme {
        ShoppingCard(
            name = "VintageNotebookResId",
            price = 10,
            penOrCanvas = "Canvas",
            category = PenAndCanvasCategory.BASIC.toString(),
            purchased = true,
            active = true
        )
    }
}