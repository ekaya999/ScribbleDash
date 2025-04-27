package com.erdemkaya.scribbledash.game.presentation.components

import android.graphics.Path
import android.graphics.PathMeasure

fun calculatePathLength(path: Path): Float {
    val pm = PathMeasure(path, false)
    var length = 0f
    do {
        length += pm.length
    } while (pm.nextContour())
    return length
}