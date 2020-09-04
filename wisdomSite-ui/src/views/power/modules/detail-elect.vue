<template>
  <el-dialog
    title="监测详细"
    :visible.sync="isShow"
    width="1200px"
    :before-close="close"
  >
    <vue-scroll ref="scroll">
      <div class="wrcp">
        <div class="wl-ft">
          <div class="tibtl">
            设备信息
          </div>
          <ul class="wl-text" style="height:200px">
            <li>设备名称：{{detailData.equipmentName}}</li>
            <li>设备编号：{{detailData.monitoringEquipment}}</li>
          </ul>
          <div class="tibtl">
            <div class="tib_l">实有数据</div>
            <div class="tib_r">更新时间：{{detailData.insertTime}}</div>
          </div>
          <ul class="wl-text r_li">
            <li>
              <div class="wlr_tl">A相电流</div>
              <div class="wlr_tr">{{detailData.currentU}}A</div>
            </li>
            <li>
              <div class="wlr_tl">A相电压</div>
              <div class="wlr_tr">{{detailData.voltageU}}V</div>
            </li>
            <li>
              <div class="wlr_tl">B相电流</div>
              <div class="wlr_tr">{{detailData.currentV}}A</div>
            </li>
            <li>
              <div class="wlr_tl">B相电压</div>
              <div class="wlr_tr">{{detailData.voltageV}}V</div>
            </li>
            <li>
              <div class="wlr_tl">C相电流</div>
              <div class="wlr_tr">{{detailData.currentW}}A</div>
            </li>
            <li>
              <div class="wlr_tl">C相电压</div>
              <div class="wlr_tr">{{detailData.voltageW}}V</div>
            </li>
            <li>
              <div class="wlr_tl">有功电能</div>
              <div class="wlr_tr">{{detailData.activeElectricity}}kW·h​</div>
            </li>
            <li>
              <div class="wlr_tl">无功电能</div>
              <div class="wlr_tr">{{detailData.reactiveElectricity}}kW·h​</div>
            </li>
          </ul>
        </div>
        <div class="wl-rt">
          <div class="tibtl">
            监测记录
            <el-select @change="getEcharts" v-model="monitorType" placeholder="" style="float: right;">
              <el-option label="近24小时" :value="1" />
              <el-option label="近3天" :value="2" />
              <el-option label="近7天" :value="3" />
            </el-select>
          </div>
          <v-chart
            :style="{ width: '100%', height: '300px' }"
            ref="rt"
            :options="rTtOptions"
            autoresize
          />
          <v-chart
            :style="{ width: '100%', height: '300px' }"
            ref="re"
            :options="rBtOptions"
            autoresize
          />
        </div>
      </div>
    </vue-scroll>
  </el-dialog>
