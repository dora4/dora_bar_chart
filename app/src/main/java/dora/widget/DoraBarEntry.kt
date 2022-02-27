package dora.widget

import dora.widget.chart.DoraChartEntry

class DoraBarEntry(value: Float = 0f, showValue: Boolean = false, val isFillBar: Boolean = true) : DoraChartEntry(value, showValue) {
    constructor(value: Float = 0f, isFillBar: Boolean = true) : this(value, false, isFillBar)
}