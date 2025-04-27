package com.erdemkaya.scribbledash.game.presentation.components

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get

fun calculateScore(userBitmap: Bitmap, exampleBitmap: Bitmap): Float {
    require(userBitmap.width == exampleBitmap.width && userBitmap.height == exampleBitmap.height) {
        "Bitmap sizes must match!"
    }

    var visibleUserPixels = 0
    var matchingUserPixels = 0

    val width = userBitmap.width
    val height = userBitmap.height

    for (y in 0 until height) {
        for (x in 0 until width) {
            val userPixel = userBitmap[x, y]
            val examplePixel = exampleBitmap[x, y]

            val userVisible = Color.alpha(userPixel) != 0
            val exampleVisible = Color.alpha(examplePixel) != 0

            if (userVisible) {
                visibleUserPixels++

                if (exampleVisible) {
                    matchingUserPixels++
                }
            }
        }
    }

    return if (visibleUserPixels == 0) {
        0f
    } else {
        matchingUserPixels.toFloat() / visibleUserPixels.toFloat()
    }
}