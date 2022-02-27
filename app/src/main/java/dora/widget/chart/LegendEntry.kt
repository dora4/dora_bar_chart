package dora.widget.chart

import android.graphics.Color
import dora.widget.chart.component.DoraChartComponent

class LegendEntry(val label: String = "图例和注记", val labelColor: Int = Color.GRAY,
                  val iconColor: Int = Color.GRAY) : DoraChartComponent()