package com.erdemkaya.scribbledash.game.presentation.components

import com.erdemkaya.scribbledash.R

class DrawingLoader(private val svgParser: SvgParser) {

    fun loadAllDrawings(): List<PathModel> {
        val resIds = listOf(
            R.xml.alien,
            R.xml.bicycle,
            R.xml.boat,
            R.xml.book,
            R.xml.butterfly,
            R.xml.camera
        )
        return resIds.map { svgParser.parseSvg(it) }
    }
}