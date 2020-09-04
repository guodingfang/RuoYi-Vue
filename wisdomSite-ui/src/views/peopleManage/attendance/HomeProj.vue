<template>
<div class="chart-wrap">
  <div class="overview">
    <div class="data-item">
      <div class="data-name">现场(人)</div>
      <div class="data-val">{{overview.onDuty}}</div>
    </div>
    <div class="data-item">
      <div class="data-name">今日出勤(人)</div>
      <div class="data-val">{{overview.attendanceToday}}</div>
    </div>
    <div class="data-item r-line">
      <div class="data-name">今日出勤率</div>
      <div class="data-val">{{overview.attendanceTodayRate}}</div>
    </div>
    <div class="data-item">
      <div class="data-name">在场(人)</div>
      <div class="data-val">{{overview.bePresent}}</div>
    </div>
    <div class="data-item">
      <div class="data-name">在册(人)</div>
      <div class="data-val">{{overview.registered}}</div>
    </div>
    <div class="data-item">
      <div class="data-name">参建单位(家)</div>
      <div class="data-val">{{overview.participatingUnitNumber}}</div>
    </div>
  </div>
  <div class="chart-box">
    <div class="chart lt-chart">
      <chart
        ref="lt"
        :options="ltOptions"
        autoresize
      />
    </div>
    <div class="chart rt-chart">
      <chart
        ref="rt"
        :options="rtOptions"
        autoresize
      />
      <div class="look-all" @click="seeAll">查看全部</div>
    </div>
    <div class="chart lm-chart">
      <chart
        ref="lm"
        :options="lmOptions"
        autoresize
      />
    </div>
    <div class="chart rm-chart">
      <chart
        ref="rm"
        :options="rmOptions"
        autoresize
      />
      <div class="worker-type-list">
        <div :class="workerType===1?'type-item active':'type-item'" @click="workerType=1">地区</div>
        <div :class="workerType===2?'type-item active':'type-item'" @click="workerType=2">年龄</div>
        <div :class="workerType===3?'type-item active':'type-item'" @click="workerType=3">性别</div>
        <div :class="workerType===4?'type-item active':'type-item'" @click="workerType=4">工种</div>
      </div>
      <div class="look-all" @click="seeAllWorker">查看全部</div>
    </div>
    <div class="chart btm-chart">
      <el-date-picker
        class="sel-date"
        v-model="queryDate"
        value-format="yyyy-MM-dd"
        :picker-options="pickerOptions"
        :clearable="false"
        @change="getXsAttData"
        type="date">
      </el-date-picker>
      <chart
        ref="btm"
        :options="btmOptions"
        autoresize
      />
    </div>
  </div>
  <SeeAllDailog
    :title="dialogTitle"
    :dialogFlag.sync="allDialogFlag"
    :allData="allData"
  />
</div>
</template>

