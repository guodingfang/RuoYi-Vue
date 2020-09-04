<template>
  <section class="dwarp">
    <div class="select-warp">
      <span class="tb-span">监测点</span>
      <el-select
        @change="selectEcharts"
        value-key="monitoringEquipmentNo"
        v-model="queryParams.monitorParm"
        placeholder="排序"
      >
        <el-option
          v-for="item in selectList"
          :key="item.monitoringEquipmentNo"
          :label="`${item.monitoringPointName}(${item.onlineOfflineName})`"
          :value="item"
        />
      </el-select>
      <el-radio-group v-model="queryParams.type">
        <el-radio-button :label="1">每日</el-radio-button>
        <el-radio-button :label="2">每月</el-radio-button>
      </el-radio-group>
      <span class="tb-span">日期</span>
      <template v-if="queryParams.type == 1">
        <el-date-picker
          class="is-picker"
          @change="getDayEcharts"
          v-model="queryParams.dustDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择日期"
        >
        </el-date-picker>
      </template>
      <template v-else>
        <el-date-picker
          @change="getMonthEcharts"
          v-model="queryParams.dustDate"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择月份"
        >
        </el-date-picker>
      </template>
    </div>
    <el-row class="chart-group" style="width:100%" :gutter="16">
      <el-col :xl="12" :lg="12" :md="12" :sm="24">
        <div class="card">
          <div class="card_title">
            <h4 class="tit">
              扬尘（PM10）：
              <span class="t_fold">{{dataMoitor.dustPm10}}</span>
              ug/m³
            </h4>
            <div class="t_r">
              <i class="ro_icon" /> PM10 <i class="rt_icon" /> PM2.5
            </div>
          </div>
          <v-chart style="width:100%" ref="rt" :options="dPm" autoresize />
        </div>
      </el-col>
      <el-col :xl="12" :lg="12" :md="12" :sm="24">
        <div class="card">
          <div class="card_title">
            <h4 class="tit">
              噪音：
              <span class="t_fold">{{dataMoitor.dustStrepitus}}</span>
              （dB）
            </h4>
          </div>
          <v-chart style="width:100%" ref="rt" :options="dDb" autoresize />
        </div>
      </el-col>

      <el-col :xl="12" :lg="12" :md="12" :sm="24">
        <div class="card">
          <div class="card_title">
            <h4 class="tit">
              温度：
              <span class="t_fold">{{dataMoitor.dustTemperature}}</span>
              ℃
            </h4>
          </div>
          <v-chart style="width:101%" ref="rt" :options="dTm" autoresize />
        </div>
      </el-col>
      <el-col :xl="12" :lg="12" :md="12" :sm="24">
        <div class="card">
          <div class="card_title">
            <h4 class="tit">
              风速：
              <span class="t_fold">{{dataMoitor.dustWindSpeed}}</span>
              m/s
            </h4>
          </div>
          <v-chart style="width:100%" ref="rt" :options="dSp" autoresize />
        </div>
      </el-col>
    </el-row>
  </section>
</template>
<script>
import dayjs from "dayjs";
import moment from "moment";
const month = dayjs(new Date())
  .startOf("month")
  .format("YYYY-MM");