</template>
<script>
import Moment from "moment";
import { extendMoment } from "moment-range";
import { getEcharts } from "@/api/scada/electricity";
const moment = extendMoment(Moment);
const range7 = moment.range(moment().subtract(30, "days"), moment());
const recent7day = Array.from(range7.by("days")).map(m => m.format("MM-DD"));
export default {
  name: "",
  props: {
    detailData: {
      type: [Object, Array],
      default: () => {}
    },
    isShow: {
      type: Boolean,
      default: false
    },
    state: {
      type: String,
      default: () => null
    },
  },
  watch: {
    isShow: {
      async handler(val) {
        if (val) {
          this.getEcharts()
        }
      },
      immediate: true
    }
  },
  data() {
    return {
      aList: [],
      bList:[],
      rTtOptions: {},
      rBtOptions: {},
      monitorType: 1,
    };
  },
  methods: {
    close() {
      this.$emit("on-close");
    },
    async getEcharts(){
      const re = await getEcharts({
        // monitoringEquipment: 2006110504,
        monitoringEquipment: this.detailData.monitoringEquipment,
        monitorType: this.monitorType
      })
      let A = {
        name: '电流（A）',
        a: re.rows.map(i=> i.currentU),
        b: re.rows.map(i=> i.currentV),
        c: re.rows.map(i=> i.currentW),
        data: re.rows.map(i=> i.insertTime)
      }
      let B = {
        name: '电压（V）',
        a: re.rows.map(i=> i.voltageU),
        b: re.rows.map(i=> i.voltageV),
        c: re.rows.map(i=> i.voltageW),
        data: re.rows.map(i=> i.insertTime)
      }
      this.setAEcharts(A)
      this.setBEcharts(B)
    },
    setAEcharts(obj) {
      this.rTtOptions = {
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
          left: "5",
          right: "5",
          bottom: "40",
          top: "55",
          containLabel: true
        },
        legend: {
          // data: ["用电"],
          // orient: "horizontal",
          // icon: "rect",
          // show: true,
          // // right: 20,
          // // top: 0,
          // itemWidth: 24,
          // itemHeight: 24
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
                color: "#2a6e97"
              }
            },
            data: obj.data
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: obj.name,
            min: 0,
            minInterval: 1,
            position: 'left',
            axisLine: {
              // show: false
              lineStyle: {
                color: "#2a6e97"
              }
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
        dataZoom: {
          show: true,
          // type: 'inside',
          start: 0,
          end: 10
        },
        series: [
          {
            name: "A相电流",
            // type: "bar",
            type: "line",
            barMaxWidth: 15,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#f0c546"
              }
            },
            data: obj.a
          },
          {
            name: "B相电流",
            // type: "bar",
            type: "line",
            barMaxWidth: 15,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#f3a16d"
              }
            },
            data: obj.b
          },
          {
            name: "C相电流",
            // type: "bar",
            type: "line",
            barMaxWidth: 15,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#df584b"
              }
            },
            data: obj.c
          }
        ]
      }
    },
    setBEcharts(obj) {
      this.rBtOptions = {
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
          left: "5",
          right: "5",
          bottom: "40",
          top: "55",
          containLabel: true
        },
        legend: {
          // data: ["用电"],
          // orient: "horizontal",
          // icon: "rect",
          // show: true,
          // // right: 20,
          // // top: 0,
          // itemWidth: 24,
          // itemHeight: 24
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
            // interval: 10,
            axisLabel: {
              textStyle: {
                fontSize: 10,
                color: "#2a6e97"
              }
            },
            data: obj.data
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: obj.name,
            min: 0,
            minInterval: 1,
            position: 'left',
            axisLine: {
              // show: false
              lineStyle: {
                color: "#2a6e97"
              }
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
        dataZoom: {
          show: true,
          // type: 'inside',
          start: 0,
          end: 10
        },
        series: [
          {
            name: "A相电压",
            // type: "bar",
            type: "line",
            // stack: "总量",
            barMaxWidth: 15,
            barGap: "10%",
            symbol: "none", //取消折点圆圈
            itemStyle: {
              normal: {
                color: "#9bd4f6"
              }
            },
            data: obj.a
          },
          {
            name: "B相电压",
            // type: "bar",
            type: "line",
            barMaxWidth: 15,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#f3a16d"
              }
            },
            data: obj.b
          },
          {
            name: "C相电压",
            // type: "bar",
            type: "line",
            barMaxWidth: 15,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#df584b"
              }
            },
            data: obj.c
          }
        ]
      }
    },
  }
};
</script>
<style lang="scss" scoped>
$--border-size: 1px solid #dddcdc;
.warp {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  min-height: 80vh;
  background: #fff;
  z-index: $eq-pop;
  padding: 0 40px 20px;
  overflow-y: auto;
  .table-tit {
    font-size: 16px;
    font-weight: normal;
    padding: 16px 30px;
    margin: 0;
    border-bottom: $--border-size;
    .el-icon-close {
      float: right;
    }
  }
}
::v-deep{
  .wrcp {
    display: flex;
    margin: 16px 0 30px;
    .wl-ft,
    .wl-rt {
      border: $--border-size;
    }
    .wl-ft {
      width: 320px;
      margin-right: 16px;
    }
    .wl-rt {
      flex: 1;
    }
    .tibtl {
      line-height: 40px;
      background: #f9faf9;
      padding: 0 10px;
      border-bottom: $--border-size;
      overflow: hidden;
      .tib_l{
        float: left;
      }
      .tib_r{
        float: right;
      }
    }
    .wl-text {
      // height: 200px;
      padding: 0;
      margin: 0;
      // display: flex;
      // flex-flow: column;
      li {
        line-height: 40px;
        padding-left: 20px;
        border-bottom: $--border-size;
        display: flex;
      }
      .wlr_tr{
        padding-right: 20px;
        margin-left: auto;
      }
    }
    .r_li{
      li: {border:  none;}
    }
  }
}

</style>
