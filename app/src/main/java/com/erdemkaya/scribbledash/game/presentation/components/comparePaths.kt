package com.erdemkaya.scribbledash.game.presentation.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import android.graphics.Typeface
import androidx.core.graphics.createBitmap
import androidx.core.graphics.get
import androidx.core.graphics.set
import kotlin.math.hypot

fun comparePaths(
    context: Context,
    userPath: Path,
    examplePath: Path,
    targetSize: Int = 1024,
    userStrokeWidth: Float = 5f,
    exampleStrokeMultiplier: Float = 15f,
    fuzzyRadius: Int = 3
): Float {
    val userBounds = RectF().apply { userPath.computeBounds(this, true) }
    val exampleBounds = RectF().apply { examplePath.computeBounds(this, true) }

    val normalizedUser = normalizePathForComparison(
        userPath,
        userBounds,
        userStrokeWidth,
        exampleStrokeMultiplier * userStrokeWidth,
        isUser = true,
        targetSize = targetSize.toFloat()
    )
    val normalizedExample = normalizePathForComparison(
        examplePath,
        exampleBounds,
        userStrokeWidth,
        exampleStrokeMultiplier * userStrokeWidth,
        isUser = false,
        targetSize = targetSize.toFloat()
    )

    val userBitmap =
        createBitmapFromPath(normalizedUser, strokeWidth = userStrokeWidth, size = targetSize)
    val exampleBitmap = createBitmapFromPath(
        normalizedExample,
        strokeWidth = exampleStrokeMultiplier * userStrokeWidth,
        size = targetSize
    )

    saveBitmapToStorage(context, userBitmap, "userPathBitmap")
    saveBitmapToStorage(context, exampleBitmap, "examplePathBitmap")

    val comparisonBitmap = createBitmap(targetSize, targetSize)
    var matchingPixels = 0
    var visibleUserPixels = 0

    for (y in 0 until targetSize) {
        for (x in 0 until targetSize) {
            val userPixel = userBitmap[x, y]
            val examplePixel = exampleBitmap[x, y]

            val userVisible = Color.alpha(userPixel) > 0
            val exampleVisible = Color.alpha(examplePixel) > 0

            var matchFound = false

            if (userVisible) {
                visibleUserPixels++

                loop@ for (dy in -fuzzyRadius..fuzzyRadius) {
                    for (dx in -fuzzyRadius..fuzzyRadius) {
                        val nx = x + dx
                        val ny = y + dy
                        if (nx in 0 until targetSize && ny in 0 until targetSize) {
                            if (Color.alpha(exampleBitmap[nx, ny]) > 0) {
                                matchFound = true
                                break@loop
                            }
                        }
                    }
                }

                if (matchFound) {
                    matchingPixels++
                    comparisonBitmap[x, y] = Color.GREEN
                } else {
                    comparisonBitmap[x, y] = Color.RED
                }
            } else if (exampleVisible) {
                comparisonBitmap[x, y] = Color.BLACK
            }
        }
    }

    saveBitmapToStorage(context, comparisonBitmap, "comparisonBitmap")

    if (visibleUserPixels == 0) return 0f

    val coveragePercentage = (matchingPixels.toFloat() / visibleUserPixels) * 100f
    val userLength = calculatePathLength(userPath)
    val exampleLength = calculatePathLength(examplePath)
    val lengthRatio = userLength / exampleLength

    val lengthPenalty = if (lengthRatio < 0.7f) {
        (0.7f - lengthRatio)
    } else 0f

    val rawScore = coveragePercentage - lengthPenalty
    val canvas = Canvas(comparisonBitmap)
    val paint = Paint().apply {
        color = Color.BLACK
        textSize = 48f
        isAntiAlias = true
        style = Paint.Style.FILL
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    val scoreText = "Score: ${"%.1f".format(rawScore.coerceIn(0f, 100f))}%"
    canvas.drawText(scoreText, 20f, 60f, paint)

//    val adjustedScore = when {
//        rawScore >= 80f -> 100f
//        rawScore >= 60f -> 80f + (rawScore - 60f)
//        else -> rawScore
//    }

    if (isPathTooSimple(userPath, userBounds)) {
        return 0f //
    }

    return rawScore.coerceIn(0f, 100f)
}


fun calculatePathLength(path: Path): Float {
    val pm = PathMeasure(path, false)
    var length = 0f
    do {
        length += pm.length
    } while (pm.nextContour())
    return length
}

fun isPathTooSimple(path: Path, bounds: RectF, threshold: Float = 0.98f): Boolean {
    val diagonalLength = hypot(bounds.width(), bounds.height())
    val pathLength = calculatePathLength(path)
    val straightnessRatio = diagonalLength / pathLength
    return straightnessRatio > threshold
}