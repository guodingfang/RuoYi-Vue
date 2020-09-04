<template>
  <section class="t_warp">
    <Table
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
    </Table>
  </section>
</template>
<script>
import {
  getJtable,
} from "@/api/people/workerLib";
import Table from '@/components/Table';
export default {
  name: 'undergo',
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
        { prop: 'buildStructureType', label: '项目类别', width: ''},
        { prop: 'createdTime', label: '进场日期' },
        { prop: 'projectNo', label: '项目编号', width: ''},
        { prop: 'projectName', label: '项目名称', width: ''},
      ],
      total: 0,
      tableData: [],
      queryParams: {
        // pageNum: 1,
        // pageSize: 10,
        deleted: 1,
        unitOrgcode: this.parmId
      },
    }
  },
  created() {
    this.getJtable()
  },
  methods: {

    async getJtable() {
      const re = await getJtable(this.queryParams)
      this.tableData = re.rows
      // this.total = re.total
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