const day = dayjs().format("YYYY-MM-DD");
import {
  getSelectList,
  getDayPm,
  getMonthPm,
  getDayDb,
  getMonthDb,
  getDayTm,
  getMonthTm,
  getDaySp,
  getMonthSp,
  getDayText,
  getMonthText
} from "@/api/dustMonitor";
import ECharts from "vue-echarts";
export default {
  name: "realTime",
  components: {
    // gauge,gaMonitor
  },
  inject: ["detailParm"],
  data() {
    return {
      selectList: [],
      queryParams: {
        monitorParm: {},
        type: 1,
        dustDate: day
      },
      dPm: {},
      dDb: {},
      dTm: {},
      dSp: {},
      timeData: [],
      dataMoitor: {}
    };
  },
  async created() {
    // const re = await getSelectList({projectId:this.detailParm.projectId})
    const re = await getSelectList();
    this.selectList = re.rows;
    this.queryParams.monitorParm = this.selectList[0];
    this.getDayEcharts();

    function timeControl() {
      timeArr = [];
      var startTime = new Date(
        new Date(new Date().toLocaleDateString()).getTime()
      ).getTime(); //当天凌晨十二点

      // var endTime = timeFormat(moment().format("YYYY-MM-DD HH:mm:ss")); //moment.js获取的现在的时间点
      var endTime =
        new Date(
          new Date(new Date().toLocaleDateString()).getTime()
        ).getTime() +
        24 * 60 * 60 * 1000; //moment.js获取的现在的时间点

      var timerange = endTime - startTime;
      var count = Math.floor(timerange / (10 * 60 * 1000)); //时间间隔 （五分钟：5*60*1000）
      // console.log(count);
      for (var i = 0; i <= count; i++) {
        var modTine = moment(startTime + 10 * 60 * 1000 * i).format(
          "YYYY-MM-DD HH:mm:ss"
        );
        timeArr.push(modTine);
      }
      // if (startTime + count * (60 * 60 * 1000) !== endTime) {
      //   timeArr.push(moment().format("YYYY-MM-DD HH:mm:ss"));
      // }
    }

    var timeArr = [];
    timeControl();
    this.timeData = timeArr;
    // console.log(timeArr);
  },
  watch: {
    "queryParams.type"(val) {
      if (val == 1) {
        this.queryParams.dustDate = day;
        this.getDayEcharts();
      } else {
        this.queryParams.dustDate = month;
        this.getMonthEcharts();
      }
    }
    // queryParams: {
    //   handler(val) {
    //     if (val.type == 1) {

    //     } else {

    //     }
    //     // if (val.type == 1) {
    //     //   // this.getDayPm()
    //     // } else {
    //     //   console.log(123);
    //     //   // this.getMonthPm()
    //     // }
    //   },
    //   deep: true,
    //   // immediate:true
    // },
  },
  methods: {
    selectEcharts() {
      if (this.queryParams.type == 1) {
        this.getDayEcharts();
      } else {
        this.getMonthEcharts();
      }
    },
    async getDayText() {
      const re = await getDayText({
        monitoringEquipmentNo: this.queryParams.monitorParm.monitoringEquipmentNo,
        dustDate: this.queryParams.dustDate
      });
      const { dustPm10,dustStrepitus,dustTemperature,dustWindSpeed } = re.data
      this.dataMoitor = {
        dustPm10,dustStrepitus,dustTemperature,dustWindSpeed
      }
    },
    async getMonthText() {
      const re = await getMonthText({
        monitoringEquipmentNo: this.queryParams.monitorParm.monitoringEquipmentNo,
        dustDate: this.queryParams.dustDate });
      const { dustPm10,dustStrepitus,dustTemperature,dustWindSpeed } = re.data
      this.dataMoitor = {
        dustPm10,dustStrepitus,dustTemperature,dustWindSpeed
      }
    },
    getDayEcharts() {
      this.getDayPm();
      this.getDayDb();
      this.getDayTm();
      this.getDaySp();
      this.getDayText();
    },
    getMonthEcharts() {
      this.getMonthPm();
      this.getMonthDb();
      this.getMonthTm();
      this.getMonthSp();
      this.getMonthText();
    },
    async getDayPm() {
      const re = await getDayPm({
        dustDate: this.queryParams.dustDate,
        monitoringEquipmentNo: this.queryParams.monitorParm
          .monitoringEquipmentNo
      });
      let obj = {
        data: re.rows.map(i => i.dustDate),
        // a: re.rows.map(i => i.dustPm10),
        // b: re.rows.map(i => i.dustPm25),
        a: re.rows.map((i, index) => {
          // return [this.timeData[index], i.dustPm10];
          return [i.dustDate, i.dustPm10];
        }),
        b: re.rows.map((i, index) => {
          return [i.dustDate, i.dustPm25];
        })
      };
      this.setPm(obj);
    },
    async getMonthPm() {
      const re = await getMonthPm({
        dustDate: this.queryParams.dustDate,
        monitoringEquipmentNo: this.queryParams.monitorParm
          .monitoringEquipmentNo
      });
      let obj = {
        data: re.rows.map(i => i.dustDate),
        // a: re.rows.map(i => i.dustPm10),
        // b: re.rows.map(i => i.dustPm25),
        a: re.rows.map((i, index) => {
          return [new Date().getFullYear() + "-" + i.dustDate, i.dustPm10];
        }),
        b: re.rows.map((i, index) => {
          return [new Date().getFullYear() + "-" + i.dustDate, i.dustPm25];
        })
      };
      console.log(obj);
      this.setPm(obj);
    },
    setPm(obj) {
      this.dPm = {
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
          right: "20",
          bottom: "15",
          top: "50",
          containLabel: true
        },
        calculable: true,
        xAxis: [
          {
            // type: "category",
            // type: this.queryParams.type == 1 ? "time" : 'category',
            type: "time",
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
            // minInterval: 1,
            interval:
              this.queryParams.type == 1 ? 5400 * 1000 : 60 * 60 * 60 * 24 * 31,
            // maxInterval: 3600 * 24 * 1000,
            axisLabel: {
              textStyle: {
                fontSize: 10,
                color: "#9ba3a3"
              },
              formatter: val => {
                var date = new Date(val);
                const minute = dayjs(date).minute();
                var texts = [dayjs(date).hour(), minute == 0 ? "00" : minute];
                var month = [dayjs(date).month() + 1, dayjs(date).date()];
                // return texts.join(":")
                return this.queryParams.type == 1
                  ? texts.join(":")
                  : month.join("-");
              }
            },
            data: obj.data
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "ug/m³",
            min: 0,
            minInterval: 1,
            // nameRotate: 90,
            // nameGap: 61,
            nameTextStyle: {
              padding: [0, 0, 10, 5],
              fontSize: 14
            },
            // nameLocation: "middle",
            position: "right",
            axisLine: {
              // show: false
              lineStyle: {
                color: "#9aa2a2"
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: "dashed"
              }
            }
          }
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
            symbol: "none", //取消折点圆圈
            itemStyle: {
              normal: {
                color: "#9bd4f6"
              }
            },
            areaStyle: {
              // 颜色自上而下渐变
              color: new ECharts.graphic.LinearGradient(0, 0, 0, 1, [
                // { offset: 0, color: '#1978EF' },
                // { offset: 1, color: '#4AACF0' }
                {
                  offset: 0,
                  color: "rgba(166,216,248,1)"
                },
                {
                  offset: 0.5,
                  color: "rgba(166,216,248,0.5)"
                },
                {
                  offset: 1,
                  color: "rgba(166,216,248,0.00)"
                }
              ])
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
            symbol: "none", //取消折点圆圈
            itemStyle: {
              normal: {
                color: "#4ddaa5"
              }
            },
            areaStyle: {
              // 颜色自上而下渐变
              color: new ECharts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "rgba(179,252,230,1)"
                },
                {
                  offset: 0.5,
                  color: "rgba(179,252,230,0.5)"
                },
                {
                  offset: 1,
                  color: "rgba(179,252,230,0.00)"
                }
              ])
            },
            data: obj.b
          }
        ]
      };
    },

    async getDayDb() {
      const re = await getDayDb({
        dustDate: this.queryParams.dustDate,
        monitoringEquipmentNo: this.queryParams.monitorParm
          .monitoringEquipmentNo
      });
      let obj = {
        data: re.rows.map(i => i.dustDate),
        a: re.rows.map((i, index) => {
          return [i.dustDate, i.dustStrepitus];
        }),
      };
      this.setDb(obj);
    },
    async getMonthDb() {
      const re = await getMonthDb({
        dustDate: this.queryParams.dustDate,
        monitoringEquipmentNo: this.queryParams.monitorParm
          .monitoringEquipmentNo
      });
      let obj = {
        data: re.rows.map(i => i.dustDate),
        a: re.rows.map((i, index) => {
          return [new Date().getFullYear() + "-" + i.dustDate, i.dustStrepitus];
        }),
      };
      this.setDb(obj);
    },
    setDb(obj) {
      console.log(obj);
      this.dDb = {
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
          right: "20",
          bottom: "15",
          top: "50",
          containLabel: true
        },
        calculable: true,
        xAxis: [
          {
            // type: "category",
            // type: this.queryParams.type == 1 ? "time" : 'category',
            type: "time",
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
            // minInterval: 1,
            interval:
              this.queryParams.type == 1 ? 5400 * 1000 : 60 * 60 * 60 * 24 * 31,
            // maxInterval: 3600 * 24 * 1000,
            axisLabel: {
              textStyle: {
                fontSize: 10,
                color: "#9ba3a3"
              },
              formatter: val => {
                var date = new Date(val);
                const minute = dayjs(date).minute();
                var texts = [dayjs(date).hour(), minute == 0 ? "00" : minute];
                var month = [dayjs(date).month() + 1, dayjs(date).date()];
                // return texts.join(":")
                return this.queryParams.type == 1
                  ? texts.join(":")
                  : month.join("-");
              }
            },
            data: obj.data
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "dB",
            min: 0,
            minInterval: 1,
            // nameRotate: 90,
            // nameGap: 61,
            nameTextStyle: {
              padding: [0, 0, 10, 7],
              fontSize: 14
            },
            // nameLocation: "middle",
            position: "right",
            axisLine: {
              // show: false
              lineStyle: {
                color: "#9aa2a2"
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: "dashed"
              }
            }
          }
        ],
        // dataZoom: {
        //   show: true,
        //   // type: 'inside',
        //   start: 0,
        //   end: 40
        // },
        series: [
          {
            name: "dB",
            // type: "bar",
            type: "line",
            // stack: "总量",
            symbol: "none", //取消折点圆圈
            barMaxWidth: 15,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#9bd4f6"
              }
            },
            areaStyle: {
              // 颜色自上而下渐变
              color: new ECharts.graphic.LinearGradient(0, 0, 0, 1, [
                // { offset: 0, color: '#1978EF' },
                // { offset: 1, color: '#4AACF0' }
                {
                  offset: 0,
                  color: "rgba(166,216,248,1)"
                },
                {
                  offset: 0.5,
                  color: "rgba(166,216,248,0.5)"
                },
                {
                  offset: 1,
                  color: "rgba(166,216,248,0.00)"
                }
              ])
            },
            data: obj.a
          }
        ]
      };
    },

    async getDayTm() {
      const re = await getDayTm({
        dustDate: this.queryParams.dustDate,
        monitoringEquipmentNo: this.queryParams.monitorParm.monitoringEquipmentNo
      });
      let obj = {
        data: re.rows.map(i => i.dustDate),
        a: re.rows.map((i, index) => {
          return [i.dustDate, i.dustTemperature];
        }),
      };
      this.setTm(obj);
    },
    async getMonthTm() {
      const re = await getMonthTm({
        dustDate: this.queryParams.dustDate,
        monitoringEquipmentNo: this.queryParams.monitorParm
          .monitoringEquipmentNo
      });
      let obj = {
        data: re.rows.map(i => i.dustDate),
        a: re.rows.map((i, index) => {
          return [new Date().getFullYear() + "-" + i.dustDate, i.dustTemperature];
        }),
      };
      this.setTm(obj);
    },
    setTm(obj) {
      this.dTm = {
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
          right: "20",
          bottom: "15",
          top: "50",
          containLabel: true
        },
        calculable: true,
        xAxis: [
          {
            // type: "category",
            // type: this.queryParams.type == 1 ? "time" : 'category',
            type: "time",
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
            // minInterval: 1,
            interval:
              this.queryParams.type == 1 ? 5400 * 1000 : 60 * 60 * 60 * 24 * 31,
            // maxInterval: 3600 * 24 * 1000,
            axisLabel: {
              textStyle: {
                fontSize: 10,
                color: "#9ba3a3"
              },
              formatter: val => {
                var date = new Date(val);
                const minute = dayjs(date).minute();
                var texts = [dayjs(date).hour(), minute == 0 ? "00" : minute];
                var month = [dayjs(date).month() + 1, dayjs(date).date()];
                // return texts.join(":")
                return this.queryParams.type == 1
                  ? texts.join(":")
                  : month.join("-");
              }
            },
            data: obj.data
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "℃",
            min: 0,
            minInterval: 1,
            // nameRotate: 90,
            // nameGap: 61,
            nameTextStyle: {
              padding: [0, 0, 10, 3],
              fontSize: 14
            },
            // nameLocation: "middle",
            position: "right",
            axisLine: {
              // show: false
              lineStyle: {
                color: "#9aa2a2"
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: "dashed"
              }
            }
          }
        ],
        // dataZoom: {
        //   show: true,
        //   // type: 'inside',
        //   start: 0,
        //   end: 40
        // },
        series: [
          {
            name: "温度",
            // type: "bar",
            type: "line",
            // stack: "总量",
            symbol: "none", //取消折点圆圈
            barMaxWidth: 15,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#35cbd9"
              }
            },
            areaStyle: {
              // 颜色自上而下渐变
              color: new ECharts.graphic.LinearGradient(0, 0, 0, 1, [
                // { offset: 0, color: '#1978EF' },
                // { offset: 1, color: '#4AACF0' }
                {
                  offset: 0,
                  color: "rgba(143,240,254,1)"
                },
                {
                  offset: 0.5,
                  color: "rgba(143,240,254,0.5)"
                },
                {
                  offset: 1,
                  color: "rgba(143,240,254,0.00)"
                }
              ])
            },
            data: obj.a
          }
        ]
      };
    },

    async getDaySp() {
      const re = await getDaySp({
        dustDate: this.queryParams.dustDate,
        monitoringEquipmentNo: this.queryParams.monitorParm.monitoringEquipmentNo
      });
      let obj = {
        data: re.rows.map(i => i.dustDate),
        a: re.rows.map((i, index) => {
          return [i.dustDate, i.dustWindSpeed];
        }),
      };
      this.setSp(obj);
    },
    async getMonthSp() {
      const re = await getMonthSp({
        dustDate: this.queryParams.dustDate,
        monitoringEquipmentNo: this.queryParams.monitorParm
          .monitoringEquipmentNo
      });
      let obj = {
        data: re.rows.map(i => i.dustDate),
        a: re.rows.map((i, index) => {
          return [new Date().getFullYear() + "-" + i.dustDate, i.dustWindSpeed];
        }),
      };
      this.setSp(obj);
    },
    setSp(obj) {
      this.dSp = {
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
          right: "20",
          bottom: "15",
          top: "50",
          containLabel: true
        },
        calculable: true,
        xAxis: [
          {
            // type: "category",
            // type: this.queryParams.type == 1 ? "time" : 'category',
            type: "time",
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
            // minInterval: 1,
            interval:
              this.queryParams.type == 1 ? 5400 * 1000 : 60 * 60 * 60 * 24 * 31,
            // maxInterval: 3600 * 24 * 1000,
            axisLabel: {
              textStyle: {
                fontSize: 10,
                color: "#9ba3a3"
              },
              formatter: val => {
                var date = new Date(val);
                const minute = dayjs(date).minute();
                var texts = [dayjs(date).hour(), minute == 0 ? "00" : minute];
                var month = [dayjs(date).month() + 1, dayjs(date).date()];
                // return texts.join(":")
                return this.queryParams.type == 1
                  ? texts.join(":")
                  : month.join("-");
              }
            },
            data: obj.data
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "m/s",
            min: 0,
            minInterval: 1,
            // nameRotate: 90,
            // nameGap: 61,
            nameTextStyle: {
              padding: [0, 0, 10, 5],
              fontSize: 14
            },
            // nameLocation: "middle",
            position: "right",
            axisLine: {
              // show: false
              lineStyle: {
                color: "#9aa2a2"
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: "dashed"
              }
            }
          }
        ],
        // dataZoom: {
        //   show: true,
        //   // type: 'inside',
        //   start: 0,
        //   end: 40
        // },
        series: [
          {
            name: "风速",
            // type: "bar",
            type: "line",
            // stack: "总量",
            barMaxWidth: 15,
            symbol: "none", //取消折点圆圈
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#afe551"
              }
            },
            areaStyle: {
              // 颜色自上而下渐变
              color: new ECharts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "rgba(221,255,169,1)"
                },
                {
                  offset: 0.5,
                  color: "rgba(221,255,169,0.5)"
                },
                {
                  offset: 1,
                  color: "rgba(221,255,169,0.00)"
                }
              ])
            },
            data: obj.a
          }
        ]
      };
    }
  }
};
</script>
<style lang="scss" scoped>
$--border-size: 1px solid #dddcdc;
.card {
  border: $--border-size;
  margin-top: 17px;
  overflow: hidden;
  .card_title {
    @include flex(flex-start, center);
  }
  .tit {
    border-left: 4px solid #1ab394;
    padding-left: 10px;
    margin-left: 17px;
    width: 100%;
    font-weight: 400;
    font-size: 14px;
    .t_fold {
      font-size: 16px;
      font-weight: bold;
    }
  }
  .t_r {
    width: 200px;
    font-size: 14px;
    @include flex(flex-start, center);
    .ro_icon,
    .rt_icon {
      width: 17px;
      margin: 0 7px;
      background: #37cbcb;
      display: inline-block;
      height: 2px;
    }
    .rt_icon {
      background: #1978ef;
    }
  }
}
.dwarp {
  width: 100%;
  height: calc(100vh - 136px);
  overflow-y: auto;
  overflow-x: hidden;
  display: flex;
  flex-wrap: wrap;
}
.comt {
  margin-left: 17px;
}
.tb-span {
  color: #999999;
  font-size: 14px;
  margin: 0 10px;
}
.select-warp {
  position: absolute;
  top: -40px;
  right: 0;
}
::v-deep{
  .is-picker{
    .el-input__suffix{
      display: none;
    }
  }
}
</style>
