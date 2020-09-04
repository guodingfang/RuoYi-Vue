<template>
  <div class="stachart-wrap">
    <section class="chart-box">
      <div class="chart lt-chart">
        <p class="chart-tit">司机不良操作</p>
        <div class="chart-cont">
          <chart
            ref="lt"
            :options="ltOptions"
            autoresize
          />
        </div>
      </div>
      <div class="chart rt-chart">
        <p class="chart-tit">设备告警</p>
        <div class="chart-cont">
          <chart
            ref="rt"
            :options="rtOptions"
            autoresize
          />
        </div>
      </div>
      <div class="chart btm-chart">
        <p class="chart-tit">设备工作记录</p>
        <div class="chart-cont">
          <div class="half">
            <chart
              ref="lb"
              :options="lbOptions"
              autoresize
            />
          </div>
          <div class="half">
            <chart
              ref="rb"
              :options="rbOptions"
              autoresize
            />
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
  import Moment from 'moment';
  import _ from 'lodash';
  import { extendMoment } from 'moment-range';
  import ECharts from 'vue-echarts'
  import 'echarts/lib/chart/bar'
  import 'echarts/lib/chart/line'
  import 'echarts/lib/chart/pie'
  import 'echarts/lib/chart/map'
  import 'echarts/lib/component/tooltip'
  import 'echarts/lib/component/legend'
  import 'echarts/lib/component/title'
  import {
    driverBadP,
    deviceAlarmP,
    equipmentWorkRecordP,
  } from "@/api/smartMonitor/elevator";
  import OrganizeTree from "@/components/OrganizeTree/index";
  const moment = extendMoment(Moment);
  const range7 = moment.range(moment().subtract(6, 'days'), moment());
  const recent7day = Array.from(range7.by('days')).map(m => m.format('MM-DD'))
  export default {
    name: "RecentWeekChart",
    components: {
      OrganizeTree,
      'chart': ECharts
    },
    props: {
      projectId: {
        type: Number,
        default: 0,
      },
      queryDate: {
        type: String,
        require: true,
        dafault:  moment().format("yyyy-MM-DD"),
      }
    },
    watch: {
      queryParams: {
        deep: true,
        handler() {
          this.getLtData();
          this.getRtData();
        }
      },
    },
    data() {
      return {
        defaultProps: {
          children: "children",
          label: "regionName",
          isLeaf: 'leaf'
        },
        ltOptions: {
          backgroundColor: "#ffffff",
          "tooltip": {
            "trigger": "axis",
            "axisPointer": {
              "type": "shadow",
              textStyle: {
                color: "#fff"
              }

            },
          },
          grid: {
            left: '5',
            right: '5',
            bottom: '10',
            top: '55',
            containLabel: true
          },
          legend: {
            data: ['报警次数', '报警率'],
            orient: 'horizontal',
            icon: "rect",
            show: true,
            right: 20,
            top: 0,
            itemWidth: 12,
            itemHeight: 12
          },
          "calculable": true,
          "xAxis": [{
            "type": "category",
            "axisLine": {
              lineStyle: {
                color: '#9aa2a2'
              }
            },
            "splitLine": {
              "show": false
            },
            "axisTick": {
              "show": false
            },
            "splitArea": {
              "show": false
            },
            "axisLabel": {
              "interval": 0,
              textStyle:{
                fontSize:10,
                color: '#9ba3a3'
              }
            },
            "data": recent7day,
          }],
          yAxis: [
            {
              type: 'value',
              name: '单位：次',
              min: 0,
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
            {
              type: 'value',
              name: '单位：%',
              position: 'right',

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
            }
          ],
          series: [
            {"name": "报警次数",
              "type": "bar",
              "stack": "总量",
              "barMaxWidth": 15,
              "barGap": "10%",
              "itemStyle": {
                "normal": {
                  "color": "#f05454",
                }
              },
              data: [0, 0, 0, 0, 0, 0, 0]

            },
            {
              name:'报警率',
              yAxisIndex: 1,
              symbolSize:10,
              symbol:'circle',
              type:'line',
              "itemStyle": {
                "normal": {
                  "color": "#5291ff",
                  "barBorderRadius": 0,
                }
              },
              data: [0, 0, 0, 0, 0, 0, 0]
            }]
        },
        rtOptions: {
          backgroundColor: "#ffffff",
          "tooltip": {
            "trigger": "axis",
            "axisPointer": {
              "type": "shadow",
              textStyle: {
                color: "#fff"
              }

            },
          },
          grid: {
            left: '5',
            right: '5',
            bottom: '10',
            top: '55',
            containLabel: true
          },
          legend: {
            data: ['预警次数', '报警次数'],
            orient: 'horizontal',
            icon: "rect",
            show: true,
            right: 20,
            top: 0,
            itemWidth: 12,
            itemHeight: 12
          },
          "calculable": true,
          "xAxis": [{
            "type": "category",
            "axisLine": {
              lineStyle: {
                color: '#9aa2a2'
              }
            },
            "splitLine": {
              "show": false
            },
            "axisTick": {
              "show": false
            },
            "splitArea": {
              "show": false
            },
            "axisLabel": {
              "interval": 0,
              textStyle:{
                fontSize:10,
                color: '#9ba3a3'
              }
            },
            "data": recent7day,
          }],
          yAxis: [
            {
              type: 'value',
              name: '单位：台',
              min: 0,
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
            {
              type: 'value',
              name: '单位：%',
              position: 'right',

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
            }
          ],
          series: [
            {"name": "预警次数",
              "type": "bar",
              "stack": "总量",
              "barMaxWidth": 15,
              "barGap": "10%",
              "itemStyle": {
                "normal": {
                  "color": "#ffc267",
                }
              },
              data: [0, 0, 0, 0, 0, 0, 0]

            },
            {"name": "报警次数",
              "type": "bar",
              "stack": "总量",
              "barMaxWidth": 15,
              "barGap": "10%",
              "itemStyle": {
                "normal": {
                  "color": "#ff813e",
                }
              },
              data: [0, 0, 0, 0, 0, 0, 0]
            }
          ]
        },
        lbOptions: {
          backgroundColor: "#ffffff",
          "tooltip": {
            "trigger": "axis",
            "axisPointer": {
              "type": "shadow",
              textStyle: {
                color: "#fff"
              }

            },
          },
          grid: {
            left: '5',
            right: '5',
            bottom: '10',
            top: '55',
            containLabel: true
          },
          legend: {
            data: ['载重', '工作次数'],
            orient: 'horizontal',
            icon: "rect",
            show: true,
            right: 20,
            top: 0,
            itemWidth: 12,
            itemHeight: 12
          },
          "calculable": true,
          "xAxis": [{
            "type": "category",
            "axisLine": {
              lineStyle: {
                color: '#9aa2a2'
              }
            },
            "splitLine": {
              "show": false
            },
            "axisTick": {
              "show": false
            },
            "splitArea": {
              "show": false
            },
            "axisLabel": {
              "interval": 0,
              textStyle:{
                fontSize:10,
                color: '#9ba3a3'
              }
            },
            "data": recent7day,
          }],
          yAxis: [
            {
              type: 'value',
              name: '单位：t',
              min: 0,
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
            {
              type: 'value',
              name: '单位：次',
              position: 'right',

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
            }
          ],
          series: [
            {"name": "载重",
              "type": "bar",
              "stack": "总量",
              "barMaxWidth": 15,
              "barGap": "10%",
              "itemStyle": {
                "normal": {
                  "color": "#f36feb",
                }
              },
              data: [0, 0, 0, 0, 0, 0, 0]

            },
            {
              name:'工作次数',
              yAxisIndex: 1,
              symbolSize:10,
              symbol:'circle',
              type:'line',
              "itemStyle": {
                "normal": {
                  "color": "#5291ff",
                  "barBorderRadius": 0,
                }
              },
              data: [0, 0, 0, 0, 0, 0, 0]
            }]
        },
        rbOptions: {
          backgroundColor: "#ffffff",
          "tooltip": {
            "trigger": "axis",
            "axisPointer": {
              "type": "shadow",
              textStyle: {
                color: "#fff"
              }

            },
          },
          grid: {
            left: '5',
            right: '5',
            bottom: '10',
            top: '55',
            containLabel: true
          },
          legend: {
            data: ['工作时长', '工作次数'],
            orient: 'horizontal',
            icon: "rect",
            show: true,
            right: 20,
            top: 0,
            itemWidth: 12,
            itemHeight: 12
          },
          "calculable": true,
          "xAxis": [{
            "type": "category",
            "axisLine": {
              lineStyle: {
                color: '#9aa2a2'
              }
            },
            "splitLine": {
              "show": false
            },
            "axisTick": {
              "show": false
            },
            "splitArea": {
              "show": false
            },
            "axisLabel": {
              "interval": 0,
              textStyle:{
                fontSize:10,
                color: '#9ba3a3'
              }
            },
            "data": recent7day,
          }],
          yAxis: [
            {
              type: 'value',
              name: '单位：h',
              min: 0,
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
            {
              type: 'value',
              name: '单位：次',
              position: 'right',

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
            }
          ],
          series: [
            {"name": "工作时长",
              "type": "bar",
              "stack": "总量",
              "barMaxWidth": 15,
              "barGap": "10%",
              "itemStyle": {
                "normal": {
                  "color": "#5bfaff",
                }
              },
              data: [0, 0, 0, 0, 0, 0, 0]

            },
            {
              name:'工作次数',
              yAxisIndex: 1,
              symbolSize:10,
              symbol:'circle',
              type:'line',
              "itemStyle": {
                "normal": {
                  "color": "#5291ff",
                  "barBorderRadius": 0,
                }
              },
              data: [0, 0, 0, 0, 0, 0, 0]
            }]
        },
      }
    },
    created() {
      this.getLtData();
      this.getRtData();
    },
    methods: {
      getLtData() {
        console.log(this.queryParams)
        driverBadP(this.queryParams).then(res => {
          this.ltOptions.xAxis[0].data = _.map(res.data, 'driverName');
          this.ltOptions.series[0].data = _.map(res.data, 'alarmingNumber');
          this.ltOptions.series[1].data = res.data.map(e => (e.alarmingNumber/e.totalNumber)*100);
        })
      },
      getRtData() {
        deviceAlarmP(this.queryParams).then(res => {
          this.rtOptions.xAxis[0].data = _.map(res.data, 'driverName');
          this.rtOptions.series[0].data = _.map(res.data, 'preAlarmingNumber');
          this.rtOptions.series[1].data = _.map(res.data, 'alarmingNumber');
          this.lbOptions.xAxis[0].data = _.map(res.data, 'driverName');
          this.lbOptions.series[0].data = _.map(res.data, 'totalNumber');
          this.lbOptions.series[1].data = _.map(res.data, 'totalWeight');
          this.rbOptions.xAxis[0].data = _.map(res.data, 'driverName');
          this.rbOptions.series[0].data = _.map(res.data, 'totalHour');
          this.rbOptions.series[1].data = _.map(res.data, 'totalNumber');

        })
      },
    },
    computed: {
      queryParams: {
        get() {
          return {
            projectId: this.projectId,
            days: - 1,
            statisticsDate: this.queryDate
          }
        },
        set() {
          console.log('我是set')
        }
      }
    },
  }
</script>

<style scoped lang="scss">
  .stachart-wrap{
    .echarts {
      width: 100%;
      height: 100%;
    }
    display: flex;
    justify-content: space-between;
    padding-top: 20px;
    .tree-box{
      width: 300px;
    }
    .tree-cont{
      margin-top: 10px;
    }
    .chart-box{
      flex: 1;
      display: flex;
      justify-content: space-around;
      flex-wrap: wrap;
      .chart{
        width: 49%;
        height: 380px;
        margin-bottom: 10px;
        padding: 10px;
        border: 1px solid #e9f2ef;
        &.btm-chart{
          width: 98%;
        }
        .chart-tit{
          display: flex;
          justify-content: space-between;
          position: relative;
          margin: 10px 0;
          padding-left: 10px;
          color: #939997;
          font-size: 14px;
          line-height: 1.5715;
          &:after{
            position: absolute;
            left: 0;
            top: 3px;
            width: 4px;
            height: 16px;
            background: #00b588;
            content: " ";
          }
        }
        .chart-cont{
          width: 100%;
          height: calc(100% - 42px);
          display: flex;
          .half{
            width: 50%;
            height: 100%;
          }
        }
      }
    }
  }
</style>
