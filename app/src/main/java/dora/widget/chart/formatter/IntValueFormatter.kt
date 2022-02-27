package dora.widget.chart.formatter

class IntValueFormatter : IValueFormatter {

    override fun formatValue(value: Float): String {
        return "${value.toInt()}"
    }
}