<script>
  import Moment from 'moment';
  import { extendMoment } from 'moment-range';
  import _ from 'lodash';
  import ECharts from 'vue-echarts'
  import 'echarts/lib/chart/bar'
  import 'echarts/lib/chart/line'
  import 'echarts/lib/chart/pie'
  import 'echarts/lib/chart/map'
  import 'echarts/lib/component/legend'
  import 'echarts/lib/component/title'
  import SeeAllDailog from "./components/SeeAllDailog";
  import {
    getOverview,
    getTrendData,
    getProjZc10Data,
    getProjZc10AllData,
    getProjAttChartData,
    getProjWork10ChartData,
    getProjWork10AllData,
    getProjXsAttData,
  } from '@/api/people/attendance';
  const moment = extendMoment(Moment);
  const range7 = moment.range(moment().subtract(6, 'days'), moment());
  const recent7day = Array.from(range7.by('days')).map(m => m.format('MM-DD'))
  const hourArr = ['01:00','02:00','03:00','04:00','05:00','06:00','07:00','08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00','24:00']
  export default {
    name: "HomeProj",
    components: {
      'chart': ECharts,
      SeeAllDailog
    },
    data() {
      return {
        dialogTitle: '现场工种',
        allDialogFlag: false,
        allData: [],
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
        },
        workerType: 1, //type（1:地区;2:年龄；3：性别；4：工种
        queryDate: moment().format("yyyy-MM-DD"),
        overview: {
          onDuty: 0,
          attendanceToday: 0,
          attendanceTodayRate: 0,
          bePresent: 0,
          registered: 0,
          participatingUnitNumber: 0,
        },
        ltOptions: {
          backgroundColor: '#fff',
          title: {
            text: "现场人数变化趋势图",
            left: "18px",
            top: "20",
            textStyle: {
              color: "#333333",
              fontSize: 16,
              fontWeight: '600'
            }
          },
          color: ['#527BFF', '#FFB761'],
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999'
              },
              lineStyle: {
                type: 'dashed'
              }
            }
          },
          grid: {
            left: '25',
            right: '25',
            bottom: '24',
            top: '75',
            containLabel: true
          },
          legend: {
            data: ['管理人员', '建筑工人'],
            orient: 'horizontal',
            icon: "rect",
            show: true,
            right: 20,
            top: 25,
            itemWidth: 12,
            itemHeight: 12
          },
          xAxis: {
            type: 'category',
            data: ['01-01', '01-02', '01-03', '01-04', '01-05', '01-06', '01-07'],
            splitLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            },
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              color: '#999',
              textStyle: {
                fontSize: 12
              },
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#F3F4F4'
              }
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            },
          },
          series: [
            {
              name: '管理人员',
              type: 'line',
              smooth: true,
              data: []
            },
            {
              name: '建筑工人',
              type: 'line',
              smooth: true,
              data: []
            }
          ]
        },
        rtOptions: {
          title: {
            text: "现场工种（前10）",
            left: "18px",
            top: "20",
            textStyle: {
              color: "#333333",
              fontSize: 16,
              fontWeight: '600'
            }
          },
          tooltip: {
            trigger: 'item',
            formatter: '{b}: &emsp;<br/>{c}人 占比{d}%'
          },
          color: ['#59BE9A', '#FF8352', '#E271DE', '#F8456B', '#00FFFF', '#4AEAB0'],
          series: [
            {
              name: '',
              type: 'pie',
              radius: ['55%', '70%'],
              avoidLabelOverlap: false,
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '30',
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: [
                {value: 335, name: ''},
                {value: 310, name: ''},
                {value: 234, name: ''},
                {value: 1548, name: ''}
              ]
            }
          ]
        },
        lmOptions: {
          backgroundColor: '#fff',
          title: {
            text: "近7日人员折线图",
            left: "18px",
            top: "20",
            textStyle: {
              color: "#333333",
              fontSize: 16,
              fontWeight: '600'
            }
          },
          color: ['#527BFF', '#FFB761'],
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999'
              },
              lineStyle: {
                type: 'dashed'
              }
            }
          },
          grid: {
            left: '25',
            right: '25',
            bottom: '24',
            top: '75',
            containLabel: true
          },
          legend: {
            data: ['管理人员', '建筑工人'],
            orient: 'horizontal',
            icon: "rect",
            show: true,
            right: 20,
            top: 25,
            itemWidth: 12,
            itemHeight: 12
          },
          xAxis: {
            type: 'category',
            data: ['01-01', '01-02', '01-03', '01-04', '01-05', '01-06', '01-07'],
            splitLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            },
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              color: '#999',
              textStyle: {
                fontSize: 12
              },
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#F3F4F4'
              }
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            },
          },
          series: [
            {
              name: '管理人员',
              type: 'line',
              data: [13,54,34,344,35,53]
            },
            {
              name: '建筑工人',
              type: 'line',
              data: [13,2,2,32,123,23,136]
            }
          ]
        },
        rmOptions: {
          title: {
            text: "建筑工人分析（前10）",
            left: "18px",
            top: "20",
            textStyle: {
              color: "#333333",
              fontSize: 16,
              fontWeight: '600'
            }
          },
          tooltip: {
            trigger: 'item',
            formatter: '{b}: &emsp;<br/>{c}人 占比{d}%'
          },
          color: ['#59BE9A', '#FF8352', '#E271DE', '#F8456B', '#00FFFF', '#4AEAB0'],
          series: [
            {
              name: '访问来源',
              type: 'pie',
              radius: ['55%', '70%'],
              avoidLabelOverlap: false,
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '30',
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: [
                {value: 335, name: ''},
                {value: 310, name: ''},
                {value: 234, name: ''},
                {value: 1548, name: ''}
              ]
            }
          ]
        },
        btmOptions: {
          title: {
            text: this.$store.state.user.curRole.label+"下属参建单位统计",
            left: "18px",
            top: "20",
            textStyle: {
              color: "#333333",
              fontSize: 16,
              fontWeight: '600'
            }
          },
          backgroundColor: '#fff',
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: ['现场', '出勤', '在场'],
            orient: 'horizontal',
            icon: "rect",
            show: true,
            right: 260,
            top: 16,
            itemWidth: 12,
            itemHeight: 12
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '25',
            containLabel: true
          },
          xAxis: [{
            type: 'category',
            data: [],
            splitLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            },
          }],
          dataZoom: [{
            "show": true,
            "height": 12,
            "xAxisIndex": [
              0
            ],
            bottom:'10',
            "start": 10,
            "end": 90,
            handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
            handleSize: '110%',
            handleStyle:{
              color:"#d3dee5",

            },
            textStyle:{
              color:"#fff"},
            borderColor:"#90979c"
          }, {
            "type": "inside",
            "show": true,
            "height": 15,
            "start": 1,
            "end": 35
          }],
          yAxis: {
            type: 'value',
            axisLabel: {
              color: '#999',
              textStyle: {
                fontSize: 12
              },
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#F3F4F4'
              }
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            },
          },
          series: [{
            name: '现场',
            type: 'bar',
            data: [],
            barWidth: 30, //柱子宽度
            // barGap: 10, //柱子之间间距
            itemStyle: {
              normal: {
                color: 'rgb(99, 204, 161)',
                opacity: 1,
              }
            }
          }, {
            name: '出勤',
            type: 'bar',
            data: [],
            barWidth: 30,
            // barGap: 10,
            itemStyle: {
              normal: {
                color: 'rgb(91, 190, 202)',
                opacity: 1,
              }
            }
          }, {
            name: '在场',
            type: 'bar',
            data: [],
            barWidth: 30,
            // barGap: 10,
            itemStyle: {
              normal: {
                color: 'rgb(82, 168, 222)',
                opacity: 1,
              }
            }
          }]
        }
      }
    },
    methods: {
      async getOverview() {
        try {
          const { data } = await getOverview();
          this.overview = data
        } catch (e) {
          console.log(e);
        }
      },
      //左上角
      async getLtChartData() {
        try {
          const { data } = await getTrendData();
          let ltX = [], zcY = [], cqY = [];
          data.map(ele => {
            // ltX.push(moment(new Date(ele.bePresentORattendanceTodayTime)).format("HH:mm"));
            zcY.push(ele.managementNumber || 0);
            cqY.push(ele.constructionWorkerNumber || 0)
          })
          this.ltOptions.xAxis.data = hourArr;
          this.ltOptions.series[0].data = zcY;
          this.ltOptions.series[1].data = cqY;
        } catch (e) {
          console.log(e);
        }
      },
      //右上角
      async getRtChartData() {
        try {
          const { data } = await getProjZc10Data();
          let rtData = [];
          data.map(ele => {
            rtData.push({
              name: ele.workName,
              value: ele.numberOfJobs
            });
          })
          this.rtOptions.series[0].data = rtData;
        } catch (e) {
          console.log(e);
        }
      },
      //中间左边
      async getLmChartData() {
        try {
          const { data } = await getProjAttChartData();
          let ltX = [], zcY = [], cqY = [];
          data.map(ele => {
            ltX.push(moment(new Date(ele.bePresentORattendanceTodayTime)).format("HH:mm"));
            zcY.push(ele.sevenDaysManagementNumber || 0);
            cqY.push(ele.sevenDaysConstructionWorkerNumber || 0)
          })
          this.lmOptions.xAxis.data = recent7day;
          this.lmOptions.series[0].data = zcY;
          this.lmOptions.series[1].data = cqY;
          // this.rtOptions.series[0].data = rtData;
        } catch (e) {
          console.log(e);
        }
      },
      //中间右边
      async getRmChartData() {
        try {
          const { data } = await getProjWork10ChartData({type: this.workerType});
          console.log(data)
          let rmData = [];
          data.map(ele => {
            rmData.push({
              name: ele.professionName,
              value: ele.professionNumber
            });
          })
          this.rmOptions.series[0].data = rmData;
        } catch (e) {
          console.log(e);
        }
      },
      //获取下属项目出勤
      async getXsAttData() {
        try {
          const { data } = await getProjXsAttData(this.queryDate);
          this.btmOptions.xAxis[0].data = _.map(data, 'unitName');
          this.btmOptions.series[0].data = _.map(data, 'sceneNumber'); //现场
          this.btmOptions.series[1].data = _.map(data, 'attendanceNumber'); //出勤
          this.btmOptions.series[2].data = _.map(data, 'presenceNumber'); //在场
        } catch (e) {
          console.log(e);
        }
      },
      async seeAll() {
        try {
          const { data } = await getProjZc10AllData();
          this.allData = data;
          console.log(data)
          this.dialogTitle = '现场工种';
          this.allDialogFlag = true;
        } catch (e) {
          console.log(e);
        }
      },
      async seeAllWorker() {
        try {
          const { data } = await getProjWork10AllData({type: this.workerType});
          this.allData = data.map( item => {
            return {
              projectName: item.professionName,
              personnelProjectProportion: item.professionNumber,
              proportion: item.proportion
            }
          });
          this.dialogTitle = '建筑工人分析';
          this.allDialogFlag = true;
        } catch (e) {
          console.log(e);
        }
      }
    },
    created() {
      this.getOverview();
      this.getLtChartData();
      this.getRtChartData();
      this.getLmChartData();
      this.getRmChartData();
      this.getXsAttData();
    },
    watch: {
      workerType() {
        this.getRmChartData();
      }
    }
  }
