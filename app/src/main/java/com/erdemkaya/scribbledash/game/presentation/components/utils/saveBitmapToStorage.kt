package com.erdemkaya.scribbledash.game.presentation.components.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore

fun saveBitmapToStorage(context: Context, bitmap: Bitmap, fileName: String): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.png")
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/PathDebug")
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }

    val uri =
        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        context.contentResolver.openOutputStream(it)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        context.contentResolver.update(it, contentValues, null, null)
    }

    return uri
}