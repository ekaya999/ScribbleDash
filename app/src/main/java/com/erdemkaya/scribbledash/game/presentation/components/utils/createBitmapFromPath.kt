package com.erdemkaya.scribbledash.game.presentation.components.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.core.graphics.createBitmap

fun createBitmapFromPath(path: Path, strokeWidth: Float, size: Int): Bitmap {
    val bitmap = createBitmap(size, size)
    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.TRANSPARENT)

    val paint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        style = Paint.Style.STROKE
        this.strokeWidth = strokeWidth
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    canvas.drawPath(path, paint)
    return bitmap
}