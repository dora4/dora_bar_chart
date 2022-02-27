package dora.widget.chart

abstract class DoraChartData<out E : DoraChartEntry, S : DoraChartDataSet<out E>>(
        vararg var dataSets: S) {
    abstract fun calcMaxEntryValue() : Float
    abstract fun calcMinEntryValue() : Float
}