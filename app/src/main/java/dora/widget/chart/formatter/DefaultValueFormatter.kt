package dora.widget.chart.formatter

import java.text.DecimalFormat

class DefaultValueFormatter : IValueFormatter {

    /**
     * 默认保留一位小数。
     */
    override fun formatValue(value: Float): String {
        return DecimalFormat("0.#").format(value)
    }
}