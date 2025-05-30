package com.erdemkaya.scribbledash.game.presentation.components.utils

import androidx.compose.ui.graphics.Color
import com.erdemkaya.scribbledash.R

sealed class CanvasColor {
    data class Solid(val color: Color) : CanvasColor()
}

val CanvasColorMap = mapOf(
    "White" to CanvasColor.Solid(Color(0xFFFFFFFF)),
    "LightGray" to CanvasColor.Solid(Color(0xFFE0E0E0)),
    "PaleBeige" to CanvasColor.Solid(Color(0xFFF5F5DC)),
    "SoftPowderBlue" to CanvasColor.Solid(Color(0xFFB0C4DE)),
    "LightSageGreen" to CanvasColor.Solid(Color(0xFFD3E8D2)),
    "PalePeach" to CanvasColor.Solid(Color(0xFFF4E1D9)),
    "SoftLavender" to CanvasColor.Solid(Color(0xFFE7D8E9)),


    "FadedOlive" to CanvasColor.Solid(Color(0xFFB8CBB8)),
    "MutedMauve" to CanvasColor.Solid(Color(0xFFD1B2C1)),
    "DustyBlue" to CanvasColor.Solid(Color(0xFFA3BFD9)),
    "SoftKhaki" to CanvasColor.Solid(Color(0xFFD8D6C1)),
    "MutedCoral" to CanvasColor.Solid(Color(0xFFF2C5C3)),
    "PaleMint" to CanvasColor.Solid(Color(0xFFD9EDE1)),
    "SoftLilac" to CanvasColor.Solid(Color(0xFFE2D3E8))
)

// Legendary textures
val WoodTextureResId = R.drawable.woodtexture
val VintageNotebookResId = R.drawable.vintagenotebook

fun getCanvasColorByName(name: String): CanvasColor? = CanvasColorMap[name]

fun getImageIdByName(name: String): Int? {
    return when (name) {
        "WoodTextureResId" -> WoodTextureResId
        "VintageNotebookResId" -> VintageNotebookResId
        else -> null
    }
}