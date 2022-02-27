package dora.widget.chart.renderer

import dora.widget.chart.animation.DoraChartAnimator

abstract class Renderer(val animator: DoraChartAnimator) {

    open fun init() {
    }

    open fun initPaints() {
    }
}