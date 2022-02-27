package dora.widget.chart

/**
 * 一个图例注记中所有数据的集合。
 */
abstract class DoraChartDataSet<E : DoraChartEntry>(
        val entries: MutableList<E>) {

    fun addEntry(entry: E) {
        this.entries.add(entry)
    }

    fun addEntries(vararg entries: E) {
        this.entries.addAll(entries)
    }

    fun addEntries(entries: List<E>) {
        this.entries.addAll(entries)
    }

    abstract fun calcLegend() : LegendEntry
}