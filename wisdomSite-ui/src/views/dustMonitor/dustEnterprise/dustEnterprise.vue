<template>
  <section class="dust-warp">
    <div class="title">
      环境监测
      <div class="t-btn">
        <el-button type="primary">刷新</el-button>
        <span class="tb-span">项目</span>
        <el-select @change="getProjetList" clearable filterable v-model="queryParams.projectId" placeholder="项目名称">
          <el-option
            v-for="item in $DStore.state.getProject"
            :key="item.id"
            :label="item.projectName"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="table-tit">
      全部（{{total}}）
      <div class="t-btn">
        <el-button type="primary" @click="openLog">无监测的项目{{num}}</el-button>
        <!-- <el-checkbox v-model="checked">仅查看超标的项目</el-checkbox>
        <span class="tb-span">项目</span>
        <el-select clearable v-model="queryParams.eqw" placeholder="排序">
          <el-option label="按报警次数降序" value="1"></el-option>
          <el-option label="按报警次数升序" value="2"></el-option>
        </el-select> -->
      </div>
    </div>
    <Table
      class="table-warp"
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span  class="jl-green mr10 pointer" @click="detail(scope.row)">监测记录</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getProjetList"
    />

    <component
      :is="model"
      v-if="popShow"
      :state="state"
      :detailData='detailData'
      :is-show="popShow"
      @on-close="closePop"
    />
  </section>
</template>
<script>
import Table from '@/components/Table';
import {
  getProjetList,
  getProjetNum,
 } from "@/api/dustMonitor";
 import logTable from './modules/logTable'
 import detailPage from './modules/detail'
export default {
  name: 'dustEnterprise',
  components: { Table, logTable, detailPage},
  data () {
    return {
      tableConfig: [
        { slot: 'operate' },
        { prop: 'projectName', label: '项目名称' },
        { prop: 'todayAlarmNum', label: '今日报警次数'},
        { prop: 'pm25', label: 'PM2.5'},
        { prop: 'pm10', label: 'PM10'},
        { prop: 'noise', label: '噪音'},
        { prop: 'temperatureType', label: '温度'},
        { prop: 'humidityType', label: '湿度'},
        { prop: 'windSpeed', label: '风速'},
      ],
      total: 0,
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      num: 0,
      popShow: false,
      detailData: {},
      state: null,
      model: null,
      // model: 'detailPage',
    }
  },
  async created() {
    this.getProjetList()
    const re = await getProjetNum()
    this.num = re.data.noMonitoringProjectNum
  },
  methods: {
    async getProjetList() {
      const re = await getProjetList(this.queryParams)
      this.tableData = re.rows
      this.total = re.total
    },
    showPop(flag) {
      this.popShow = flag
    },
    closePop(resful) {
      this.showPop(false);
      if (resful == 'setStaff') {
        // this.getData(this.paramInit);
      }
      this.model = null;
    },
    openLog() {
      this.model = 'logTable',
      this.showPop(true)
    },
    detail(row) {
      this.detailData = row
      this.model = 'detailPage',
      this.showPop(true)
    },
  }
}
</script>
<style lang='scss' scoped>
$--border-size: 1px solid #dddcdc;
.dust-warp{
  position: relative;
  .title{
    line-height: 60px;
    border-bottom: $--border-size;
    padding-left: 27px;
    @include flex(null, center);

  }
  .table-tit{
    margin: 17px 27px;
    @include flex(null, center);
  }
  .t-btn{
    margin-left: auto;
    margin-right: 27px;
    .tb-span{
      color: #999999;
      font-size: 14px;
      margin: 0 10px;
    }
  }
  .table-warp{
    margin: 0 27px
  }
}
</style>
