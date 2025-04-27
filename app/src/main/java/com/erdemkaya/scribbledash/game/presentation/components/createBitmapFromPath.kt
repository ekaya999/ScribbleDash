package com.erdemkaya.scribbledash.game.presentation.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import androidx.core.graphics.createBitmap

fun createBitmapFromPath(
    path: Path,
    strokeWidth: Float,
    size: Int
): Bitmap {
    val bitmap = createBitmap(size, size)
    val canvas = Canvas(bitmap)

    val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = android.graphics.Color.BLACK
        this.strokeWidth = strokeWidth
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    canvas.drawPath(path, paint)

    return bitmap
}