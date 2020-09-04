<template>
  <div class="stachart-wrap">
    <section class="tree-box">
      <organize-tree @node-click="handleNodeClick"/>
    </section>
    <template v-if="type===0">
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
          <p class="chart-tit">设备违章分析</p>
          <div class="chart-cont">
            <chart
              ref="rt"
              :options="rtOptions"
              autoresize
            />
          </div>
        </div>
        <div class="chart lb-chart">
          <p class="chart-tit">项目违章排名</p>
          <div class="chart-cont">
            <chart
              ref="lb"
              :options="lbOptions"
              autoresize
            />
          </div>
        </div>
        <div class="chart rb-chart">
          <p class="chart-tit">项目违章占比</p>
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
  // import 'echarts/lib/chart/scatter'
  // import 'echarts/lib/chart/effectScatter'
  // import 'echarts/lib/component/tooltip'
  // import 'echarts/lib/component/geo'
  import 'echarts/lib/component/legend'
  import 'echarts/lib/component/title'
  // import 'echarts/lib/component/dataset'
  import {
    listProject,
    getProjChild,
  } from "@/api/quality/measure";
  import {
    getEquipAnaE,
    getEquipViolAnaE,
    getViolationsRankE,
    getViolationsPerE
  } from "@/api/smartMonitor/tower";
  import OrganizeTree from "@/components/OrganizeTree/index";
  import ProjChart from "../../project/components/RecentMonthChart";
  const moment = extendMoment(Moment);
  const range7 = moment.range(moment().subtract(29, 'days'), moment());
  const recent30day = Array.from(range7.by('days')).map(m => m.format('MM/DD'))
  export default {
    name: "RecentMonthChart",
    components: {
      'chart': ECharts,
      OrganizeTree,
      ProjChart,
    },
    data() {
      return {
        type: this.$store.state.user.curRole.type,
        projectId: this.$store.state.user.curRole.id,
        companyId: this.$store.state.user.curRole.id,
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
            data: ['塔吊设备', '监控设备', '监控设备使用率'],
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
            nameRotate: 90,
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
              interval: 'auto',
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
              minInterval: 1,
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
            {"name": "塔吊设备",
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
              name:'监控设备使用率',
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
            data: ['违章次数'],
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
              interval: 'auto',
              // rotate: 30,
              textStyle:{
                fontSize:10,
                color: '#9ba3a3',
              }
            },
            "data": recent30day,
          }],
          yAxis: [
            {
              type: 'value',
              name: '单位：次',
              min: 0,
              minInterval: 1,
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
            {"name": "违章次数",
              "type": "bar",
              "stack": "总量",
              "barMaxWidth": 15,
              "barGap": "10%",
              "itemStyle": {
                "normal": {
                  "color": "#f05454",
                }
              },
              data:[0,0,0,0,0,0,0]

            }]
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
            {"name": "违章次数",
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
        rbOptions: {
          color: ['#73ddff', '#f05454'],
          tooltip: {
            trigger: 'item'
          },
          legend: {
            data: ['项目总数', '违章项目'],
            orient: 'horizontal',
            icon: "rect",
            show: true,
            right: 20,
            top: 0,
            itemWidth: 12,
            itemHeight: 12
          },
          series: [{
            type: 'pie',
            center: ['50%', '50%'],
            radius: ['44%', '65%'],
            clockwise: true,
            avoidLabelOverlap: true,
            hoverOffset: 15,
            label: {
              show: true,
              position: 'outside',
              formatter: '{a|{b}：{d}%}\n{hr|}',
              rich: {
                hr: {
                  backgroundColor: 't',
                  borderRadius: 3,
                  width: 3,
                  height: 3,
                  padding: [3, 3, 0, -12]
                },
                a: {
                  padding: [-30, 15, -20, 15]
                }
              }
            },
            labelLine: {
              normal: {
                length: 20,
                length2: 30,
                lineStyle: {
                  width: 1
                }
              }
            },
            data: [
              {value: 0, name: '项目总数'},
              {value: 0, name: '违章项目'},
            ],
          }]
        },
        keyWords: '',
        queryParams: {
          companyId: this.$store.state.user.curRole.id,
          days: 30,
        }
      }
    },
    created() {
      this.getEquipAnaE();
      this.getEquipViolAnaE();
      this.getViolationsRankE();
      this.getViolationsPerE();
    },
    methods: {
      getEquipAnaE() {
        getEquipAnaE(this.queryParams).then(res => {
          this.ltOptions.series[0].data = res.data.map(e => e.towerCraneEquipmentNum || 0)
          this.ltOptions.series[1].data = res.data.map(e => e.towerCraneMonitoringEquipmentNum || 0)
          this.ltOptions.series[2].data = res.data.map(e => e.monitoringOnlineRate || 0)
        })
      },
      getEquipViolAnaE() {
        getEquipViolAnaE(this.queryParams).then(res => {
          this.rtOptions.series[0].data = _.map(res.data, 'violationsNum');
        })
      },
      getViolationsRankE() {
        getViolationsRankE(this.queryParams).then(res => {
          console.log(res)
          //TODO 这里需要有数据才能对接
          this.lbOptions.yAxis[0].data = _.map(res.data, 'projectName');
          this.lbOptions.series[0].data = _.map(res.data, 'projectNumber');
          // this.rtOptions.series[0].data = _.map(res.data, 'equipmentViolationNum');
        })
      },
      getViolationsPerE() {
        getViolationsPerE(this.queryParams).then(res => {
          this.rbOptions.series[0].data = [
            {value: res.data.projectNumber||0, name: '项目总数'},
            {value: res.data.proportionProjectViolation||0, name: '违章项目'}]
        })
      },
      handleNodeClick(node) {
        this.type = node.type;
        if (node.type === 1) {
          this.projectId = node.id;
        } else {
          this.queryParams.companyId = node.id;
          this.getEquipAnaE();
          this.getEquipViolAnaE();
          this.getViolationsRankE();
          this.getViolationsPerE();
        }
      },
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
