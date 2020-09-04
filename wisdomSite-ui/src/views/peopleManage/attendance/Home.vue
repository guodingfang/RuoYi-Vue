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
      <div class="data-name">项目</div>
      <div class="data-val">{{overview.projectNumber}}</div>
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
    :dialogFlag.sync="allDialogFlag"
    :allData="allData"
  />
</div>
</template>

<script>
  import moment from 'moment';
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
    getAttChartData,
    getZc10Data,
    getZc10AllData,
    getXsAttData,
  } from '@/api/people/attendance'
  export default {
    name: "Home",
    components: {
      'chart': ECharts,
      SeeAllDailog
    },
    data() {
      return {
        allDialogFlag: false,
        allData: [],
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
        },
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
            text: "近7日人员出勤折线图",
            left: "18px",
            top: "20",
            textStyle: {
              color: "#333333",
              fontSize: 16,
              fontWeight: '600'
            }
          },
          color: ['#73DEB3', '#FFB761'],
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
            data: ['在场', '出勤'],
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
              name: '在场',
              type: 'line',
              data: [13,54,34,344,35,53]
            },
            {
              name: '出勤',
              type: 'line',
              data: [13,2,2,32,123,23,136]
            }
          ]
        },
        rtOptions: {
          title: {
            text: "各项目在场人员占比",
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
                  fontSize: '16',
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
            text: "下属项目出勤统计",
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
      async getAttChartData() {
        try {
          const { data } = await getAttChartData();
          let ltX = [], zcY = [], cqY = [];
          data.map(ele => {
            ltX.push(moment(ele.bePresentORattendanceTodayTime).format("MM-DD"));
            zcY.push(ele.bePresentNumber || 0);
            cqY.push(ele.attendanceTodayNumber || 0)
          })
          this.ltOptions.xAxis.data = ltX;
          this.ltOptions.series[0].data = zcY;
          this.ltOptions.series[1].data = cqY;
        } catch (e) {
          console.log(e);
        }
      },
      //获取前十
      async getZc10Data() {
        try {
          const { data } = await getZc10Data();
          let rtData = [];
          data.map(ele => {
            rtData.push({
              name: ele.projectName,
              value: ele.personnelProjectProportion
            });
          })
          this.rtOptions.series[0].data = rtData;
        } catch (e) {
          console.log(e);
        }
      },
      //获取下属项目出勤
      async getXsAttData() {
        try {
          const { data } = await getXsAttData(this.queryDate);
          this.btmOptions.xAxis[0].data = _.map(data, 'projectName');
          this.btmOptions.series[0].data = _.map(data, 'sceneNumber'); //现场
          this.btmOptions.series[1].data = _.map(data, 'attendanceNumber'); //出勤
          this.btmOptions.series[2].data = _.map(data, 'presenceNumber'); //在场
          // console.log
          // this.overview = data
        } catch (e) {
          console.log(e);
        }
      },
      async seeAll() {
        try {
          const { data } = await getZc10AllData();
          this.allData = data;
          this.allDialogFlag = true;
        } catch (e) {
          console.log(e);
        }
      }
    },
    created() {
      this.getOverview();
      this.getAttChartData();
      this.getZc10Data();
      this.getXsAttData();
    },
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
      &.lt-chart{
        width: 65%;
      }
      &.rt-chart{
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
