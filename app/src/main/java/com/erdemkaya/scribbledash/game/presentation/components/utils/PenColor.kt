package com.erdemkaya.scribbledash.game.presentation.components.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

sealed class PenColor {
    data class Solid(val color: Color) : PenColor()
    data class Gradient(val brush: Brush) : PenColor()
}

val PenColorMap = mapOf(
    "MidnightBlack" to PenColor.Solid(Color(0xFF101820)),
    "CrimsonRed" to PenColor.Solid(Color(0xFFB22234)),
    "SunshineYellow" to PenColor.Solid(Color(0xFFF9D85D)),
    "OceanBlue" to PenColor.Solid(Color(0xFF1D4E89)),
    "EmeraldGreen" to PenColor.Solid(Color(0xFF4CAF50)),
    "FlameOrange" to PenColor.Solid(Color(0xFFF57F20)),

    "RoseQuartz" to PenColor.Solid(Color(0xFFF4A6B8)),
    "RoyalPurple" to PenColor.Solid(Color(0xFF6A0FAB)),
    "TealDream" to PenColor.Solid(Color(0xFF008C92)),
    "GoldenGlow" to PenColor.Solid(Color(0xFFFFD700)),
    "CoralReef" to PenColor.Solid(Color(0xFFFF6F61)),
    "MajesticIndigo" to PenColor.Solid(Color(0xFF4B0082)),
    "CopperAura" to PenColor.Solid(Color(0xFFB87333)),

    "RainbowPenBrush" to PenColor.Gradient(
        Brush.linearGradient(
            colors = listOf(
                Color(0xFFFB02FB),
                Color(0xFF0000FF),
                Color(0xFF00EEFF),
                Color(0xFF008000),
                Color(0xFFFFFF00),
                Color(0xFFFFA500),
                Color(0xFFFF0000),
            )
        )
    )
)

fun getPenColorByName(name: String): PenColor? = PenColorMap[name]