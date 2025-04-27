package com.erdemkaya.scribbledash.game.presentation.components

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF

fun normalizePathForComparison(
    originalPath: Path,
    originalBounds: RectF,
    userStrokeWidth: Float,
    exampleStrokeWidth: Float,
    isUser: Boolean,
    targetSize: Float
): Path {
    val path = Path(originalPath)

    val bounds = RectF(originalBounds)

    val insetAmount = userStrokeWidth / 2f

    if (isUser) {
        val userInsetAdjustment = (exampleStrokeWidth - userStrokeWidth) / 2f
        bounds.inset(insetAmount + userInsetAdjustment, insetAmount + userInsetAdjustment)
    } else {
        bounds.inset(insetAmount, insetAmount)
    }

    val matrix = Matrix()
    matrix.postTranslate(-bounds.left, -bounds.top)
    path.transform(matrix)

    bounds.offset(-bounds.left, -bounds.top)
    val scaleX = targetSize / bounds.width()
    val scaleY = targetSize / bounds.height()
    val scale = minOf(scaleX, scaleY)

    val scaleMatrix = Matrix()
    scaleMatrix.postScale(scale, scale)
    path.transform(scaleMatrix)

    return path
}