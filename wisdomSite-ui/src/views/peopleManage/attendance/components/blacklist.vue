<template>
  <section class="t_warp">
    <Table
      :tableData="tableData"
      :tableConfig="tableConfig"
    />

    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getBtable"
    />
  </section>
</template>
<script>
import {
  getBtable,
} from "@/api/people/workerLib";
import Table from '@/components/Table';
export default {
  name: 'blacklist',
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
        { prop: 'projectName', label: '所属项目', width: ''},
        { prop: 'participatingUnitName', label: '所属参建单位', width: ''},
        { prop: 'teamName', label: '所属班组', width: ''},
        { prop: 'workTypeName', label: '工种', width: ''},
        { prop: 'illegalDate', label: '违规日期' },
        { prop: 'illegalReason', label: '违规原因', width: ''},
        { prop: 'createdTime', label: '加入日期', width: ''},
        { prop: 'removeDate', label: '移除日期', width: ''},
      ],
      total: 0,
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deleted: 1,
        idNumber: this.parmId
      },
    }
  },
  created() {
    this.getBtable()
  },
  methods: {
    async getBtable() {
      const re = await getBtable(this.queryParams)
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
