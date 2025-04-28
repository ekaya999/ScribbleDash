package com.erdemkaya.scribbledash.game.presentation.components

import android.graphics.Color
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import androidx.core.graphics.get

fun comparePaths(
    userPath: Path,
    examplePath: Path,
    targetSize: Int = 1024,
    userStrokeWidth: Float = 5f,
    exampleStrokeMultiplier: Float = 15f,
    fuzzyRadius: Int = 4
): Float {
    val userBounds = RectF().apply { userPath.computeBounds(this, true) }
    val exampleBounds = RectF().apply { examplePath.computeBounds(this, true) }

    val normalizedUser = normalizePathForComparison(
        userPath, userBounds,
        userStrokeWidth, exampleStrokeMultiplier * userStrokeWidth,
        isUser = true, targetSize = targetSize.toFloat()
    )
    val normalizedExample = normalizePathForComparison(
        examplePath, exampleBounds,
        userStrokeWidth, exampleStrokeMultiplier * userStrokeWidth,
        isUser = false, targetSize = targetSize.toFloat()
    )

    val userBitmap = createBitmapFromPath(normalizedUser, userStrokeWidth, targetSize)
    val exampleBitmap = createBitmapFromPath(
        normalizedExample,
        exampleStrokeMultiplier * userStrokeWidth,
        targetSize
    )

    var matchingPixels = 0
    var visibleUserPixels = 0

    for (y in 0 until targetSize) {
        for (x in 0 until targetSize) {
            val userPixel = userBitmap[x, y]
            if (Color.alpha(userPixel) > 0) {
                visibleUserPixels++

                var matchFound = false
                loop@ for (dy in -fuzzyRadius..fuzzyRadius) {
                    for (dx in -fuzzyRadius..fuzzyRadius) {
                        val nx = x + dx
                        val ny = y + dy
                        if (nx in 0 until targetSize && ny in 0 until targetSize) {
                            val examplePixel = exampleBitmap[nx, ny]
                            if (Color.alpha(examplePixel) > 0) {
                                matchFound = true
                                break@loop
                            }
                        }
                    }
                }
                if (matchFound) matchingPixels++
            }
        }
    }

    if (visibleUserPixels == 0) return 0f

    val coveragePercentage = (matchingPixels.toFloat() / visibleUserPixels) * 100f

    val userLength = calculatePathLength(userPath)
    val exampleLength = calculatePathLength(examplePath)

    val lengthRatio = userLength / exampleLength
    val missingLengthPenalty = if (lengthRatio < 0.7f) {
        100f - (lengthRatio * 100f)
    } else {
        0f
    }

    val finalScore = coveragePercentage - missingLengthPenalty
    return finalScore.coerceIn(0f, 100f)
}

fun calculatePathLength(path: Path): Float {
    val pm = PathMeasure(path, false)
    var length = 0f
    do {
        length += pm.length
    } while (pm.nextContour())
    return length
}