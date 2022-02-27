# DoraBarChart

描述：图表引擎之条形统计图

复杂度：★★★★☆

分组：【图表引擎】

关系：[dora_curve_chart](https://github.com/dora4/dora_curve_chart)

技术要点：基本绘图、属性动画

### 照片

![avatar](https://github.com/dora4/dora_bar_chart/blob/main/art/dora_bar_chart.jpg)

### 动图

![avatar](https://github.com/dora4/dora_bar_chart/blob/main/art/dora_bar_chart.gif)

### 软件包

https://github.com/dora4/dora_bar_chart/blob/main/art/dora_bar_chart.apk

### 用法

| 类            | API           | 描述                                       |
| ------------- | ------------- | ------------------------------------------ |
| DoraBarEntry    | value         | 一个Bar的值                                 |
| DoraBarEntry    | showValue     | 是否显示Bar的值                             |
| DoraBarEntry  | isFillBar     | 是否填充Bar      |
| DoraChartView | setData()     | 设置图表的数据                             |
| DoraChartView | setLegend()   | 设置图表的图例和注记                       |
| DoraAxis      | axisLineColor | 坐标轴线的颜色                             |
| DoraAxis      | axisLineWidth | 坐标轴线的宽度                             |
| DoraAxis      | drawGridLine  | 是否绘制网格线                             |
| DoraBarDataset  | lineWidth     | Bar的边框线的宽度       |
| DoraBarDataset  | barWidth         | Bar的宽度                                 |
| DoraBarDataset  | barColor          | Bar的颜色        |
| DoraBarDataset    | signedBarColor  | 负值Bar的颜色                           |
| DoraBarDataset    | labelBarGap | 值文本和Bar之间的间距                           |
| DoraBarDataset    | valueTextSize      | 值文本的文字大小               |
| DoraBarDataset    | valueTextColor       | 值文本的文字颜色                     |
| DoraYAxis    | isShowLeft          | 是否显示左边的y轴轴线 |
| DoraYAxis   | isShowRight         | 是否显示右边的y轴轴线      |
| DoraYAxis   | sideScaleValueCount    | 单边刻度值的数量，不包括0值                                 |
| DoraYAxis   | useFormattedScaleValue     | 是否使用数据的IValueFormatter对刻度值进行格式化                                 |
