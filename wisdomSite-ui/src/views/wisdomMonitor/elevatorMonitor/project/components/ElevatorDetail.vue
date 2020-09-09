<template>
  <div class="monitor-detail-wrap">
    <div class="detail-header">
      <el-button type="text" @click="$router.go(-1)">实时监控 <i class="el-icon-arrow-right"></i></el-button>
      <template v-if="sn.length===2">
        <el-button type="text" @click="curSel=0" :style="`color: #000000 !important;${curSel===0?'border-bottom: 2px solid #1ab394':''}`">升降机左笼</el-button>
        <el-button type="text" @click="curSel=1" :style="`color: #000000 !important;${curSel===1?'border-bottom: 2px solid #1ab394':''}`">升降机右笼</el-button>
      </template>
      <template v-else>
        <el-button type="text" @click="curSel=0" :style="`color: #000000 !important;${curSel===0?'border-bottom: 2px solid #1ab394':''}`">升降机</el-button>
      </template>
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
          title="载重（t）"
          :min="0"
          :max="topData.maxWeight"
          :cur="topData.weight"
          :status="topData.systemStatusWeight|mapStatus"
        />
        <status-range
          title="载重比（%）"
          :min="0"
          :max="100"
          :cur="topData.weightPercent"
          :status="topData.systemStatusWeight|mapStatus"
        />
        <status-range
          title="高度（m）"
          :min="0"
          :max="topData.maxHeight"
          :cur="topData.height"
          :status="topData.systemStatusHeight|mapStatus"
        />
        <status-range
          title="速度（m/s）"
          :min="0"
          :max="topData.maxSpeed"
          :cur="topData.speed"
          :status="topData.systemStatusSpeed|mapStatus"
        />
        <status-range
          title="倾角（°）"
          :min="0"
          :max="topData.maxTilt"
          :cur="topData.tilt+'/'+topData.tilt2"
          :status="topData.systemStatusTilt|mapStatus"
        />
        <status-range
          title="前门关闭"
          min=""
          max=""
          cur=""
          :status="topData.foreDoorStatus|mapStatus"
        />
        <status-range
          title="后门关闭"
          min=""
          max=""
          cur=""
          :status="topData.behindDoorStatus|mapStatus"
        />
        <status-range
          title="身份认证状态"
          min=""
          max=""
          cur=""
          :status="`${topData.authen===0?'warning':''}`"
        />
      </div>
    </div>
    <div class="mid-top-box">
      <div class="mt-cont">
        <div class="mt-top">
          <div class="mt-top-item">
            <p class="split-tit">升降机信息 </p>
            <p class="r-top-l-text">备案号：{{elevInfo.recordNumber}}</p>
            <p class="r-top-l-text">塔机型号：{{elevInfo.models}}</p>
            <p class="r-top-l-text">产权单位：{{elevInfo.propertyUnit}}</p>
            <p class="r-top-l-text">安装单位：{{elevInfo.installationUnit}}</p>
            <p class="r-top-l-text">违章通知：{{elevInfo.violationNotice}}</p>
          </div>
          <div class="mt-top-item">
            <p class="split-tit">司机信息 </p>
            <p class="r-top-l-text">姓名：{{driverInfo.name}}</p>
            <p class="r-top-l-text">身份证：{{driverInfo.idNumber}}</p>
            <p class="r-top-l-text">特种作业证：{{driverInfo.specialOperationCertificate}}</p>
            <p class="r-top-l-text">身份确认时间：{{driverInfo.recognitionTime}}</p>
          </div>
          <div class="mt-top-item">
            <button type="button" @click="openHis()" class="more-btn jl-btn"><span>历史记录</span></button>
            <img class="driver-avatar" :src="driverInfo.faceUrl" alt="">
          </div>
          <div class="mt-top-item">

          </div>
        </div>
        <div class="mt-btm">
          <div class="mt-btm-item">
            <img class="top-monitor-icon s-elevator" src="~@/assets/image/cage-icon.png">
            <p class="top-monitor-text">
              <b>{{yeToData.yesterday.totalNumber||0}}<span class="top-monitor-unit">次</span></b>
              <span>昨日装载次数</span>
            </p>
          </div>
          <div class="mt-btm-item r-line">
            <img class="top-monitor-icon s-elevator" src="~@/assets/image/s_alarm.png">
            <p class="top-monitor-text">
              <b>{{yeToData.yesterday.alarmingNumber||0}}<span class="top-monitor-unit">次</span></b>
              <span>昨日违章次数</span>
            </p>
          </div>
          <div class="mt-btm-item">
            <img class="top-monitor-icon s-elevator" src="~@/assets/image/cage-icon.png">
            <p class="top-monitor-text">
              <b>{{yeToData.today.totalNumber||0}}<span class="top-monitor-unit">次</span></b>
              <span>今日装载次数</span>
            </p>
          </div>
          <div class="mt-btm-item">
            <img class="top-monitor-icon s-elevator" src="~@/assets/image/s_alarm.png">
            <p class="top-monitor-text">
              <b>{{yeToData.today.alarmingNumber||0}}<span class="top-monitor-unit">次</span></b>
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
          <el-radio :label="7">近7日</el-radio>
          <el-radio :label="15">近15日</el-radio>
          <el-radio :label="30">近30日</el-radio>
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
      :sn="curSn"
    />
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
    getElevInfo,
    getDriverInfo,
    getYeAndToInfo,
    getDevStatusAna,
    getDevCycleAna,
    getTopRtData,
    getRecentWz
  } from "@/api/smartMonitor/elevator";
  import StatusRange from "@/components/StatusRange/index";
  import HistoryDialog from "./HistoryDialog";
  const moment = extendMoment(Moment);
  const range7 = moment.range(moment().subtract(29, 'days'), moment());
  const recent30day = Array.from(range7.by('days')).map(m => m.format('MM/DD'))

  export default {
    name: "ElevatorDetail",
    components: {
      HistoryDialog,
      StatusRange,
      ECharts
    },
    data() {
      return {
        dialogFlag: false,
        sn: [],
        curSel: 0,
        statusAnaNum: 20,
        workCycleNum: 7,
        topData: {},
        driverInfo: {},
        elevInfo: {},
        yeToData: {
          yesterday: {
            totalNumber: 0,
            alarmingNumber: 0
          },
          today: {
            totalNumber: 0,
            alarmingNumber: 0
          }
        },
        btmList: [],
        queryParams: {
          projectId: this.$store.state.user.curRole.id,
        },
        detailDialogFlag: false,
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
            data: ['装载重量', '载重百分比'],
            orient: 'horizontal',
            icon: "rect",
            show: true,
            position: 'center',
            itemWidth: 12,
            itemHeight: 12
          },
          // dataZoom: {
          //   show: true,
          //   bottom: 0,
          // },
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
            name: '单位：kg',
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
              name:'装载重量',
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
            data: ['装载次数', '违章次数'],
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
            {"name": "装载次数",
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
      this.sn = this.$route.query.sn.split(',')
      this.getTopRtData();
      this.getDetailData();
      this.getDriverInfo();
      this.getYeAndToInfo();
      this.getDevStatusAna();
      this.getDevCycleAna();
      this.getRecentWz();
    },
    methods: {
      openHis() {
        this.dialogFlag = true;
      },
      async getTopRtData() {
        const { data:detailData } = await getTopRtData(this.curSn);
        this.topData = detailData;
      },
      async getDetailData() {
        const { data:detailData } = await getElevInfo(this.$route.query.id);
        this.elevInfo = detailData;
      },
      async getDriverInfo() {
        const { data } = await getDriverInfo(this.curSn);
        this.driverInfo = data;
      },
      async getYeAndToInfo() {
        const { data } = await getYeAndToInfo(this.curSn);
        this.yeToData = data;
      },
      async getDevStatusAna() {
        const { data } = await getDevStatusAna(this.curSn, this.statusAnaNum);
        this.mbOptions.xAxis[0].data = data.map(e => moment(e.insertTime).format('YYYY-MM-DD HH:mm:ss'));
        this.mbOptions.series[0].data = data.map(e => e.weight);
        this.mbOptions.series[1].data = data.map(e => e.weightPercent);
        // this.yeToData = data;
      },
      async getDevCycleAna() {
        const { data } = await getDevCycleAna(this.curSn, this.workCycleNum);
        this.btmOptions.xAxis[0].data = data.map(e => moment(e.statisticsDate).format('MM-DD'));
        this.btmOptions.series[0].data = data.map(e => e.totalNumber);
        this.btmOptions.series[1].data = data.map(e => e.alarmingNumber);
        // this.yeToData = data;
      },
      async getRecentWz() {
        const { data:detailData } = await getRecentWz(this.curSn);
        this.topData = detailData;
      },
    },
    filters: {
      mapStatus(val) {
        switch (val) {
          case 0 :
            return 'succ';
          case 1 :
            return 'warning';
          case 2 :
            return 'error';
        }
      }
    },
    computed: {
      curSn() {
        return this.sn[this.curSel]
      }
    },
    watch: {
      curSel(val) {
        this.getTopRtData();
        // this.getDetailData();
        this.getDriverInfo();
        this.getYeAndToInfo();
        this.getDevStatusAna();
        this.getDevCycleAna();
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
