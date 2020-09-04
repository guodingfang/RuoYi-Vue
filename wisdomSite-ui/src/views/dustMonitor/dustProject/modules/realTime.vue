<template>
  <section>
    <h4 class="tit">环境监测</h4>
    <v-chart ref="rt" :options="rtOptions" autoresize />
  </section>
</template>
<script>
export default {
  name: "realTime",
  data() {
    return {
      rtOptions: {
        tooltip: {
          formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {
          feature: {
            restore: {},
            saveAsImage: {}
          }
        },
        series: [
          {
            name: "业务指标",
            type: "gauge",
            detail: { formatter: "{value}%" },
            data: [{ value: 50, name: "PM2.5 \n （ug/m³）" }],
            axisLine: {            // 坐标轴线
                lineStyle: {       // 属性lineStyle控制线条样式
                    width: 20
                }
            },
            title: {
                // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                fontWeight: 'bolder',
                fontSize: 20,
                offsetCenter: [0,"-37%"]
            },
          }
        ]
      }
    };
  }
};
</script>
<style lang="scss" scoped>
.tit {
  border-left: 4px solid #1ab394;
  padding-left: 10px;
  width: 100%;
  .tit_right {
    padding: 0;
    float: right;
  }
}
</style>
