package com.erdemkaya.scribbledash.game.presentation.components.utils

import android.content.Context
import android.graphics.Path
import android.graphics.RectF
import androidx.annotation.DrawableRes
import androidx.core.graphics.PathParser
import com.erdemkaya.scribbledash.game.presentation.models.PathModel
import org.xmlpull.v1.XmlPullParser

class SvgParser(private val context: Context) {

    fun parseVectorDrawable(@DrawableRes resId: Int): PathModel {
        val parser = context.resources.getXml(resId)
        val path = Path()

        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.name == "path") {
                val pathData = parser.getAttributeValue(
                    "http://schemas.android.com/apk/res/android",
                    "pathData"
                )
                if (!pathData.isNullOrEmpty()) {
                    val parsedPath =
                        PathParser.createPathFromPathData(pathData)
                    path.addPath(parsedPath)
                }
            }
            eventType = parser.next()
        }
        val bounds = RectF()
        path.computeBounds(bounds, true)
        return PathModel(path, bounds)
    }
}