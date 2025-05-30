package com.erdemkaya.scribbledash.game.presentation.components.utils

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import androidx.compose.ui.geometry.Size

fun normalizePathToCanvas(
    originalPath: Path,
    originalBounds: RectF,
    targetSize: Size
): Path {
    val scaleX = (targetSize.width * 0.5f) / originalBounds.width()
    val scaleY = (targetSize.height * 0.5f) / originalBounds.height()
    val scale = minOf(scaleX, scaleY)

    val dx = (targetSize.width - originalBounds.width() * scale) / 2f - originalBounds.left * scale
    val dy = (targetSize.height - originalBounds.height() * scale) / 2f - originalBounds.top * scale

    val matrix = Matrix().apply {
        postScale(scale, scale)
        postTranslate(dx, dy)
    }

    return Path(originalPath).apply {
        transform(matrix)
    }
}