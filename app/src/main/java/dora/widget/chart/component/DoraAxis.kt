package dora.widget.chart.component

import android.graphics.Color
import dora.widget.chart.DoraChartData
import dora.widget.chart.DoraChartDataSet
import dora.widget.chart.DoraChartEntry

abstract class DoraAxis<E : DoraChartEntry, S : DoraChartDataSet<out E>>(var axisLineColor: Int = Color.GRAY,
                                                                         var axisLineWidth: Float = 5f,
                                                                         var drawGridLine: Boolean = true) : DoraChartComponent() {
    fun calcMaxValue(data: DoraChartData<E, S>): Float {
        return data.calcMaxEntryValue()
    }

    fun calcMinValue(data: DoraChartData<E, S>): Float {
        return data.calcMinEntryValue()
    }
}