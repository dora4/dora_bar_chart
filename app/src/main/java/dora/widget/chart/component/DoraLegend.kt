package dora.widget.chart.component

import dora.widget.chart.LegendEntry

class DoraLegend(var iconLabelGap: Float,
                 var legendItemGap: Float,
                 var iconSize: Float,
                 var entries: MutableList<LegendEntry>,
                 var type: LegendType
) : DoraChartComponent() {

    constructor() : this(10f, 20f, 40f, arrayListOf(), LegendType.SQUARE)

    enum class LegendType {
        SQUARE,
        CIRCLE
    }
}