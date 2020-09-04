<template>
<section class="lwarp">
  <div class="top-warp">
    <!-- faceUrl -->
    <img
      :src="topData.faceUrl"
      class="top-img"
    />
    <pTable
      :tableData="labelData"
    ></pTable>
  </div>
    <Table
      style="margin-top: 27px;"
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
      <template v-slot:eventType>
        <el-table-column label="事件类型" align="left">
          <template slot-scope="scope">
            <span v-if='scope.row.eventType == 0'>负向</span>
            <span v-else>正向</span>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="照片" align="left">
          <template slot-scope="scope">
            <!-- scope.row.imageUrl -->
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getBtable"
    />
    <!-- -->
</section>

</template>
<script>
import pTable from "./pTables";
import Table from '@/components/Table';
import { exchangeRecord,getBtable } from "@/api/people/record";
export default {
  name: "Inspection",
  components: {
    pTable,Table
    // recordJust,
    // recordBack,
  },
  props: {
    detailData: {
      type: Object,
      default: () => {}
    }
  },
  watch: {
    detailData: {
      async handler(val){
        const { personId } = val
        const re = await exchangeRecord(personId)
        this.topData = re.data
        this.getBtable()
      },
      deep:true,
      immediate:true
    }
  },
  data() {
    return {
      tableConfig: [
        { prop: "createdTime", label: '兑换时间' },
        { prop: 'itemName', label: '兑换物品' },
        { prop: 'exchangeMount', label: '兑换数量' },
      ],
      total: 0,
      tableData: [],
      topData: {},
      //TODO 这个查询参数感觉有问题，后面再写
      //项目查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
    };
  },
  computed: {
    labelData() {
      const re = this.topData;
      return [
        { key: "姓名", value: re.personName },
        { key: "累计兑换点券", value: re.costPoint },
        { key: "累计扣除分数", value: re.totalDeductPoints },
        { key: "身份证号", value: re.idNumber },
        { key: "所在班组", value: re.teamName },
        { key: "参建单位", value: re.unitName }
      ];
    }
  },
  methods: {
    async getBtable() {
      const re = await getBtable({
        ...this.queryParams,
        personId: this.detailData.personId
      })
      this.tableData = re.rows
      this.total = re.total
    }
  }
};
</script>
<style lang="scss" scoped>
.lwarp{
  height: 78vh;
  overflow: auto;
}
.top-warp{
  display: flex;
  align-items: center;
  margin-top: 20px;
  .top-img{
    padding: 6px 17px;
    border: 1px solid #dddcdc;
    width: 70px;
    height: 89px;
    box-sizing: content-box;
    border-right: none;
    margin-right: 1px;
  }
}
</style>
