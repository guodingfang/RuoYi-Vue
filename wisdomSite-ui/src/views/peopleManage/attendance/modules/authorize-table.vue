<template>
  <el-table :data="tableData" style="width: 100%" class="month_table">
    <el-table-column prop="deviceName" align="left" label="打卡机名称" />
    <el-table-column prop="serialNumber" align="left" label="打卡机编号" />
    <el-table-column prop="online" align="left" label="打卡机状态" />
    <el-table-column prop="remark" v-if="state" align="left" label="失败原因" />
  </el-table>
</template>
<script>
export default {
  name: 'authorizeTable',
  props: {
    tableData: {
      type: [Array],
      default: () => []
    },
    state: {
      type: String,
      default: () => null
    }
  },
  data () {
    return {
    }
  },
  created() {
    console.log(this.tableData);
  }
}
</script>
<style lang='scss' scoped>
.month_table{
  margin-top: 16px;
}
</style>
