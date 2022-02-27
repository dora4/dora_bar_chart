package dora.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import dora.widget.chart.DoraAxisChartView
import dora.widget.chart.animation.DoraChartAnimator
import dora.widget.chart.animation.DoraChartAnimator.Companion.ANIMATOR_TIME
import dora.widget.chart.renderer.DoraDataRenderer
import kotlin.math.abs

class DoraBarChart @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0)
    : DoraAxisChartView<DoraBarEntry, DoraBarDataset, DoraBarData>(context, attrs, defStyleAttr) {

    override fun createDataRenderer(): DoraDataRenderer {

        var phaseY = 1f

        return object : DoraDataRenderer(DoraChartAnimator {
            animator -> animator.addUpdateListener {
            phaseY = it.animatedValue as Float
            postInvalidate()
        }
        }) {

            override fun init() {
                super.init()
                animator.animateY(ANIMATOR_TIME)
            }

            override fun initPaints() {
                super.initPaints()
                shapePaint.strokeCap = Paint.Cap.SQUARE
                shapePaint.strokeJoin = Paint.Join.ROUND
            }

            override fun drawShape(canvas: Canvas) {
                val maxHeight = height - yAxisPlaceholder * 2
                val maxValue = data.calcMaxEntryValue()
                val minValue = data.calcMinEntryValue()
                when (calcXAxisType()) {
                    XAxisType.BOTTOM -> {
                        val scale = maxValue / maxHeight
                        for (dataSet in data.dataSets) {
                            shapePaint.strokeWidth = dataSet.lineWidth
                            shapePaint.color = dataSet.barColor

                            var size = getXAxisGridCount()
                            if (size == 0) {
                                return
                            }
                            if (size > maxVisibleCount) {
                                size = maxVisibleCount
                            }
                            val gridWidth = ((width - xAxisPlaceholder * 2) / size)
                            for ((index, entry) in dataSet.entries.subList(0, size).withIndex()) {
                                val curX = xAxisPlaceholder + gridWidth * index + gridWidth / 2
                                val curY = yAxisPlaceholder + maxHeight - (entry.value / scale) * phaseY
                                canvas.drawRect(RectF(curX - dataSet.barWidth / 2, curY, curX + dataSet.barWidth / 2, yAxisPlaceholder + maxHeight), shapePaint)
                            }
                        }
                    }
                    XAxisType.TOP -> {
                        val scale = abs(minValue) / maxHeight
                        for (dataSet in data.dataSets) {
                            shapePaint.strokeWidth = dataSet.lineWidth
                            shapePaint.color = dataSet.signedBarColor

                            var size = getXAxisGridCount()
                            if (size == 0) {
                                return
                            }
                            if (size > maxVisibleCount) {
                                size = maxVisibleCount
                            }
                            val gridWidth = ((width - xAxisPlaceholder * 2) / size)
                            for ((index, entry) in dataSet.entries.subList(0, size).withIndex()) {
                                val curX = xAxisPlaceholder + gridWidth * index + gridWidth / 2
                                val curY = yAxisPlaceholder + (abs(entry.value) / scale) * phaseY
                                canvas.drawRect(RectF(curX - dataSet.barWidth / 2, yAxisPlaceholder,
                                        curX + dataSet.barWidth / 2, curY), shapePaint)
                            }
                        }
                    }
                    XAxisType.INSIDE -> {
                        val valueScope = abs(maxValue).coerceAtLeast(abs(minValue))
                        val halfMaxHeight = maxHeight / 2
                        // 每一个刻度能表示的值的大小
                        val scale = valueScope / halfMaxHeight
                        for (dataSet in data.dataSets) {
                            shapePaint.strokeWidth = dataSet.lineWidth

                            var size = getXAxisGridCount()
                            if (size == 0) {
                                return
                            }
                            if (size > maxVisibleCount) {
                                size = maxVisibleCount
                            }
                            val gridWidth = ((width - xAxisPlaceholder * 2) / size)
                            for ((index, entry) in dataSet.entries.subList(0, size).withIndex()) {
                                shapePaint.style = if (entry.isFillBar) Paint.Style.FILL_AND_STROKE else Paint.Style.STROKE
                                val curX = xAxisPlaceholder + gridWidth * index + gridWidth / 2
                                var curY: Float
                                if (entry.value < 0) {
                                    shapePaint.color = dataSet.signedBarColor
                                    curY = yAxisPlaceholder + (abs(entry.value) / scale) * phaseY
                                    canvas.drawRect(RectF(curX - dataSet.barWidth / 2,
                                            yAxisPlaceholder + halfMaxHeight, curX + dataSet.barWidth / 2,
                                            halfMaxHeight + curY), shapePaint)
                                } else {
                                    shapePaint.color = dataSet.barColor
                                    curY = yAxisPlaceholder + halfMaxHeight - (entry.value / scale) * phaseY
                                    canvas.drawRect(RectF(curX - dataSet.barWidth / 2,
                                            curY, curX + dataSet.barWidth / 2, yAxisPlaceholder + halfMaxHeight), shapePaint)
                                }
                            }
                        }
                    }
                }
            }

            override fun drawLabel(canvas: Canvas) {
                val maxHeight = height - yAxisPlaceholder * 2
                val maxValue = data.calcMaxEntryValue()
                val minValue = data.calcMinEntryValue()
                when (calcXAxisType()) {
                    XAxisType.BOTTOM -> {
                        val scale = maxValue / maxHeight
                        for (dataSet in data.dataSets) {
                            labelPaint.textSize = dataSet.valueTextSize
                            labelPaint.color = dataSet.valueTextColor

                            var size = getXAxisGridCount()
                            if (size == 0) {
                                return
                            }
                            if (size > maxVisibleCount) {
                                size = maxVisibleCount
                            }
                            val gridWidth = ((width - xAxisPlaceholder * 2) / size)
                            val labelBarGap = dataSet.labelBarGap
                            for ((index, entry) in dataSet.entries.subList(0, size).withIndex()) {
                                if (entry.showValue) {
                                    val textBounds = Rect()
                                    labelPaint.getTextBounds(valueFormatter.formatValue(entry.value), 0,
                                            valueFormatter.formatValue(entry.value).length, textBounds)
                                    val textWidth = textBounds.width()
                                    val curX = xAxisPlaceholder + gridWidth * index + gridWidth / 2 - textWidth / 2
                                    var curY = yAxisPlaceholder + maxHeight - (entry.value / scale) * phaseY - labelBarGap
                                    canvas.drawText(valueFormatter.formatValue(entry.value), curX, curY, labelPaint)
                                }
                            }
                        }
                    }
                    XAxisType.TOP -> {
                        val scale = abs(minValue) / maxHeight
                        for (dataSet in data.dataSets) {
                            labelPaint.textSize = dataSet.valueTextSize
                            labelPaint.color = dataSet.valueTextColor

                            var size = getXAxisGridCount()
                            if (size == 0) {
                                return
                            }
                            if (size > maxVisibleCount) {
                                size = maxVisibleCount
                            }
                            val gridWidth = ((width - xAxisPlaceholder * 2) / size)
                            val labelBarGap = dataSet.labelBarGap
                            for ((index, entry) in dataSet.entries.subList(0, size).withIndex()) {
                                if (entry.showValue) {
                                    val textBounds = Rect()
                                    labelPaint.getTextBounds(valueFormatter.formatValue(entry.value), 0,
                                            valueFormatter.formatValue(entry.value).length, textBounds)
                                    val textWidth = textBounds.width()
                                    val textHeight = textBounds.height()
                                    val curX = xAxisPlaceholder + gridWidth * index + gridWidth / 2 - textWidth / 2
                                    var curY = yAxisPlaceholder + (abs(entry.value) / scale) * phaseY + labelBarGap + textHeight
                                    canvas.drawText(valueFormatter.formatValue(entry.value), curX, curY, labelPaint)
                                }
                            }
                        }
                    }
                    XAxisType.INSIDE -> {
                        val valueScope = abs(maxValue).coerceAtLeast(abs(minValue))
                        val halfMaxHeight = maxHeight / 2
                        // 每一个刻度能表示的值的大小
                        val scale = valueScope / halfMaxHeight
                        for (dataSet in data.dataSets) {
                            labelPaint.textSize = dataSet.valueTextSize
                            labelPaint.color = dataSet.valueTextColor

                            var size = getXAxisGridCount()
                            if (size == 0) {
                                return
                            }
                            if (size > maxVisibleCount) {
                                size = maxVisibleCount
                            }
                            val gridWidth = ((width - xAxisPlaceholder * 2) / size)
                            val labelBarGap = dataSet.labelBarGap
                            for ((index, entry) in dataSet.entries.subList(0, size).withIndex()) {
                                if (entry.showValue) {
                                    val textBounds = Rect()
                                    labelPaint.getTextBounds(valueFormatter.formatValue(entry.value), 0,
                                            valueFormatter.formatValue(entry.value).length, textBounds)
                                    val textWidth = textBounds.width()
                                    val textHeight = textBounds.height()
                                    val curX = xAxisPlaceholder + gridWidth * index + gridWidth / 2 - textWidth / 2
                                    var curY: Float
                                    if (entry.value < 0) {
                                        curY = yAxisPlaceholder + halfMaxHeight + (abs(entry.value) / scale) * phaseY + labelBarGap + textHeight
                                        canvas.drawText(valueFormatter.formatValue(entry.value), curX, curY, labelPaint)
                                    } else {
                                        curY = yAxisPlaceholder + halfMaxHeight - (entry.value / scale) * phaseY - labelBarGap
                                        canvas.drawText(valueFormatter.formatValue(entry.value), curX, curY, labelPaint)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getXAxisGridCount(): Int {
        return data.calcDatasetGridCount()
    }
}