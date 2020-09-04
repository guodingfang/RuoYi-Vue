<template>
  <section>
    <div class="select_warp">
      <el-date-picker
        v-model="queryParams.startTime"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="开始日期">
      </el-date-picker>
      <el-date-picker
        v-model="queryParams.endTime"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="结束日期">
      </el-date-picker>
      <el-button type='primary' @click="getEtable">查询</el-button>
      <el-button type="primary" @click="handleExport">导出数据</el-button>
    </div>
    <Table
      class="table-warp"
      :tableData="tableData"
      :tableConfig="tableConfig"
    />
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getEtable"
    />
  </section>
</template>
<script>
import Table from '@/components/Table';
import {
  getEtable,exportJob
 } from "@/api/unload";
export default {
  name: 'log',
  components: { Table,},
  inject: ["detailParm"],
  data () {
    return {
      tableConfig: [
        { prop: 'alarmStatus', label: '报警状态' },
        { prop: "id", label: "序号", width: '50px' },
        { prop: 'reportingTime', label: '时间'},
        { prop: 'presentLoad', label: '当前载重' },
        { prop: 'percentageLoad', label: '载重百分比' },
        { prop: 'workingCondition', label: '工作状态' },
      ],
      total: 0,
      tableData: [],
      //项目查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        monitoringEquipmentNo: this.detailParm.monitoringEquipmentNo,
        startTime: '',
        endTime: '',
      },
    }
  },
  created() {
    this.getEtable()
    console.log(this.queryParams);
  },
  methods: {
    async getEtable() {
      const re = await getEtable(this.queryParams)
      this.tableData = re.rows
      this.total = re.total
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出数据！", "导出", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportJob(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    }
  }
}
</script>
<style lang='scss' scoped>
.select_warp{
  margin-bottom: 17px;
}
</style>
