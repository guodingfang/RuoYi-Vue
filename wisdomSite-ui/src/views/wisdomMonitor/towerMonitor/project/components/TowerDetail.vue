<template>
  <div class="monitor-detail-wrap">
    <div class="detail-header">
      <el-button type="text" @click="$router.go(-1)">实时监控 <i class="el-icon-arrow-right"></i></el-button>
      <el-button type="text" style="color: #000000 !important;">塔机监控详情</el-button>
    </div>
    <div class="top-box moni-box">
      <p class="split-tit">实时监控 </p>
      <div class="legend-box">
        <span class="legend-icon"></span>未监控/离线&emsp;
        <span class="legend-icon succ"></span>正常&emsp;
        <span class="legend-icon warn"></span>预警&emsp;
        <span class="legend-icon err"></span>报警
      </div>
      <div class="status-cont">
        <status-range
          title="高度（m）"
          :min="topData.minLiftingheight"
          :max="topData.maxLiftingheight"
          :cur="topData.liftingheight"
          :status="topData.systemStatusWeight|mapStatus"
        />
        <status-range
          title="幅度（m）"
          :min="topData.minRange"
          :max="topData.maxRange"
          :cur="topData.ranges"
          :status="topData.rangeState|mapStatus"
        />
        <status-range
          title="回转（°）"
          :min="minCorner"
          :max="topData.maxCorner"
          :cur="topData.corner"
          :status="topData.rotationState|mapStatus"
        />
        <status-range
          title="当前载重（t）"
          :min="topData.minLiftingWeight"
          :max="topData.maxLiftingWeight"
          :cur="topData.liftingWeight"
          :status="topData.overweightState|mapStatus"
        />
        <status-range
          title="当前允许载重（t）"
          :min="topData.minLiftingWeight"
          :max="topData.maxLiftingWeight"
          :cur="topData.allowLiftingWeight"
          :status="topData.overweightState|mapStatus"
        />
        <status-range
          title="风速（m/s）"
          :min="topData.minWindSpeed"
          :max="topData.maxWindSpeed"
          :cur="topData.windSpeed"
          :status="topData.windSpeedState|mapStatus"
        />
        <status-range
          title="倾斜（°）"
          :min="topData.minAngle"
          :max="topData.maxAngle"
          :cur="topData.angle"
          :status="topData.tiltState|mapStatus"
        />
        <status-range
          title="碰撞提醒"
          min=""
          max=""
          cur=""
          :status="topData.antiCollisionState|mapStatus"
        />
      </div>
    </div>
    <div class="mid-top-box">
      <div class="mt-cont">
        <div class="mt-top">
          <div class="mt-top-item">
            <p class="split-tit">塔机信息 </p>
            <p class="r-top-l-text">备案号：{{topData.recordNo}}</p>
            <p class="r-top-l-text">塔机型号：{{topData.towerCraneModel}}</p>
            <p class="r-top-l-text">产权单位：{{topData.propertyUnit}}</p>
            <p class="r-top-l-text">安装单位：{{topData.installUnit}}</p>
            <p class="r-top-l-text">违章通知：{{topData.violationNotice}}</p>
          </div>
          <div class="mt-top-item">
            <p class="split-tit">司机信息 </p>
            <p class="r-top-l-text">姓名：{{topData.driversName}}</p>
            <p class="r-top-l-text">身份证：{{topData.idNumber}}</p>
            <p class="r-top-l-text">特种作业证：{{topData.specialOperationNumber}}</p>
            <p class="r-top-l-text">身份确认时间：{{topData.identificationTime}}</p>
          </div>
          <div class="mt-top-item">
            <button type="button" @click="openHis()" class="more-btn jl-btn"><span>历史记录</span></button>
            <img class="driver-avatar" :src="topData.photoAddress" alt="">
          </div>
          <div class="mt-top-item">

          </div>
        </div>
        <div class="mt-btm">
          <div class="mt-btm-item">
            <img class="top-monitor-icon s-elevator" src="~@/assets/image/detail-hook.png">
            <p class="top-monitor-text">
              <b>{{topData.yesterdayHoistingNum||0}}<span class="top-monitor-unit">次</span></b>
              <span>昨日吊装次数</span>
            </p>
          </div>
          <div class="mt-btm-item r-line">
            <img class="top-monitor-icon s-elevator" src="~@/assets/image/s_alarm.png">
            <p class="top-monitor-text">
              <b>{{topData.yesterdayViolationNum||0}}<span class="top-monitor-unit">次</span></b>
              <span>昨日违章次数</span>
            </p>
          </div>
          <div class="mt-btm-item">
            <img class="top-monitor-icon s-elevator" src="~@/assets/image/detail-hook.png">
            <p class="top-monitor-text">
              <b>{{topData.todayHoistingNum||0}}<span class="top-monitor-unit">次</span></b>
              <span>今日吊装次数</span>
            </p>
          </div>
          <div class="mt-btm-item">
            <img class="top-monitor-icon s-elevator" src="~@/assets/image/s_alarm.png">
            <p class="top-monitor-text">
              <b>{{topData.todayViolationNum||0}}<span class="top-monitor-unit">次</span></b>
              <span>今日违章次数</span>
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="top-box moni-box">
      <p class="split-tit">设备状态分析
        <el-radio-group v-model="statusAnaNum" @change="getDevStatusAna">
          <el-radio :label="20">近20次</el-radio>
          <el-radio :label="50">近50次</el-radio>
          <el-radio :label="200">近200次</el-radio>
        </el-radio-group>
      </p>
      <div class="chart-cont">
        <e-charts
          ref="mb"
          :options="mbOptions"
          autoresize
        />
      </div>
    </div>
    <div class="top-box moni-box">
      <p class="split-tit">工作循环次数
        <el-radio-group v-model="workCycleNum" @change="getDevCycleAna">
          <el-radio :label="1">近7日</el-radio>
          <el-radio :label="2">近15日</el-radio>
          <el-radio :label="3">近30日</el-radio>
        </el-radio-group>
      </p>
      <div class="chart-cont">
        <e-charts
          ref="btm"
          :options="btmOptions"
          autoresize
        />
      </div>
    </div>
    <history-dialog
      :dialogFlag.sync="dialogFlag"
      :tId="towerCraneId"
    />
  </div>
