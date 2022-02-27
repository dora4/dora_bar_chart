package dora.widget.chart.component

import dora.widget.chart.DoraChartDataSet
import dora.widget.chart.DoraChartEntry

data class DoraYAxis<E : DoraChartEntry, S : DoraChartDataSet<out E>>(var isShowLeft: Boolean = true,
                                                                      var isShowRight: Boolean = true,
                                                                      // 单边刻度值的数量，如果有正有负，则实际数量为sideScaleValueCount * 2 + 1
                                                                      // 反之，则实际数量为sideScaleValueCount + 1，因为y轴0轴是肯定要绘制的
                                                                      var sideScaleValueCount: Int = 2,
                                                                      var useFormattedScaleValue: Boolean = false) : DoraAxis<E, S>()