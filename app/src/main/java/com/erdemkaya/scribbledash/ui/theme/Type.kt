package com.erdemkaya.scribbledash.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.erdemkaya.scribbledash.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.outfit_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.bagelfatone_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 66.sp,
        lineHeight = 80.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.bagelfatone_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 44.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.bagelfatone_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        lineHeight = 48.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.bagelfatone_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 30.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.bagelfatone_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.outfit_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.outfit_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.outfit_semibold)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.outfit_medium)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.outfit_semibold)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    ),

)