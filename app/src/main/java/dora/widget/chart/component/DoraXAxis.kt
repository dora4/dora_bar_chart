package dora.widget.chart.component

import dora.widget.chart.DoraChartDataSet
import dora.widget.chart.DoraChartEntry

data class DoraXAxis<E : DoraChartEntry, S : DoraChartDataSet<out E>>(val isShow: Boolean = true) : DoraAxis<E, S>()