package dora.widget.chart.component

import android.graphics.Color
import android.graphics.Typeface

abstract class DoraChartComponent(
    var xAxisOffset: Float = 0f,
    var yAxisOffset: Float = 0f,
    var textSize: Float = 20f,
    var textColor: Int = Color.GRAY,
    var textTypeface: Typeface = Typeface.DEFAULT)