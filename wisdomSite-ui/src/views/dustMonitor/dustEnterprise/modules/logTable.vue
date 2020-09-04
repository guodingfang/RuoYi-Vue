<template>
  <el-dialog
    title="无环境监测的项目"
    :visible.sync="isShow"
    width="700px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <Table
      class="table-warp"
      :tableData="tableData"
      :tableConfig="tableConfig"
    />
  </el-dialog>
</template>
<script>
import Table from '@/components/Table';
import {
  getProjetNumList,
 } from "@/api/dustMonitor";
export default {
  name: 'log',
  components: { Table,},
  props: {
    isShow: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: [Object, Array],
      default: () => {}
    },
    state: {
      type: String,
      default: () => null
    }
  },
  watch: {
    isShow: {
      async handler(val){
        if(val) {
          const re = await getProjetNumList()
          this.tableData = re.rows
          console.log(re);
        }
      },
      immediate:true
    }
  },
  data () {
    return {
      tableConfig: [
        { prop: 'projectLeader', label: '项目负责人'},
        { prop: 'projectName', label: '项目名称' },
      ],
      tableData: [],
    }
  },
  methods: {
    handleClose() {
      this.$emit("on-close");
    },
  }
}
</script>
<style lang='stylus' scoped>

</style>
