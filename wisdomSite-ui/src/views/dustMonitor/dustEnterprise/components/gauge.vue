<template>
  <section>
    <h4 class="tit">环境监测</h4>
    <div class="card-ecarts">
      <v-chart style="height: 270px" ref="rt" :options="p2" autoresize />
      <v-chart style="height: 270px" ref="rt" :options="p1" autoresize />
      <v-chart style="height: 270px" ref="rt" :options="db" autoresize />
    </div>
    <div class="cest">
      <div class="card-text">
        <div class="ct-l">
          <img src="./../image/1.png" alt="">
          <span>温度</span>
        </div>
        <div class="ct-r">{{obj.temperature}}℃</div>
      </div>
      <div class="card-text">
        <div class="ct-l">
          <img src="./../image/2.png" alt="">
          <span>湿度</span>
        </div>
        <div class="ct-r">{{obj.humidity}}%RH</div>
      </div>
      <div class="card-text">
        <div class="ct-l">
          <img src="./../image/3.png" alt="">
          <span>风速</span>
        </div>
        <div class="ct-r">{{obj.windVelocity}}m/s
          <span class="ctr-tetx">风向：{{obj.windDirection}}</span>
        </div>
      </div>
    </div>

    <h4 class="tit">天气预报</h4>
    <!-- <div class="card-sky"> -->
    <div class="iframe-warp">
      <!-- scrolling="no"  -->
      <iframe
        ref="iframeRef"
        class='iframeRef'
        frameborder='0'
        scrolling="no"
        width='100%'
        src='https://apip.weatherdt.com/v2/h5.html?bg=3&md=4&lc=auto&key=8uVkWRo5tA'
      />
      <!-- <div class="csky-title">
        12345
      </div> -->
    </div>
  </section>
</template>
<script>
import {
  getRealText,
 } from "@/api/dustMonitor";
