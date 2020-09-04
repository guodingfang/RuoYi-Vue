<template>
  <section class="t_warp">
    <Table
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
      <template v-slot:operate>
        <el-table-column label="劳务合同附件" align="left">
          <template slot-scope="scope">
            <!-- <span  class="jl-green mr10 pointer" @click="detailWorker(scope.row.id)">详情</span> -->
            <span  class="jl-green mr10 pointer">查看</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getRtable"
    />
  </section>
</template>
<script>
import {
  getRtable,
} from "@/api/people/workerLib";
import Table from '@/components/Table';
export default {
  name: 'resume',
  props: {
    parmId: {
      type: [String,Number],
      default: () => null
    }
  },
  components: {
    Table,
  },
  data () {
    return {
     tableConfig: [
        { slot: 'operate' },
        { prop: 'inDate', label: '进场日期' },
        { prop: 'outDate', label: '退场日期', width: ''},
        { prop: 'projectName', label: '所属项目', width: ''},
        { prop: 'participatingUnitName', label: '所属参建单位', width: ''},
        { prop: 'teamName', label: '所属班组', width: ''},
        { prop: 'workTypeName', label: '工种', width: ''},
      ],
      total: 0,
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deleted: 1,
        id: this.parmId
      },
    }
  },
  created() {
    this.getRtable()
  },
  methods: {

    async getRtable() {
      const re = await getRtable(this.queryParams)
      this.tableData = re.rows
      this.total = re.total
      console.log(re);
    },
  }
}
</script>
<style lang='scss' scoped>
.t_warp{
  margin-top: 24px;
}
</style>
