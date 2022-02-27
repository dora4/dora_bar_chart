package dora.widget.chart

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewGroup
import dora.widget.chart.animation.DoraChartAnimator
import dora.widget.chart.component.DoraLegend
import dora.widget.chart.formatter.IntValueFormatter
import dora.widget.chart.formatter.IValueFormatter
import dora.widget.chart.renderer.DoraDataRenderer
import dora.widget.chart.renderer.DoraLegendRenderer
import kotlin.properties.Delegates

abstract class DoraChartView<E : DoraChartEntry, S : DoraChartDataSet<E>,
        T : DoraChartData<E, S>>
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                          defStyleAttr: Int = 0) : ViewGroup(context,
        attrs, defStyleAttr) {

    private lateinit var legend: DoraLegend
    lateinit var dataRenderer: DoraDataRenderer
    lateinit var legendRenderer: DoraLegendRenderer
    lateinit var animator: DoraChartAnimator
    internal var valueFormatter: IValueFormatter = IntValueFormatter()
    internal var data: T by Delegates.notNull()

    fun setValueFormatter(formatter: IValueFormatter) {
        this.valueFormatter = formatter
    }

    fun setData(data: T) {
        this.data = data
        postInvalidate()
    }

    fun setLegend(legend: DoraLegend) {
        this.legend = legend
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (data == null) {
            return
        }
        dataRenderer.drawShape(canvas)
        dataRenderer.drawLabel(canvas)
        legendRenderer.drawLegend(legend, canvas)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        for (i in 0 until childCount) {
            getChildAt(i).layout(left, top, right, bottom)
        }
    }

    protected abstract fun createDataRenderer() : DoraDataRenderer

    protected open fun init() {
        // 没有子控件可绘制且不设置背景的情况下，也要让ViewGroup调用onDraw
        // 预留将子控件作为图层进行绘制
        setWillNotDraw(false)
        legend = DoraLegend()
        dataRenderer = createDataRenderer()
        animator = DoraChartAnimator { }
        legendRenderer = DoraLegendRenderer(animator)
    }

    init {
        init()
    }
}