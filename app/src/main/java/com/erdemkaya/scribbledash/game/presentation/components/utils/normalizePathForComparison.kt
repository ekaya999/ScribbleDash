package com.erdemkaya.scribbledash.game.presentation.components.utils

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import kotlin.math.min

fun normalizePathForComparison(
    path: Path,
    bounds: RectF,
    strokeWidth: Float,
    strokeScale: Float,
    isUser: Boolean,
    targetSize: Float,
    paddingFactor: Float = 0.1f
): Path {
    val normalizedPath = Path(path)

    val scaleX = (targetSize * (1 - 2 * paddingFactor)) / bounds.width()
    val scaleY = (targetSize * (1 - 2 * paddingFactor)) / bounds.height()
    val scale = min(scaleX, scaleY)

    val dx = -bounds.left
    val dy = -bounds.top

    val tx = (targetSize - bounds.width() * scale) / 2f
    val ty = (targetSize - bounds.height() * scale) / 2f

    val matrix = Matrix().apply {
        postTranslate(dx, dy)
        postScale(scale, scale)
        postTranslate(tx, ty)
    }

    normalizedPath.transform(matrix)
    return normalizedPath
}