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
        <p class="chart-tit">设备违章</p>
        <div class="chart-cont">
          <chart
            ref="rt"
            :options="rtOptions"
            autoresize
          />
        </div>
      </div>
      <div class="chart lb-chart">
        <p class="chart-tit">设备工作记录</p>
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
  </div>
</template>

<script>
  import Moment from 'moment';
  import { extendMoment } from 'moment-range';
  import ECharts from 'vue-echarts'
  import 'echarts/lib/chart/bar'
  import 'echarts/lib/chart/line'
  import 'echarts/lib/chart/pie'
  import 'echarts/lib/chart/map'
  // import 'echarts/lib/chart/scatter'
  // import 'echarts/lib/chart/effectScatter'
  import 'echarts/lib/component/tooltip'
  // import 'echarts/lib/component/geo'
  import 'echarts/lib/component/legend'
  import 'echarts/lib/component/title'
  // import 'echarts/lib/component/dataset'
  import {
    getEquipAnaE,
    getEquipViolAnaE,
    getViolationsRankE,
    getViolationsPerE
  } from "@/api/smartMonitor/tower";
  const moment = extendMoment(Moment);
  const range7 = moment.range(moment().subtract(6, 'days'), moment());
  const recent7day = Array.from(range7.by('days')).map(m => m.format('MM-DD'))
  export default {
    name: "RecentChart",
    components: {
      'chart': ECharts
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
          tooltip: {
            trigger: "axis",
            axisPointer: {
              type: "shadow",
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
            data: ['塔吊设备', '监控设备', '监控在线率'],
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
              data:[421,356,719,658,458,443,620,416,551,721,466,632]

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
              data:[121,256,119,258,358,343,220,216,151,221,266,532]

            },
            {
              name:'监控在线率',
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
              data:[11,4,7,8,6,2,10,8,10,5,5,4]
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
              data:[421,356,719,658,458,443,620,416,551,721,466,632]

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
              interval: 0,
              textStyle:{
                fontSize:10,
                color: '#9ba3a3'
              }
            },
            data: ['项目1', '项目2', '项目3'],
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
              data:[1, 32, 18]

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
              {value: 123, name: '项目总数'},
              {value: 102, name: '违章项目'},
            ],
          }]
        },
        keyWords: '',
        queryParams: {
        }
      }
    },
    methods: {
      loadNode(node, resolve) {
        if (node.level === 0) {
          this.getProjectList(resolve);
        }
        if (node.level === 1) {
          this.getArea(node, resolve);
        }
        if (node.level === 2) {
          let newData = node.data.children.map(item => ({
            ...item,
            leaf: true
          }));
          return resolve(newData)
        }
        if (node.level === 3) {
          return resolve([])
        }
      },
      //获取所有的项目列表
      getProjectList(resolve) {
        listProject().then(res => {
          let newData = res.rows.map(item => ({
            ...item,
            regionName: item.projectName,
          }));
          resolve(newData);
        }).catch(err => {
          this.msgError(err)
        })
      },
      //获取项目下的区域树
      getArea(node, resolve) {
        getProjChild(node.data.id).then(res => {
          resolve(res.data)
        })
      },
      handleNodeClick(data, node, ele) {
        // console.log(data, node, ele)
        this.queryParams.projectId = data.id
        this.getMeasureList()
      },
      renderContent(h, { node, data, store }) {
        // if(node.level === 3) {
        //   return (
        //     <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
        //     <span>
        //     <span>{node.label}</span>
        //     </span>
        //     <span>
        //     <el-button style="font-size: 12px;" type="text" on-click={ () => this.append(data) }>新增实测部位 </el-button>
        //   </span>
        //   </span>);
        // }
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
