package dora.widget

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import dora.widget.chart.component.DoraLegend

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val macdChart = findViewById<DoraBarChart>(R.id.macdChart)
        val legend = DoraLegend()
        legend.type = DoraLegend.LegendType.CIRCLE
        legend.xAxisOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25f,
                resources.displayMetrics)
        val dataSet = DoraBarDataset()
        dataSet.valueTextColor = Color.parseColor("#FFFFFF")
        dataSet.addEntry(DoraBarEntry(3f, true, true))
        dataSet.addEntry(DoraBarEntry(2f, true, true))
        dataSet.addEntry(DoraBarEntry(1f, true, true))
        dataSet.addEntry(DoraBarEntry(2f, true, false))
        dataSet.addEntry(DoraBarEntry(3f, true, false))
        dataSet.addEntry(DoraBarEntry(1f, true, true))
        dataSet.addEntry(DoraBarEntry(-1f, true, true))
        dataSet.addEntry(DoraBarEntry(-2f, true, true))
        dataSet.addEntry(DoraBarEntry(-3f, true, true))
        dataSet.addEntry(DoraBarEntry(-4f,   true, true))
        dataSet.addEntry(DoraBarEntry(-3f, true, false))
        dataSet.addEntry(DoraBarEntry(-2f,  true, false))
        val data = DoraBarData(dataSet)
        macdChart.xAxis.axisLineColor = Color.GRAY
        macdChart.xAxis.axisLineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1f, resources.displayMetrics)
        macdChart.yAxis.axisLineColor = Color.GRAY
        macdChart.yAxis.axisLineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1f, resources.displayMetrics)
        macdChart.yAxis.drawGridLine = false
        macdChart.yAxis.sideScaleValueCount = 4
        macdChart.setData(data)
    }
}