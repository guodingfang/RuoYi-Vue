<template>
  <section>
    <h4 class="tit">监测统计</h4>
    <div class="card-ecarts">
      <v-chart style="width: 100%;height: 331px" ref="rt" :options="Toptions" autoresize />
      <v-chart style="width: 100%;height: 331px" ref="rt" :options="Boptions" autoresize />
    </div>
  </section>
</template>
<script>
import { getMonitTop, getMonitBottom } from "@/api/dustMonitor";
import ECharts from 'vue-echarts'
export default {
  name: "realTime",
  data() {
    return {
      Toptions: {},
      Boptions: {}
    };
  },
  props: {
    monitorParm: {
      type: Object,
      default: () => {}
    }
  },
  inject: ["detailParm"],
  watch: {
    monitorParm: {
      handler(val) {
        const {videoEquipmentId,monitoringEquipmentNo} = val
        this.getMonitTop(monitoringEquipmentNo)
        this.getMonitBottom(monitoringEquipmentNo)
      },
      deep:true,
    }
  },
  async created() {

    // console.log(this.detailParm);
  },
  methods: {
    async getMonitTop(monitoringEquipmentNo) {
      const re = await getMonitTop({monitoringEquipmentNo: monitoringEquipmentNo})
      let obj = {
        data: re.rows.map(i => i.dustDate),
        a: re.rows.map(i => i.dustPm10),
        b: re.rows.map(i => i.dustPm25),
      }
      this.setTEcharts(obj)
    },
    async getMonitBottom(monitoringEquipmentNo) {
      const re = await getMonitBottom({monitoringEquipmentNo: monitoringEquipmentNo})
      let obj = {
        data: re.rows.map(i => i.dustDate),
        a: re.rows.map(i => i.dustStrepitus),
      }
      this.setBEcharts(obj)
    },
    setTEcharts(obj) {
      this.Toptions = {
        backgroundColor: "#ffffff",
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
            textStyle: {
              color: "#fff"
            }
          }
        },
        grid: {
          left: "20",
          right: "5",
          bottom: "40",
          top: "70",
          containLabel: true
        },
        calculable: true,
        xAxis: [
          {
            type: "category",
            axisLine: {
              lineStyle: {
                color: "#9aa2a2"
              }
            },
            splitLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitArea: {
              show: false
            },
            axisLabel: {
              interval: 0,
              textStyle: {
                fontSize: 10,
                color: "#9ba3a3"
              }
            },
            data: obj.data
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '近7天扬尘变化情况（单位：up/m3）',
            min: 0,
            minInterval: 1,
            // nameRotate: 90,
            // nameGap: 61,
            nameTextStyle: {
               padding: [0, 0, 20, 170],
               fontSize: 14
            },
            // nameLocation: "middle",
            position: 'left',
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: "dashed",
              }
            },
          },
        ],
        // dataZoom: {
        //   show: true,
        //   // type: 'inside',
        //   start: 0,
        //   end: 40
        // },
        series: [
          {
            name: "PM10",
            // type: "bar",
            type: "line",
            // stack: "总量",
            barMaxWidth: 15,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#1978ef"
              }
            },
            areaStyle: { // 颜色自上而下渐变
              color: new ECharts.graphic.LinearGradient(0, 0, 0, 1, [
                // { offset: 0, color: '#1978EF' },
                // { offset: 1, color: '#4AACF0' }
                {
                  offset: 0,
                  color: 'rgba(25,120,239,1)'
                }, {
                  offset: 0.5,
                  color: 'rgba(25,120,239,0.5)'
                }, {
                  offset: 1,
                  color: 'rgba(25,120,239,0.00)'
                }
              ]),
            },
            data: obj.a
          },
          {
            name: "PM2.5",
            // type: "bar",
            type: "line",
            // stack: "总量",
            barMaxWidth: 15,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#37CBCB"
              }
            },
            areaStyle: { // 颜色自上而下渐变
              color: new ECharts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#37CBCB' },
                { offset: 1, color: '#93EED2' }
              ]),
            },
            data: obj.b
          },
        ]
      }
    },
    setBEcharts(obj) {
      this.Boptions = {
        backgroundColor: "#ffffff",
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
            textStyle: {
              color: "#fff"
            }
          }
        },
        grid: {
          left: "20",
          right: "5",
          bottom: "40",
          top: "70",
          containLabel: true
        },
        calculable: true,
        xAxis: [
          {
            type: "category",
            axisLine: {
              lineStyle: {
                color: "#9aa2a2"
              }
            },
            splitLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitArea: {
              show: false
            },
            axisLabel: {
              interval: 0,
              textStyle: {
                fontSize: 10,
                color: "#9ba3a3"
              }
            },
            data: obj.data
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '近7天噪音变化情况（单位：dB）',
            min: 0,
            minInterval: 1,
            // nameRotate: 90,
            // nameGap: 61,
            nameTextStyle: {
               padding: [0, 0, 20, 170],
               fontSize: 14
            },
            // nameLocation: "middle",
            position: 'left',
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: "dashed",
              }
            },
          },
        ],
        series: [
          {
            name: "单位",
            type: "bar",
            // stack: "总量",
            barMaxWidth: 15,
            barGap: "10%",
            label: {
                show: true,
                position: 'top'
            },
            itemStyle: {
              normal: {
                color: "#2dc3d6"
              }
            },
            data: obj.a
          }
        ]
      }
    },
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
.card-ecarts {
  // display: flex;
  overflow: inherit;
  border: 1px solid #dddcdc;
  margin-right: 1px;
}
</style>
