package com.erdemkaya.scribbledash.game.presentation.components

import android.content.Context
import android.graphics.Path
import androidx.annotation.RawRes
import org.w3c.dom.Element
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

class SvgParser(private val context: Context) {

    fun parseSvg(@RawRes resId: Int): PathModel {
        val inputStream: InputStream = context.resources.openRawResource(resId)
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = documentBuilder.parse(inputStream)
        val path = Path()

        val root = document.documentElement
        val nodes = root.getElementsByTagName("*")

        for (i in 0 until nodes.length) {
            val node = nodes.item(i)
            if (node is Element) {
                when (node.tagName) {
                    "path" -> {
                        val d = node.getAttribute("d")
                        if (d.isNotEmpty()) {
                            val parsedPath =
                                androidx.core.graphics.PathParser.createPathFromPathData(d)
                            path.addPath(parsedPath)
                        }
                    }

                    "line" -> {
                        val x1 = node.getAttribute("x1").toFloatOrNull() ?: 0f
                        val y1 = node.getAttribute("y1").toFloatOrNull() ?: 0f
                        val x2 = node.getAttribute("x2").toFloatOrNull() ?: 0f
                        val y2 = node.getAttribute("y2").toFloatOrNull() ?: 0f
                        path.moveTo(x1, y1)
                        path.lineTo(x2, y2)
                    }

                    "circle" -> {
                        val cx = node.getAttribute("cx").toFloatOrNull() ?: 0f
                        val cy = node.getAttribute("cy").toFloatOrNull() ?: 0f
                        val r = node.getAttribute("r").toFloatOrNull() ?: 0f
                        path.addCircle(cx, cy, r, Path.Direction.CW)
                    }

                    "ellipse" -> {
                        val cx = node.getAttribute("cx").toFloatOrNull() ?: 0f
                        val cy = node.getAttribute("cy").toFloatOrNull() ?: 0f
                        val rx = node.getAttribute("rx").toFloatOrNull() ?: 0f
                        val ry = node.getAttribute("ry").toFloatOrNull() ?: 0f
                        path.addOval(cx - rx, cy - ry, cx + rx, cy + ry, Path.Direction.CW)
                    }
                }
            }
        }

        return PathModel(path)
    }
}