<template>
  <div class="stachart-wrap">
    <section class="tree-box">
      <organize-tree :projectId="projectId" @node-click="handleNodeClick"/>
    </section>
    <template v-if="type === 0">
      <section class="chart-box">
        <div class="chart lt-chart">
          <p class="chart-tit">监控设备分析</p>
          <div class="chart-cont">
            <chart
              ref="lt"
              :options="ltOptions"
              autoresize
            />
          </div>
        </div>
        <div class="chart rt-chart">
          <p class="chart-tit">设备告警分析</p>
          <div class="chart-cont">
            <chart
              ref="rt"
              :options="rtOptions"
              autoresize
            />
          </div>
        </div>
        <div class="chart lb-chart">
          <p class="chart-tit">项目告警分析</p>
          <div class="chart-cont">
            <chart
              ref="lb"
              :options="lbOptions"
              autoresize
            />
          </div>
        </div>
        <div class="chart rb-chart">
          <p class="chart-tit">报警项目排名</p>
          <div class="chart-cont">
            <chart
              ref="rb"
              :options="rbOptions"
              autoresize
            />
          </div>
        </div>
      </section>
    </template>
    <template v-else>
      <section class="chart-box">
        <proj-chart style="margin-top: -20px" :projectId="projectId"/>
      </section>
    </template>
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
  // import 'echarts/lib/component/dataset'
  import {
    listProject,
    getProjChild,
  } from "@/api/quality/measure";
  import {
    getEquipAnaE,
    alarmingAnaE,
    projAlarmAnaE,
    alarmRankE
  } from "@/api/smartMonitor/elevator";
  import OrganizeTree from "@/components/OrganizeTree/index";
  import ProjChart from "../../project/components/RecentWeekChart";
  const moment = extendMoment(Moment);
  const range7 = moment.range(moment().subtract(6, 'days'), moment());
  const recent7day = Array.from(range7.by('days')).map(m => m.format('MM-DD'))
  export default {
    name: "RecentWeekChart",
    components: {
      OrganizeTree,
      ProjChart,
      'chart': ECharts
    },
    data() {
      return {
        type: this.$store.state.user.selectRole.type,
        projectId: this.$store.state.user.selectRole.id,
        companyId: this.$store.state.user.selectRole.id,
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
            data: ['升降机设备', '监控设备', '监控使用率'],
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
              // "interval": 0,
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
            {"name": "升降机设备",
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
            {"name": "监控设备",
              "type": "bar",
              "stack": "总量",
              "barMaxWidth": 15,
              "itemStyle": {
                "normal": {
                  "color": "#9567f6",
                  "barBorderRadius": 0,
                }
              },
              data: [0, 0, 0, 0, 0, 0, 0]

            },
            {
              name:'监控使用率',
              yAxisIndex: 1,
              symbolSize:10,
              symbol:'circle',
              type:'line',
              "itemStyle": {
                "normal": {
                  "color": "#E976FF",
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
              // "interval": 0,
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
              data:[0,0,0,0,0,0,0]
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
              data:[0,0,0,0,0,0,0]
            },
          ]
        },
        lbOptions: {
          title: [{
            text: '预警项目占比',
            textStyle: {
              color: '#8a8f8f',
              fontSize: 14,
              fontWeight: 'normal'
            },
            left: '18%',
            bottom: '12%'
          },{
            text: '报警项目占比',
            textStyle: {
              color: '#8a8f8f',
              fontSize: 14,
              fontWeight: 'normal'
            },
            left: '70%',
            bottom: '12%'
          }],
          series: [
            {
              type: 'pie',
              radius: [40, 60],
              center: ['25%', '50%'],
              data: [{
                value: 100,
                itemStyle: {
                  color: '#ededed'
                },
                labelLine: {
                  show: false
                }
              },
                {
                  value: 0,
                  itemStyle: {
                    color: "transparent"
                  }
                }
              ]
            },
            {
              type: 'pie',
              radius: [40, 60],
              center: ['75%', '50%'],
              data: [{
                value: 100,
                itemStyle: {
                  color: '#ededed'
                },
                labelLine: {
                  show: false
                }
              },
                {
                  value: 0,
                  itemStyle: {
                    color: "transparent"
                  }
                }
              ]
            },
            {
              type: 'pie',
              radius: [40, 70],
              center: ['25%', '50%'],
              clockwise: false,
              data: [
                {
                  value: 90,
                  itemStyle: {
                    color: "transparent"
                  }
                },
                {
                  value: 10,
                  name: '预警项目数',
                  itemStyle: {
                    color: '#ffc267'
                  },
                  label: {
                    color: "#ffc267",
                    fontSize: 18,
                    show: true,
                    position: 'center',
                    formatter: '{d}%',
                  }
                }
              ]
            },
            {
              type: 'pie',
              radius: [40, 70],
              center: ['75%', '50%'],
              clockwise: false,
              data: [
                {
                  value: 90,
                  itemStyle: {
                    color: "transparent"
                  }
                },
                {
                  value: 10,
                  name: '报警项目数',
                  itemStyle: {
                    color: '#ff813e'
                  },
                  label: {
                    color: "#ff813e",
                    fontSize: 18,
                    show: true,
                    position: 'center',
                    formatter: '{d}%',
                  }
                }
              ]
            }
          ]
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
          "calculable": true,
          xAxis: [
            {
              type: 'value',
              name: '',
              min: 0,
              position: 'left',
              axisLine: {
                lineStyle: {
                  color: '#727575'
                }
              },
              axisTick: {
                show: true
              },
              splitLine: {
                show: false
              },
            }
          ],
          yAxis: [{
            type: "category",
            axisLine: {
              lineStyle: {
                color: '#727575'
              }
            },
            splitLine: {
              "show": false
            },
            axisTick: {
              "show": false
            },
            splitArea: {
              "show": false
            },
            axisLabel: {
              // interval: 0,
              textStyle:{
                fontSize:10,
                color: '#9ba3a3'
              }
            },
            data: ['项目1'],
          }],
          series: [
            {"name": "报警数量",
              "type": "bar",
              "stack": "总量",
              "barMaxWidth": 15,
              "barGap": "10%",
              "itemStyle": {
                "normal": {
                  "color": "#ff813e",
                }
              },
              data:[1]

            }]
        },
      }
    },
    created() {
      this.getEquipAnaE();
      this.alarmingAnaE();
      this.projAlarmAnaE();
      this.alarmRankE();
    },
    beforeDestroy() {
      console.log('销毁');
      this.$store.dispatch('SetSelectRole', this.$store.state.user.curRole)
    },
    methods: {
      getEquipAnaE() {
        getEquipAnaE(this.queryParams).then(res => {
          /**
           *       let Y1 = [], Y2 = [], Y3 = [];
           *       res.data.map(e => {
           *         Y1 = [...Y1, e.towerCraneEquipmentNum];
           *         Y2 = [...Y2, e.tonitoringEquipmentNum || 0];
           *         Y3 = [...Y3, e.monitoringOnlineRate];
           *       });
           *      this.ltOptions.series[0].data = Y1;
           *       this.ltOptions.series[1].data = Y2;
           *     this.ltOptions.series[2].data = Y3;
           */
          this.ltOptions.series[0].data = _.map(res.data, 'deviceNumber');
          this.ltOptions.series[1].data = _.map(res.data, 'totalMonitorDeviceNumber');
          this.ltOptions.series[2].data = res.data.map(e => (e.coverDeviceNumber*100/e.deviceNumber).toFixed(1))
        })
      },
      alarmingAnaE() {
        alarmingAnaE(this.queryParams).then(res => {
          this.rtOptions.series[0].data = _.map(res.data, 'preAlarmingNumber');
          this.rtOptions.series[1].data = _.map(res.data, 'alarmingNumber');
        })
      },
      projAlarmAnaE() {
        projAlarmAnaE(this.queryParams).then(res => {
          console.log(res)
          // console.log(res)
          this.lbOptions.series[2].data[0].value = 100-res.data.warningProjectPercent;
          this.lbOptions.series[2].data[1].value = res.data.warningProjectPercent;
          this.lbOptions.series[3].data[0].value = 100-res.data.alarmingProjectPercent;
          this.lbOptions.series[3].data[1].value = res.data.alarmingProjectPercent;
        })
      },
      alarmRankE() {
        alarmRankE(this.queryParams).then(res => {
          this.rbOptions.yAxis[0].data = _.map(res.data, 'projectName');
          this.rbOptions.series[0].data = _.map(res.data, 'alarmingNumber');
        })
      },
      /** *********************下方是左侧树相关的**************************/
      handleNodeClick(node) {
        this.type = node.type;
        if (node.type === 1) {
          this.projectId = node.id;
        } else {
          this.companyId = node.id;
          this.getEquipAnaE();
          this.alarmingAnaE();
          this.projAlarmAnaE();
          this.alarmRankE();
        }
      },
    },
    computed: {
      queryParams() {
        const params = {
          days: - 7,
          statisticsDate: moment(new Date()).format('yyyy-MM-DD')
        }
        this.type === 0 ? params.companyId = this.companyId : params.projectId = this.projectId;
        return {
          ...params
        }
      }
    }
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
        }
      }
    }
  }
</style>
