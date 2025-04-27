package com.erdemkaya.scribbledash.game.presentation.components

import android.content.Context
import android.graphics.Path
import android.graphics.RectF
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import org.w3c.dom.Element
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

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
                        androidx.core.graphics.PathParser.createPathFromPathData(pathData)
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