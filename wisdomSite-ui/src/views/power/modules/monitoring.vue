<template>
  <section>
    <div class="pos_title">
      <el-select
        placeholder="项目名称"
        @change="getData"
        v-model="queryParams.projectId"
        style="width: 180px; margin-right: 20px;"
        filterable
        clearable
      >
        <el-option
          v-for="item in $DStore.state.getProject"
          :key="item.id"
          :label="item.projectName"
          :value="item.id"
        >
        </el-option>
      </el-select>
    </div>
    <div class="title_warp">
      <div class="tl_w">
        <img style="width: 127px;height:127px" src="./../image/1.png" alt="" />
        <ul class="text">
          <li class="tl_wt">总用电量（kWh）</li>
          <li class="tl_num">{{tlw.totalPower}}</li>
          <li class="tl_op">计量周期:{{tlw.insertTime}}</li>
          <!-- insertTime -->
        </ul>
      </div>
      <ul class="tr_w">
        <li class="tr_wxtp">
          <img src="./../image/5.png" alt="">
          <span class="tl_wt">有功功率（kwh）</span>
          <span class="tl_num">{{tlw.activePower}}</span>
        </li>
        <li class="tr_wxtp">
          <img src="./../image/4.png" alt="">
          <span class="tl_wt">无功功率（kwh）</span>
          <span class="tl_num">{{tlw.reactivePower}}</span>
        </li>
      </ul>
    </div>
    <h4 class="tit" style="margin-bottom: 0;">能耗分析</h4>
    <el-tabs v-model="activeName" class="warp_tab">
      <el-tab-pane v-for="item in tabsList" :key="item.label" :label="item.label" :name="item.name">
      </el-tab-pane>
    </el-tabs>
    <v-chart :style="{width: '100%'}" ref="rt" :options="rtOptions" autoresize />
    <h4 class="tit">电表详情</h4>
    <Table
      class="table-top"
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
      <template v-slot:whether>
        <el-table-column label="是否总表" align="left">
          <template slot-scope="scope">
            <span v-if="scope.row.whether == 1">是</span>
            <span v-else>否</span>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span  class="jl-green mr10 pointer" @click="detailMonit(scope.row.monitoringEquipment)">监测记录</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getMtable"
    />
    <component
      :is="model"
      :isShow="popShow"
      :monitoringEquipment='monitoringEquipment'
      @on-close="closePop"
    />
  </section>
</template>
<script>
import { getMtable,getKwh, getEchartsData, detailKwh, } from "@/api/scada/monitoring";
import Table from '@/components/Table';
import detailMonit from './detail-monit'
import Moment from 'moment';
import { extendMoment } from 'moment-range';
const moment = extendMoment(Moment);
const range7 = moment.range(moment().subtract(30, 'days'), moment());
const recent7day = Array.from(range7.by('days')).map(m => m.format('MM-DD'))
export default {
  name: "monitoring",
  components: {
    Table,
    detailMonit,
  },
  data() {
    return {
      activeName: 'second',
      tabsList: [
        // {label: '按日', name: 'first'},
        // {label: '按月', name: 'dic'},
        {label: '按区域', name: 'second'},
      ],
      tableConfig: [
        { slot: 'operate' },
        { prop: 'equipmentName', label: '设备名称' },
        { prop: 'monitoringEquipment', label: '监测设备'},
        { prop: 'installationAreaName', label: '安装区域'},
        { slot: 'whether' },
        { prop: 'todayElectricity', label: '今日电量(kWh)'},
        { prop: 'monthElectricity', label: '本月电量(kWh)'},
      ],
      total: 0,
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: this.$lGet(this,'$DStore.state.getProject.0.id', null)
      },
      tlw: {},
      rtOptions: {},
      popShow: false,
      model: null,
      monitoringEquipment: null
    };
  },
  async created() {
    this.getData()
  },
  methods: {
    getData() {
      this.getMtable()
      this.getKwh()
      this.getEchartsData()
    },
    async getKwh() {
      const re = await getKwh({
        projectId: this.queryParams.projectId
      })
      this.tlw = re.data
    },
    async getEchartsData() {
      const res = await getEchartsData({
        analysisType: 3,
        projectId: this.queryParams.projectId
      })
      let obj = {
        name: res.rows.map(i => i.region),
        data: res.rows.map(i => i.totalPower),
      }
      this.setRtOptions(obj)
    },
    async getMtable() {
      const re = await getMtable(this.queryParams)
      this.tableData = re.rows
      this.total = re.total
    },
    async detailMonit(monitoringEquipment) {
      // const re = await detailKwh({monitoringEquipment:monitoringEquipment,queryType: 1})
      // console.log(re);
      this.monitoringEquipment = monitoringEquipment
      this.model = 'detailMonit'
      this.showPop(true)
    },
    setRtOptions(obj){
      this.rtOptions = {
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
          bottom: '40',
          top: '55',
          containLabel: true
        },
        legend: {
          data: ['用电'],
          orient: 'horizontal',
          icon: "rect",
          show: true,
          right: 20,
          top: 0,
          itemWidth: 24,
          itemHeight: 24
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
          "data": obj.name,
        }],
        yAxis: [
          {
            type: 'value',
            name: '用电（kWh）',
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
        ],
        dataZoom: {
          show: true,
          // type: 'inside',
          start: 0,
          end: 40,
        },
        series: [
          {"name": "用电量",
            "type": "bar",
            "stack": "总量",
            "barMaxWidth": 15,
            "barGap": "10%",
            "itemStyle": {
              "normal": {
                "color": "#60bdda",
              }
            },
            data:obj.data

          }]
      }
    },
    showPop(flag) {
      this.popShow = flag;
    },
    closePop(resful) {
      this.showPop(false);
      if (resful == 'setStaff') {
        // this.getData(this.paramInit);
      }
      this.model = null;
    },
  }
};
</script>
<style lang="scss" scoped>
$--border-size: 1px solid #dddcdc;
.tit {
  border-left: 4px solid #1ab394;
  padding-left: 10px;
  width: 100%;
  .tit_right {
    padding: 0;
    float: right;
  }
}
.pos_title{
  position: absolute;
  right: 0;
  top:-42px
}
.title_warp {
  border: $--border-size;
  background: #f6fbfd;
  @include flex(flex-start, center);
  .tl_w {
    @include flex(center, center);
    height: 280px;
    border-right: $--border-size;
    flex: 1;
    img {
      width: 200px;
      height: 200px;
    }
    .text {
      li {
        line-height: 50px;
      }
      .tl_op {
        color: #909399;
        font-size: 12px;
      }
    }
  }
  .tr_w{
    flex: 1;
    height: 280px;
    margin: 0;
    display: flex;
    flex-direction: column;
    .tr_wxtp{
      flex: 1;
      display: flex;
      align-items: center;
      img{
        width: 77px;
        height: 77px;
        margin-right: 17px;
      }
    }
  }
  .tl_wt,.tl_num {
    color: #7f8e8e8e;
    font-size: 26px;
    font-weight: bold;
  }
  .tl_num {
    color: #005bc5;
  }
}
</style>