</template>

<script>
  import Moment from 'moment';
  const moment = extendMoment(Moment);
  const range7 = moment.range(moment().subtract(29, 'days'), moment());
  const recent30day = Array.from(range7.by('days')).map(m => m.format('MM/DD'))
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
    projectMonitoringDetails,
    hookVideoDetails,
    getDevStatusAna,
    getDevCycleAna,
  } from "@/api/smartMonitor/tower";
  import StatusRange from "@/components/StatusRange/index";
  import HistoryDialog from "./HistoryDialog";
  export default {
    name: "TowerDetail",
    components: {
      HistoryDialog,
      StatusRange,
      ECharts
    },
    data() {
      return {
        topData: {},
        statusAnaNum: 20,
        workCycleNum: 1,
        btmList: [],
        queryParams: {
          projectId: this.$store.state.user.curRole.id,
        },
        detailDialogFlag: false,
        dialogFlag: false,

        mbOptions: {
          tooltip: {
            trigger: 'axis',
          },
          grid: {
            left: '5',
            right: '5',
            bottom: '10',
            top: '55',
            containLabel: true
          },
          legend: {
            data: ['吊装重量', '载重百分比'],
            orient: 'horizontal',
            icon: "rect",
            show: true,
            position: 'center',
            itemWidth: 12,
            itemHeight: 12
          },
          xAxis: [{
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
              // "interval": 0,
              textStyle:{
                fontSize:10,
                color: '#9ba3a3'
              }
            },
            "data": recent30day,
          }],

          yAxis: [{
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
          },{
            type: 'value',
            name: '单位：%',
            position: 'right',
            max: 100,
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: false,
              lineStyle: {
                type: "dashed",
              }
            },
          }],
          series: [
            {
              name:'吊装重量',
              type: 'line',
              showAllSymbol: true,
              symbol: 'circle',
              symbolSize: 5,
              lineStyle: {
                normal: {
                  color: "#A582EA",
                },
              },
              itemStyle: {
                color: "#A582EA",
                borderColor: "#A582EA",
                borderWidth: 1,
              },
              areaStyle: {
                normal: {
                  color: 'rgba(136,94,224,0.22)',
                }
              },
              data: []//data.values
            },
            {
              name:'载重百分比',
              type: 'line',
              yAxisIndex: 1,
              showAllSymbol: true,
              symbol: 'circle',
              symbolSize: 5,
              lineStyle: {
                normal: {
                  color: "#5291ff",
                },
              },
              itemStyle: {
                color: "#2CABE3",
                borderColor: "#2CABE3",
                borderWidth: 1,
              },
              areaStyle: {
                normal: {
                  color: 'rgba(82,145,255,0.22)',
                }
              },
              data: []//data.values
            },
          ]
        },
        btmOptions: {
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
            data: ['工作次数', '违章次数'],
            orient: 'horizontal',
            icon: "rect",
            show: true,
            position: 'center',
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
              // "interval": 0,
              textStyle:{
                fontSize:10,
                color: '#9ba3a3'
              }
            },
            "data": recent30day,
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
              max: 100,
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
            {"name": "工作次数",
              "type": "bar",
              "stack": "总量",
              "barMaxWidth": 15,
              "barGap": "10%",
              "itemStyle": {
                "normal": {
                  "color": "#5291ff",
                }
              },
              data: [0, 0, 0, 0, 0, 0, 0]

            },
            {"name": "违章次数",
              yAxisIndex: 1,
              "type": "bar",
              "stack": "总量",
              "barMaxWidth": 15,
              "itemStyle": {
                "normal": {
                  "color": "#f05454",
                  "barBorderRadius": 0,
                }
              },
              data: [0, 0, 0, 0, 0, 0, 0]

            }]
        },
      }
    },
    mounted() {
      this.getDetailData();
      this.getDevStatusAna();
      this.getDevCycleAna();
    },
    methods: {
      openHis() {
        this.dialogFlag = true;
      },
      async getDetailData() {
        const { data:detailData } = await projectMonitoringDetails(this.$route.query);
        if (!!detailData.hookVideoId) {
          const { data:video } = await hookVideoDetails({ 'hookVideoId': detailData.hookVideoId})
          console.log(video)
        }
        this.topData = detailData;
      },
      async getDevStatusAna() {
        const { data } = await getDevStatusAna(this.monitoringEquipmentNo, this.statusAnaNum);
        this.mbOptions.xAxis[0].data = data.map(e => e.analysisTime);
        this.mbOptions.series[0].data = data.map(e => e.analysisLiftingWeight);
        this.mbOptions.series[1].data = data.map(e => e.analysisLoadPercentage);
        // this.yeToData = data;
      },
      async getDevCycleAna() {
        const { data } = await getDevCycleAna(this.monitoringEquipmentNo, this.workCycleNum);
        this.btmOptions.xAxis[0].data = data.map(e => e.hoistingCycleTime);
        this.btmOptions.series[0].data = data.map(e => e.hoistingCycleNum);
        this.btmOptions.series[1].data = data.map(e => e.regulationsNum);
        // this.yeToData = data;
      },
    },
    filters: {
      mapStatus(val) {
        switch (val) {
          case '0' :
            return 'succ';
          case '1' :
            return 'warning';
          case '2' :
            return 'error';
        }
      }
    },
    computed: {
      monitoringEquipmentNo() {
        return this.$route.query.monitoringEquipmentNo;
      },
      towerCraneId() {
        return this.$route.query.towerCraneId;
      }
    }
  }