</script>

<style scoped lang="scss">
.chart-wrap{
  .echarts {
    width: 100%;
    height: 100%;
  }
  .overview{
    display: flex;
    margin-bottom: 20px;
    .data-item{
      width: 15%;
      text-align: center;
      padding: 0 20px;
      margin-top: 20px;
      &.r-line{
        border-right: 1px solid #e9f2ef;
      }
      .data-name{
        font-size: 14px;
        margin-bottom: 15px;
      }
      .data-val{
        font-size: 22px;
      }
    }
  }
  .chart-box{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    .chart{
      height: 352px;
      border: 1px solid #e9f2ef;
      position: relative;
      .sel-date{
        position: absolute;
        right: 20px;
        top: 10px;
        z-index: 2;
      }
      .look-all{
        color: #09a17b;
        font-size: 14px;
        cursor: pointer;
        position: absolute;
        width: 100%;
        text-align: center;
        bottom: 20px;
      }
      .worker-type-list{
        width: 180px;
        position: absolute;
        top: 20px;
        right: 0;
        .type-item{
          display: inline-block;
          font-size: 12px;
          padding: 2px 5px;
          border-radius: 10px;
          cursor: pointer;
          &.active{
            background: #00b588;
            color: #ffffff;
          }
        }
      }
      &.lt-chart{
        width: 65%;
      }
      &.rt-chart{
        width: calc(35% - 20px);
      }
      &.lm-chart{
        margin-top: 20px;
        width: 65%;
      }
      &.rm-chart{
        margin-top: 20px;
        width: calc(35% - 20px);
      }
      &.btm-chart{
        margin-top: 20px;
        width: 100%;
        height: 440px;
      }
    }
  }
}
</style>
