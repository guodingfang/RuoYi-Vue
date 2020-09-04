<template>
  <el-dialog
    title="监测详细"
    :visible.sync="isShow"
    width="1200px"
    :before-close="close"
  >
    <vue-scroll ref="scroll" style="height: 77vh">
      <div class="date_btn">
        统计周期：
        <template v-if="activeName== 1">
          <el-date-picker
            @change="getEcharts"
            v-model="queryParams.statisticalPeriod"
            type='date'
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>
        </template>
        <template v-else>
          <el-date-picker
            @change="getEcharts"
            v-model="queryParams.statisticalPeriod"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择月份"
          >
          </el-date-picker>
        </template>
      </div>

      <el-tabs v-model="activeName" class="warp_tab">
        <el-tab-pane
          v-for="item in tabsList"
          :key="item.label"
          :label="item.label"
          :name="item.name"
        >
        </el-tab-pane>
      </el-tabs>
      <v-chart
        :style="{ width: '100%' }"
        ref="rt"
        :options="rtOptions"
        autoresize
      />
      <div class="table-text">
        总量
        <span class="jl-green mr10 pointer title">{{num}}</span>
        kWh
      </div>
      <Table
        class="table-top"
        :tableData="tableData"
        :tableConfig="tableConfig"
      />
    </vue-scroll>
  </el-dialog>
</template>
<script>
import Table from "@/components/Table";
import { detailKwh } from "@/api/scada/monitoring";
import dayjs from "dayjs";
import moment from "moment";
const month = dayjs(new Date())
  .startOf("month")
  .format("YYYY-MM");
const day = dayjs().format("YYYY-MM-DD");
export default {
  name: "",
  components: {
    Table
  },
  props: {
    detailData: {
      type: [Object, Array],
      default: () => {}
    },
    isShow: {
      type: Boolean,
      default: false
    },
    state: {
      type: String,
      default: () => null
    },
    monitoringEquipment: {
      type: String,
      default: () => null
    }
  },
  watch: {
    isShow: {
      async handler(val) {
        if (val) {
          this.getEcharts()
        }
      },
      immediate: true
    },
    activeName(val) {
      if (val == 1) {
        this.queryParams.statisticalPeriod = day;
        this.getEcharts();
      } else {
        this.queryParams.statisticalPeriod = month;
        this.getEcharts();
      }
    }
  },
  data() {
    return {
      activeName: "1",
      tabsList: [
        { label: "按日", name: "1" },
        { label: "按月", name: "2" }
        // {label: '按区域', name: 'second'},
      ],
      tableConfig: [
        { prop: "electricityQuantity", label: "电量(kWh)" },
        { prop: "timeSlot", label: "时间" },
        { prop: "initialCharge", label: "起码(kWh)" },
        { prop: "stopCodePower", label: "止码(kWh)" }
        // { prop: 'monitoringEquipment', label: '安装区域'},
        // { prop: 'monitoringEquipment', label: '是否总表'},
        // { prop: 'monitoringEquipment', label: '今日电量(kWh)'},
        // { prop: 'monitoringEquipment', label: '本月电量(kWh)'},
      ],
      total: 0,
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        statisticalPeriod: day
      },
      rtOptions: {},
      num: 0
    };
  },
  methods: {
    close() {
      this.$emit("on-close");
    },
    async getEcharts(type) {
      const re = await detailKwh({
        monitoringEquipment: this.monitoringEquipment,
        queryType: this.activeName,
        statisticalPeriod: this.queryParams.statisticalPeriod
      });
      let obj = {
        name:re.rows.map(i => i.timeSlot),
        data:re.rows.map(i => i.electricityQuantity),
      }
      this.tableData = re.rows
      let num = 0
      let arr = [1,1,1,1,2,3]
      re.rows.forEach(i=> {
        num += parseInt(i.electricityQuantity)
      })
      this.num = num
      this.setEcharts(obj)
    },
    setEcharts(obj) {
      this.rtOptions = {
        backgroundColor: "#ffffff",
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
            textStyle: {
              color: "#fff"
            }
          }
        },
        grid: {
          left: "20",
          right: "5",
          bottom: "40",
          top: "55",
          containLabel: true
        },
        legend: {
          data: ["用电"],
          orient: "horizontal",
          icon: "rect",
          show: true,
          right: 20,
          top: 0,
          itemWidth: 24,
          itemHeight: 24
        },
        calculable: true,
        xAxis: [
          {
            type: "category",
            axisLine: {
              lineStyle: {
                color: "#9aa2a2"
              }
            },
            splitLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitArea: {
              show: false
            },
            axisLabel: {
              interval: 0,
              textStyle: {
                fontSize: 10,
                color: "#9ba3a3"
              }
            },
            data: obj.name
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "用电（kWh）",
            min: 0,
            minInterval: 1,
            position: "left",
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: "dashed"
              }
            }
          }
        ],
        dataZoom: {
          show: true,
          // type: 'inside',
          start: 0,
          end: 40
        },
        series: [
          {
            name: "用电量",
            type: "bar",
            stack: "总量",
            barMaxWidth: 15,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "#2dcfcb"
              }
            },
            data: obj.data
          }
        ]
      };
    }
  }
};
</script>
<style lang="scss" scoped>
$--border-size: 1px solid #dddcdc;
.date_btn{
  position: absolute;
  right: 0;
  top: 10px;
  z-index: 99;
}
.warp {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  min-height: 80vh;
  background: #fff;
  z-index: $eq-pop;
  // padding: 0 40px 20px;
  // overflow-y: auto;
  .table-tit {
    font-size: 16px;
    font-weight: normal;
    padding: 16px 30px;
    margin: 0;
    border-bottom: $--border-size;
    .el-icon-close {
      float: right;
    }
  }
}
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
.table-top {
  margin-top: 16px;
}
.table-text{
  font-size: 16px;
  line-height: 40px;
  font-weight: bold;
  span{
    font-size: 22px;
  }
}
</style>