</script>

<style lang='scss' scoped>
  .monitor-detail-wrap{
    /*display: flex;*/
    .echarts {
      width: 100%;
      height: 100%;
    }
    .more-btn{
      background: none;
      color: #00b588;
      border-color: #00b588;
    }
    justify-content: space-between;
    width: 100%;
    padding: 0 20px 20px;
    box-sizing: border-box;
    font-size: 14px;
    /*height: 780px;*/
    .detail-header{
      width: 100%;
      font-size: 14px;
      color: #1ab394;
      line-height: 40px;
      border-bottom: 1px solid #E9E9E9;
    }
    .moni-box{
      padding: 10px 20px 20px;
      box-sizing: border-box;
      border: 1px solid #E9E9E9;
      margin-top: 20px;
      .legend-box{
        text-align: center;
        font-size: 12px;
        color: #333333;
        margin-top: -10px;
        margin-bottom: 16px;
        .legend-icon{
          display: inline-block;
          width: 10px;
          height: 10px;
          background: #aaaaaa;
          &.succ{
            background: #aaaaaa;
          }
          &.warn{
            background: #ffc267;
          }
          &.err{
            background: #ff813e;
          }
        }
      }
      .status-cont{
        display: flex;
        justify-content: space-between;
      }
      .chart-cont{
        width: 100%;
        height: 260px;
      }
    }
    .mt-cont{
      display: flex;
      flex-wrap: wrap;
      width: 100%;
      background: #eef8f6;
      padding: 10px 20px 20px;
      box-sizing: border-box;
      margin-top: 20px;
      .mt-top{
        display: flex;
        justify-content: flex-start;
        width: 100%;
        padding: 0 10px 10px;
        .mt-top-item{
          width: 25%;
          .r-top-l-text{
            margin: 0;
            padding-left: 10px;
            line-height: 28px;
          }
          .driver-avatar{
            display: block;
            width: 70px;
            height: 80px;
            margin-top: 20px;
            background: #eeeeee;
          }
        }
      }
      .mt-btm{
        width: 100%;
        display: flex;
        justify-content: space-between;
        .mt-btm-item{
          display: flex;
          align-items: center;
          justify-content: center;
          width: 25%;
          height: 88px;
          color: #696969;
          box-sizing: border-box;
          &.r-line{
            border-right: 1px solid #eeeeee;
          }
          .top-monitor-icon{
            font-size: 62px;
            margin-right: 45px;
            width: 62px;
            height: 62px;
            &.s-elevator{
              width: auto;
              height: 62px;
            }
          }
          .top-monitor-text{
            display: flex;
            flex-direction: column;
            text-align: center;
            b{
              color: #4c4e4d;
              font-size: 42px;
              .top-monitor-unit{
                color: #696969;
                font-size: 12px;
              }
            }
          }
        }
      }
      .r-mid{
        margin: 20px 0 0;
        padding: 0 10px 10px;
        border: 1px solid #e1ece8;
      }
      .r-btm{
        margin: 20px 0 0;
        padding: 0 10px 10px;
        border: 1px solid #e1ece8;
      }
    }
  }
</style>
