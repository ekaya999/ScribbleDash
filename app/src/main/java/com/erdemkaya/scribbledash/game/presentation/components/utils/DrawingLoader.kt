package com.erdemkaya.scribbledash.game.presentation.components.utils

import com.erdemkaya.scribbledash.R
import com.erdemkaya.scribbledash.game.presentation.models.PathModel

class DrawingLoader(private val svgParser: SvgParser) {

    fun loadAllDrawings(): List<PathModel> {
        val resIds = listOf(
            R.drawable.alien,
            R.drawable.bicycle,
            R.drawable.boat,
            R.drawable.book,
            R.drawable.butterfly,
            R.drawable.camera,
            R.drawable.car,
            R.drawable.castle,
            R.drawable.cat,
            R.drawable.clock,
            R.drawable.cup,
            R.drawable.dog,
            R.drawable.envelope,
            R.drawable.eye,
            R.drawable.fish,
            R.drawable.flower,
            R.drawable.football_field,
            R.drawable.frog,
            R.drawable.glasses,
            R.drawable.heart,
            R.drawable.helicotper,
            R.drawable.hotairballoon,
            R.drawable.house,
            R.drawable.moon,
            R.drawable.mountains,
            R.drawable.robot,
            R.drawable.rocket,
            R.drawable.smiley,
            R.drawable.snowflake,
            R.drawable.sofa,
            R.drawable.train,
            R.drawable.umbrella,
            R.drawable.whale,
        )
        return resIds.map { svgParser.parseVectorDrawable(it) }
    }
}