export default {
  name: "realTime",
  data() {
    return {
      p2: {},
      p1: {},
      db: {},
      obj:{}
    };
  },
  props: {
    monitorParm: {
      type: Object,
      default: () => {}
    }
  },
  inject: ['detailParm'],
  watch: {
    monitorParm: {
      handler(val) {
        const {videoEquipmentId,monitoringEquipmentNo} = val
        this.getRealText(monitoringEquipmentNo)
      },
      deep:true,
    }
  },
  async created() {
    // const re = await getRealText({monitoringEquipmentNo: this.monitorParm.monitoringEquipmentNo})
  },
  methods: {
    async getRealText(monitoringEquipmentNo) {
      const re = await getRealText({monitoringEquipmentNo: monitoringEquipmentNo})
      const {pmTwentyFive, pmTen, strepitus, temperature, humidity, windVelocity, windDirection} = re.data
      this.getP2(pmTwentyFive)
      this.getP1(pmTen)
      this.getDb(strepitus)
      this.obj= { temperature, humidity, windVelocity, windDirection }

    },
    getP2(val) {
      this.p2 = {
        tooltip: {
          formatter: "{b} : {c}"
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
            detail: {
              formatter: "{value}",
              fontSize: 14,
            },
            data: [{ value: val, name: "PM2.5 \n （ug/m³）" }],
            axisLine: {            // 坐标轴线
              lineStyle: {       // 属性lineStyle控制线条样式
                // color: [[1, 'black']],
                width: 10,
                // color: [[0.2, '#77f4c5'], [0.8, '#ecf7cd'], [1, '#ff9c9c']]
                color: [[0.2, '#77f4c5'], [0.8, '#ecf7cd'], [1, '#ff9c9c']]
              }
            },
            axisTick: {
              show: false,
            },
            pointer: { // 仪表盘指针。
              show: true, // 是否显示指针,默认 true。
              length: "70%", // 指针长度，可以是绝对数值，也可以是相对于半径的百分比,默认 80%。
              width: 3, // 指针宽度,默认 8。
            },

            itemStyle: { // 仪表盘指针样式。
              color: "#0bc299",
            },

            splitLine: {            // 分隔线样式。
              show: false,
              length: 15,             // 分隔线线长。支持相对半径的百分比,默认 30。
            },
            title: {
                // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                fontWeight: 'bolder',
                fontSize: 14,
                offsetCenter: [0,"-33%"]
            },
            detail: { // 仪表盘详情，用于显示数据。
              color: "#0bc299", // 文字的颜色,默认 auto。
              fontSize: 20
            }
          }
        ]
      }
    },
    getP1(val) {
      this.p1 = {
        tooltip: {
          formatter: "{b} : {c}"
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
            detail: {
              formatter: "{value}",
              fontSize: 14,
            },
            data: [{ value: val, name: "PM10 \n （ug/m³）" }],
            axisLine: {            // 坐标轴线
              lineStyle: {       // 属性lineStyle控制线条样式
                width: 10,
                color: [[0.2, '#77f4c5'], [0.8, '#ecf7cd'], [1, '#ff9c9c']]
              }
            },
            axisTick: {
              show: false,
            },
            pointer: { // 仪表盘指针。
              show: true, // 是否显示指针,默认 true。
              length: "70%", // 指针长度，可以是绝对数值，也可以是相对于半径的百分比,默认 80%。
              width: 3, // 指针宽度,默认 8。
            },
            itemStyle: { // 仪表盘指针样式。
              color: "#0bc299",
            },
            splitLine: {            // 分隔线样式。
              show: false,
              length: 15,             // 分隔线线长。支持相对半径的百分比,默认 30。
            },
            title: {
                // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                fontWeight: 'bolder',
                fontSize: 14,
                offsetCenter: [0,"-33%"]
            },
            detail: { // 仪表盘详情，用于显示数据。
              color: "#0bc299", // 文字的颜色,默认 auto。
              fontSize: 20
            }
          }
        ]
      }
    },
    getDb(val) {
      this.db = {
        tooltip: {
          formatter: "{b} : {c}"
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
            detail: {
              formatter: "{value}",
              fontSize: 14,
            },
            data: [{ value: val, name: "噪音 \n （dB）" }],
            axisLine: {            // 坐标轴线
              lineStyle: {       // 属性lineStyle控制线条样式
                width: 10,
                color: [[0.2, '#77f4c5'], [0.8, '#ecf7cd'], [1, '#ff9c9c']]
              }
            },
            axisTick: {
              show: false,
            },
            pointer: { // 仪表盘指针。
              show: true, // 是否显示指针,默认 true。
              length: "70%", // 指针长度，可以是绝对数值，也可以是相对于半径的百分比,默认 80%。
              width: 3, // 指针宽度,默认 8。
            },
            itemStyle: { // 仪表盘指针样式。
              color: "#0bc299",
            },
            splitLine: {            // 分隔线样式。
              show: false,
              length: 15,             // 分隔线线长。支持相对半径的百分比,默认 30。
            },
            title: {
                // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                fontWeight: 'bolder',
                fontSize: 14,
                offsetCenter: [0,"-33%"]
            },
            detail: { // 仪表盘详情，用于显示数据。
              color: "#0bc299", // 文字的颜色,默认 auto。
              fontSize: 20
            }
          }
        ]
      }
    },
  },
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
.card-ecarts{
  display: flex;
  border: 1px solid #dddcdc;
}
.cest{
  @include flex(flex-start, center);
  background: #fbfcfc;
  border: 1px solid #dddcdc;
  border-top: none;
  .card-text{
    flex: 1;
    @include flex(center, center);
    .ct-l,.ct-r{
      text-align: center;
      margin: 12px 0;
      @include flex(center, center);
      img{
        margin-right: 10px;
      }
    }
    .ct-l{
      width: 100px;
    }
    .ct-r{
      flex: 1;
      font-size: 20px;
      color: #4e9e7e;
      .ctr-tetx{
        font-size: 12px;
        margin-left: 7px;
        color: #939a9b;
      }
    }
  }
}
.card-sky{
  border: 1px solid #dddcdc;
}
.iframe-warp{
  overflow: hidden;
  position: relative;
  height: 300px;
  .iframeRef{
    position: absolute;
    top: -90px;
    height: 390px;
  }
}
</style>
