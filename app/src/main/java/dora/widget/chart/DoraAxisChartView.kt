package dora.widget.chart

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import dora.widget.chart.animation.DoraChartAnimator
import dora.widget.chart.component.DoraXAxis
import dora.widget.chart.component.DoraYAxis
import dora.widget.chart.formatter.DefaultValueFormatter
import dora.widget.chart.renderer.DoraAxisRenderer
import kotlin.math.abs

abstract class DoraAxisChartView<E : DoraChartEntry, S : DoraChartDataSet<E>,
        T : DoraChartData<E, S>>
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                          defStyleAttr: Int = 0) : DoraChartView<E, S, T>(context,
        attrs, defStyleAttr) {

    var xAxis: DoraXAxis<E, S> = DoraXAxis()
    var yAxis: DoraYAxis<E, S> = DoraYAxis()
    var xAxisPlaceholder =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
    20f, resources.displayMetrics)
    var yAxisPlaceholder =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            20f, resources.displayMetrics)
    var maxVisibleCount: Int = 100
    lateinit var axisRenderer: DoraAxisRenderer<E, S>

    override fun init() {
        super.init()
        axisRenderer = object : DoraAxisRenderer<E, S>(DoraChartAnimator {}) {
            override fun drawXAxis(xAxis: DoraXAxis<E, S>, canvas: Canvas) {
                axisPaint.color = xAxis.axisLineColor
                axisPaint.strokeWidth = xAxis.axisLineWidth
                if (xAxis.isShow) {
                    when (calcXAxisType()) {
                        XAxisType.TOP -> {
                            canvas.drawLine(xAxisPlaceholder, yAxisPlaceholder, width - xAxisPlaceholder,
                                    yAxisPlaceholder, axisPaint)
                        }
                        XAxisType.INSIDE -> {
                            canvas.drawLine(xAxisPlaceholder, height / 2f, width - xAxisPlaceholder,
                                    height / 2f, axisPaint)
                        }
                        XAxisType.BOTTOM -> {
                            canvas.drawLine(xAxisPlaceholder, height - yAxisPlaceholder, width - xAxisPlaceholder,
                                    height - yAxisPlaceholder, axisPaint)
                        }
                    }
                }
            }

            override fun drawYAxis(yAxis: DoraYAxis<E, S>, canvas: Canvas) {
                axisPaint.color = yAxis.axisLineColor
                axisPaint.strokeWidth = yAxis.axisLineWidth
                when (calcXAxisType()) {
                    XAxisType.TOP -> {
                        if (yAxis.isShowLeft) {
                            canvas.drawLine(xAxisPlaceholder, yAxisPlaceholder,
                                    xAxisPlaceholder, height.toFloat() - yAxisPlaceholder, axisPaint)
                        }
                        if (yAxis.isShowRight) {
                            canvas.drawLine(width - xAxisPlaceholder, yAxisPlaceholder,
                                    width - xAxisPlaceholder, height.toFloat() - yAxisPlaceholder, axisPaint)
                        }
                    }
                    XAxisType.BOTTOM -> {
                        if (yAxis.isShowLeft) {
                            canvas.drawLine(xAxisPlaceholder, yAxisPlaceholder,
                                    xAxisPlaceholder, height.toFloat() - yAxisPlaceholder, axisPaint)
                        }
                        if (yAxis.isShowRight) {
                            canvas.drawLine(width - xAxisPlaceholder, yAxisPlaceholder,
                                    width - xAxisPlaceholder, height.toFloat() - yAxisPlaceholder, axisPaint)
                        }
                    }
                    XAxisType.INSIDE -> {
                        if (yAxis.isShowLeft) {
                            canvas.drawLine(xAxisPlaceholder, yAxisPlaceholder,
                                    xAxisPlaceholder, height - yAxisPlaceholder, axisPaint)
                        }
                        if (yAxis.isShowRight) {
                            canvas.drawLine(width - xAxisPlaceholder, yAxisPlaceholder,
                                    width - xAxisPlaceholder, height - yAxisPlaceholder, axisPaint)
                        }
                    }
                }
            }

            override fun drawGridLine(xAxis: DoraXAxis<E, S>, canvas: Canvas) {
                var size = getXAxisGridCount()
                if (size == 0) {
                    return
                }
                if (size > maxVisibleCount) {
                    size = maxVisibleCount
                }
                val gridWidth = ((width - xAxisPlaceholder * 2) / size)
                for (dataSet in data.dataSets) {
                    for ((index, _) in dataSet.entries.subList(0, size).withIndex()) {
                        var curX = xAxisPlaceholder + gridWidth * (index + 1)
                        if (yAxis.drawGridLine && index != dataSet.entries.size - 1) {
                            drawGridLine(curX, canvas)
                        }
                    }
                }
            }

            override fun drawGridLine(yAxis: DoraYAxis<E, S>, canvas: Canvas) {
            }

            private fun drawGridLine(x: Float, canvas: Canvas) {
                val maxHeight = height - yAxisPlaceholder * 2
                when (calcXAxisType()) {
                    XAxisType.INSIDE -> {
                        val lineLength = 20f
                        val spaceLength = 20f
                        val gridLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
                        gridLinePaint.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                1f, resources.displayMetrics)
                        gridLinePaint.color = Color.LTGRAY
                        gridLinePaint.pathEffect = DashPathEffect(floatArrayOf(
                                lineLength, spaceLength
                        ), 30f)
                        canvas.drawLine(x, yAxisPlaceholder, x, yAxisPlaceholder + maxHeight, gridLinePaint)
                    }
                    XAxisType.TOP -> {
                        val lineLength = 20f
                        val spaceLength = 20f
                        val gridLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
                        gridLinePaint.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                1f, resources.displayMetrics)
                        gridLinePaint.color = Color.LTGRAY
                        gridLinePaint.pathEffect = DashPathEffect(floatArrayOf(
                                lineLength, spaceLength
                        ), 30f)
                        canvas.drawLine(x,  yAxisPlaceholder, x, yAxisPlaceholder + maxHeight, gridLinePaint)
                    }
                    XAxisType.BOTTOM -> {
                        val lineLength = 20f
                        val spaceLength = 20f
                        val gridLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
                        gridLinePaint.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                1f, resources.displayMetrics)
                        gridLinePaint.color = Color.LTGRAY
                        gridLinePaint.pathEffect = DashPathEffect(floatArrayOf(
                                lineLength, spaceLength
                        ), 30f)
                        canvas.drawLine(x, yAxisPlaceholder, x, yAxisPlaceholder + maxHeight, gridLinePaint)
                    }
                }
            }

            override fun drawXAxisScaleValue(xAxis: DoraXAxis<E, S>, canvas: Canvas) {
            }

            override fun drawYAxisScaleValue(yAxis: DoraYAxis<E, S>, canvas: Canvas) {
                val scalePaint = Paint(Paint(Paint.ANTI_ALIAS_FLAG))
                scalePaint.textSize = 30f
                scalePaint.color = Color.WHITE
                val labelAxisGap = 15
                val maxHeight = height - yAxisPlaceholder * 2
                val maxValue = data.calcMaxEntryValue()
                val minValue = data.calcMinEntryValue()
                when (calcXAxisType()) {
                    XAxisType.BOTTOM -> {
                        val scale = maxValue / maxHeight
                        val valueScope = maxValue
                        // 每一个刻度的值的范围
                        val scaleValue = valueScope / scale
                        var count = yAxis.sideScaleValueCount + 1
                        for (i in 0 .. count) {
                            val text = (if (yAxis.useFormattedScaleValue) valueFormatter else DefaultValueFormatter()).formatValue(valueScope
                                    - valueScope * i / yAxis.sideScaleValueCount)
                            val textBounds = Rect()
                            scalePaint.getTextBounds(text, 0, text.length, textBounds)
                            val textWidth = textBounds.width()
                            val textHeight = textBounds.height()
                            if (yAxis.isShowLeft) {
                                if (i == count) {
                                    canvas.drawText((if (yAxis.useFormattedScaleValue) valueFormatter else DefaultValueFormatter()).formatValue(0f), xAxisPlaceholder - textWidth - labelAxisGap, yAxisPlaceholder + maxHeight / (count - 1) * i + textHeight / 2, scalePaint)
                                } else {
                                    canvas.drawText(text, xAxisPlaceholder - textWidth - labelAxisGap, yAxisPlaceholder + maxHeight / (count - 1) * i + textHeight / 2, scalePaint)
                                }
                            }
                            if (yAxis.isShowRight) {
                                if (i == count) {
                                    canvas.drawText((if (yAxis.useFormattedScaleValue) valueFormatter else DefaultValueFormatter()).formatValue(0f), width - xAxisPlaceholder + labelAxisGap, yAxisPlaceholder + maxHeight / (count - 1) * i + textHeight / 2, scalePaint)
                                } else {
                                    canvas.drawText(text, width - xAxisPlaceholder + labelAxisGap, yAxisPlaceholder + maxHeight / (count - 1) * i + textHeight / 2, scalePaint)
                                }
                            }
                        }
                    }
                    XAxisType.TOP -> {
                        val valueScope = abs(minValue)
                        var count = yAxis.sideScaleValueCount + 1
                        for (i in 0 .. count) {
                            val text = (if (yAxis.useFormattedScaleValue) valueFormatter else DefaultValueFormatter()).formatValue( -valueScope
                                    / yAxis.sideScaleValueCount * i)
                            val textBounds = Rect()
                            scalePaint.getTextBounds(text, 0, text.length, textBounds)
                            val textWidth = textBounds.width()
                            val textHeight = textBounds.height()
                            if (yAxis.isShowLeft) {
                                if (i == 0) {
                                    canvas.drawText((if (yAxis.useFormattedScaleValue) valueFormatter else DefaultValueFormatter()).formatValue(0f),
                                            xAxisPlaceholder - textWidth - labelAxisGap, yAxisPlaceholder + maxHeight / (count - 1) * i + textHeight / 2, scalePaint)
                                } else {
                                    canvas.drawText(text, xAxisPlaceholder - textWidth - labelAxisGap, yAxisPlaceholder + maxHeight / (count - 1) * i + textHeight / 2, scalePaint)
                                }
                            }
                            if (yAxis.isShowRight) {
                                if (i == 0) {
                                    canvas.drawText((if (yAxis.useFormattedScaleValue) valueFormatter else DefaultValueFormatter()).formatValue(0f), width - xAxisPlaceholder + labelAxisGap, yAxisPlaceholder + maxHeight / (count - 1) * i + textHeight / 2, scalePaint)
                                } else {
                                    canvas.drawText(text, width - xAxisPlaceholder + labelAxisGap, yAxisPlaceholder + maxHeight / (count - 1) * i + textHeight / 2, scalePaint)
                                }
                            }
                        }
                    }
                    XAxisType.INSIDE -> {
                        val valueScope = abs(maxValue).coerceAtLeast(abs(minValue))
                        var count = yAxis.sideScaleValueCount * 2 + 1
                        for (i in 0 until count) {
                            val text =
                                    if (i < i / 2f) { // 正数区域
                                        (if (yAxis.useFormattedScaleValue) valueFormatter else DefaultValueFormatter()).formatValue(valueScope - valueScope * i)
                                    } else {    // 负数区域
                                        (if (yAxis.useFormattedScaleValue) valueFormatter else DefaultValueFormatter()).formatValue(valueScope - valueScope * i / yAxis.sideScaleValueCount)
                                    }
                            val textBounds = Rect()
                            scalePaint.getTextBounds(text, 0, text.length, textBounds)
                            val textWidth = textBounds.width()
                            val textHeight = textBounds.height()
                            if (yAxis.isShowLeft) {
                                canvas.drawText(text, xAxisPlaceholder - textWidth - labelAxisGap, yAxisPlaceholder + maxHeight / (count - 1) * i + textHeight / 2, scalePaint)
                            }
                            if (yAxis.isShowRight) {
                                canvas.drawText(text, width - xAxisPlaceholder + labelAxisGap, yAxisPlaceholder + maxHeight /   (count - 1) * i + textHeight / 2, scalePaint)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取x轴需要绘制多少网格。
     */
    abstract fun getXAxisGridCount() : Int

    /**
     * 计算数据得出x轴的位置。
     */
    fun calcXAxisType() : XAxisType {
        val maxValue = data.calcMaxEntryValue()
        val minValue = data.calcMinEntryValue()
        return when {
            minValue > 0 -> {
                XAxisType.BOTTOM
            }
            maxValue < 0 -> {
                XAxisType.TOP
            }
            else -> XAxisType.INSIDE
        }
    }

    enum class XAxisType {
        TOP,    // 顶部
        INSIDE, // 内嵌
        BOTTOM  // 底部
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        axisRenderer.drawXAxis(xAxis, canvas)
        axisRenderer.drawGridLine(xAxis, canvas)
        axisRenderer.drawXAxisScaleValue(xAxis, canvas)
        axisRenderer.drawYAxis(yAxis, canvas)
        axisRenderer.drawGridLine(yAxis, canvas)
        axisRenderer.drawYAxisScaleValue(yAxis, canvas)
    }
}