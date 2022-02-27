package dora.widget.chart.renderer

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextPaint
import dora.widget.chart.animation.DoraChartAnimator
import dora.widget.chart.component.DoraLegend

class DoraLegendRenderer(animator: DoraChartAnimator) : Renderer(animator) {

    val labelPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    val iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val textBounds = Rect()

    fun drawLegend(legend: DoraLegend, canvas: Canvas) {
        labelPaint.textSize = legend.iconSize
        val fontMetrics = labelPaint.fontMetrics
        var centerY = 0f
        for (entry in legend.entries) {
            labelPaint.color = entry.labelColor
            iconPaint.color = entry.iconColor
            // 获取文字的高度
            labelPaint.getTextBounds(entry.label, 0, entry.label.length, textBounds)
            val textHeight = textBounds.height().toFloat()
            val itemHeight = if (legend.type == DoraLegend.LegendType.SQUARE)
                textHeight.coerceAtLeast(legend.iconSize)
            else if (legend.type == DoraLegend.LegendType.CIRCLE) textHeight
            else textHeight.coerceAtLeast(legend.iconSize)
            centerY += (itemHeight + legend.legendItemGap)
            val baselineY = centerY + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            canvas.drawText(entry.label, 0, entry.label.length,
                    legend.iconSize + legend.iconLabelGap + legend.xAxisOffset, baselineY + legend.yAxisOffset, labelPaint)
            when (legend.type) {
                DoraLegend.LegendType.SQUARE -> {
                    var rect = RectF(legend.iconSize / 6, centerY - legend.iconSize / 2 + legend.iconSize / 6,
                            legend.iconSize - legend.iconSize / 6, centerY + legend.iconSize / 2 - legend.iconSize / 6)
                    rect.offset(legend.xAxisOffset, legend.yAxisOffset)
                    canvas.drawRect(rect, iconPaint)
                }
                DoraLegend.LegendType.CIRCLE -> {
                    canvas.drawCircle(legend.iconSize / 2 + legend.xAxisOffset, centerY + legend.yAxisOffset,
                            if (legend.iconSize < textHeight * 2 / 3) legend.iconSize / 3 else textHeight / 3, iconPaint)
                }
            }
        }
    }
}