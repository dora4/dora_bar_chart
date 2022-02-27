package dora.widget.chart.renderer

import android.graphics.Canvas
import android.graphics.Paint
import dora.widget.chart.DoraChartDataSet
import dora.widget.chart.DoraChartEntry
import dora.widget.chart.animation.DoraChartAnimator
import dora.widget.chart.component.DoraXAxis
import dora.widget.chart.component.DoraYAxis

abstract class DoraAxisRenderer<E : DoraChartEntry, S : DoraChartDataSet<E>>(animator: DoraChartAnimator)
    : Renderer(animator) {

    protected val axisPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    abstract fun drawXAxis(xAxis: DoraXAxis<E, S>, canvas: Canvas)
    abstract fun drawYAxis(yAxis: DoraYAxis<E, S>, canvas: Canvas)
    abstract fun drawXAxisScaleValue(xAxis: DoraXAxis<E, S>, canvas: Canvas)
    abstract fun drawYAxisScaleValue(yAxis: DoraYAxis<E, S>, canvas: Canvas)
    abstract fun drawGridLine(xAxis: DoraXAxis<E, S>, canvas: Canvas)
    abstract fun drawGridLine(yAxis: DoraYAxis<E, S>, canvas: Canvas)

    override fun initPaints() {
        axisPaint.style = Paint.Style.STROKE
    }


    init {
        initPaints()
    }
}