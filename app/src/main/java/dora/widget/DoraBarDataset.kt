package dora.widget

import android.graphics.Color
import dora.widget.chart.DoraChartDataSet
import dora.widget.chart.LegendEntry

class DoraBarDataset(
                    entries: MutableList<DoraBarEntry> = arrayListOf(),
                     var lineWidth: Float = 4f,
                     var barWidth: Float = 60f,
                     var barColor: Int = Color.GREEN,
                     var signedBarColor: Int = Color.RED,
                     var label: String = "",
                     var labelColor: Int = Color.GRAY,
                     var valueTextSize: Float = 30f,
                     var valueTextColor: Int = Color.GRAY,
                     var labelBarGap: Float = 15f) : DoraChartDataSet<DoraBarEntry>(entries) {


    override fun calcLegend(): LegendEntry {
        return LegendEntry(label, labelColor, labelColor)
    }
}