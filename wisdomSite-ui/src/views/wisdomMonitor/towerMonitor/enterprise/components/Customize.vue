<template>
  <div class="stachart-wrap">
    <section class="tree-box">
      <organize-tree @node-click="handleNodeClick"/>
    </section>
    <template v-if="type === 0">
      <section class="chart-box">
        <div class="chart lt-chart">
          <p class="chart-tit">监控设备分析</p>
          <div class="chart-cont analyse">
            <div class="ana-l">
              <img src="~@/assets/image/tower.png" alt="">
              <p>
                <b><span class="num">{{topData.towerCraneOnSiteNum||0}}</span>台</b>
                <span class="ana-tit">在场塔吊数量</span>
              </p>
            </div>
            <div class="ana-r">
              <div class="ana-item">
                <i class="ana_item_icon cam"></i>
                <p>
                  <b><span class="num">{{topData.tonitoringEquipmentNum||0}}</span>台</b>
                  <span>监控设备数量</span>
                </p>
              </div>
              <div class="ana-item">
                <i class="ana_item_icon pie"></i>
                <p>
                  <b><span class="num">{{topData.monitorOnLineRate||0}}</span>%</b>
                  <span>监测设备使用率</span>
                </p>
              </div>
            </div>
          </div>
        </div>
        <div class="chart rt-chart">
          <p class="chart-tit">设备违章分析</p>
          <div class="chart-cont analyse">
            <div class="chart-cont analyse">
              <div class="ana-l">
                <img src="~@/assets/image/alarm.png" alt="">
                <p class="">
                  <b><span class="num">{{topData.equipmentViolationRate||0}}</span>%</b>
                  <span>违章率</span>
                </p>
              </div>
              <div class="ana-r">
                <div class="ana-item">
                  <i class="ana_item_icon hook"></i>
                  <!--                <img class="hook" src="~@/assets/image/hook.png" alt="">-->
                  <p>
                    <b><span class="num">{{topData.equipmentHoistingNum||0}}</span>次</b>
                    <span>吊装次数</span>
                  </p>
                </div>
                <div class="ana-item">
                  <i class="ana_item_icon e_hook"></i>
                  <!--                <img class="e_hook" src="~@/assets/image/e_hook.png" alt="">-->
                  <p>
                    <b><span class="num">{{topData.equipmentViolationNum||0}}</span>次</b>
                    <span>违章次数</span>
                  </p>
                </div>
              </div>
            </div>
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
        <proj-chart style="margin-top: -20px" :projectId="projectId" :queryDate="queryDate"/>
      </section>
    </template>
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
  import 'echarts/lib/component/legend'
  import 'echarts/lib/component/title'
  import {
    getViolationsRankCusE,
    getMoniEquiViolE,
    getProjViolPropE,
  } from "@/api/smartMonitor/tower";
  import OrganizeTree from "@/components/OrganizeTree/index";
  import ProjChart from "../../project/components/Customize";
  const moment = extendMoment(Moment);
  const range7 = moment.range(moment().subtract(6, 'days'), moment());
  const recent7day = Array.from(range7.by('days')).map(m => m.format('MM-DD'))
  export default {
    name: "Customize",
    components: {
      'chart': ECharts,
      OrganizeTree,
      ProjChart
    },
    props: {
      queryDate: {
        type: String,
        require: true,
        dafault:  moment().format("yyyy-MM-DD"),
      }
    },
    data() {
      return {
        topData:'',
        type: this.$store.state.user.curRole.type,
        projectId: this.$store.state.user.curRole.id,
        companyId: this.$store.state.user.curRole.id,
        // conditionsDate
        defaultProps: {
          children: "children",
          label: "regionName",
          isLeaf: 'leaf'
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
              {value: 0, name: '项目总数'},
              {value: 0, name: '违章项目'},
            ],
          }]
        },
        keyWords: '',
      }
    },
    created() {
      this.getTopData();
      this.getLbData()
      this.getRbData()
    },
    computed: {
      queryParams() {
        console.log(this.queryDate)
        return {
          conditionsDate: this.queryDate,
          companyId: this.companyId
        }
      }
    },
    methods: {
      getTopData() {
        getMoniEquiViolE(this.queryParams).then( res => {
            this.topData = res.data;
        })
      },
      getLbData(){
        getViolationsRankCusE(this.queryParams).then(res => {
          this.lbOptions.yAxis[0].data = _.map(res.data, 'projectName');
          this.lbOptions.series[0].data = res.data.map(e => e.projectNumber||0);
        })
      },
      getRbData(){
        getProjViolPropE(this.queryParams).then(res => {
          this.rbOptions.series[0].data = [
            {value: res.data.projectNumber||0, name: '项目总数'},
            {value: res.data.proportionProjectViolation||0, name: '违章项目'},
          ]
          console.log('违章占比',res)
        })
      },
      /** *********************下方是左侧树相关的**************************/
      handleNodeClick(node) {
        this.type = node.type;
        if (node.type === 1) {
          this.projectId = node.id;
        } else {
          this.companyId = node.id;
          this.getTopData();
          this.getLbData()
          this.getRbData()
        }
      },
    },
    watch: {
      queryDate() {
        this.getTopData();
        this.getLbData()
        this.getRbData()
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
          align-items: center;
          .ana-l{
            p{
              width: 85px;
              text-align: center;
              font-size: 16px;
              /*color: #383838;*/
              b{
                margin-bottom: 10px;
                display: block;
                font-weight: normal;
                .num{
                  font-size: 30px;
                }
              }
              .ana-tit{
                white-space: nowrap;
                display: block;
                margin-top: 16px;
              }
            }
          }
          .ana-item{
            display: flex;
            width: 160px;
            justify-content: space-between;
            margin-bottom: 38px;
            .ana_item_icon{
              width: 40px;
              height: 50px;
              display: block;
              &.cam{
                background: url(~@/assets/image/cam.png) no-repeat center;
                background-size: contain;
              }
              &.e_cam{
                background: url(~@/assets/image/e_cam.png) no-repeat center;
                background-size: contain;
              }
              &.pie{
                background: url(~@/assets/image/pie.png) no-repeat center;
                background-size: contain;
              }
              &.hook{
                background: url(~@/assets/image/hook.png) no-repeat center;
                background-size: contain;
              }
              &.e_hook{
                background: url(~@/assets/image/e_hook.png) no-repeat center;
                background-size: contain;
              }
            }
            p{
              width: 85px;
              text-align: center;
              font-size: 12px;
              margin-bottom: 10px;
              /*color: #383838;*/
              b{
                display: block;
                font-weight: normal;
                margin-bottom: 10px;
                .num{
                  font-size: 16px;
                  font-weight: bold;
                }
              }
            }
          }
          &.analyse{
            display: flex;
            justify-content: space-around;
          }
        }
      }
    }
  }
</style>
