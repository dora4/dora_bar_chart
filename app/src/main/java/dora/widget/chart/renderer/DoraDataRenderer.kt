package dora.widget.chart.renderer

import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import dora.widget.chart.animation.DoraChartAnimator

abstract class DoraDataRenderer(animator: DoraChartAnimator)
    : Renderer(animator) {

    protected val shapePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    protected val labelPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    abstract fun drawShape(canvas: Canvas)
    abstract fun drawLabel(canvas: Canvas)

    override fun initPaints() {
        shapePaint.style = Paint.Style.FILL
    }

    init {
        init()
        initPaints()
    }
}