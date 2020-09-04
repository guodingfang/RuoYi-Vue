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
            <span v-if='scope.row.event.eventType == 0'>负向</span>
            <span v-else>正向</span>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="照片" align="left">
          <template slot-scope="scope">
            <el-image
              v-for="item in scope.row.imageUrls"
              :key='item.id'
              class="elImage"
              fit="cover"
              :src="item.url"
              :preview-src-list="[item.url]"
            >
              <div slot="error" class="image-error-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
            <!-- scope.row.imageUrl -->
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getLtable"
    />
    <!-- -->
</section>

</template>
<script>
import pTable from "./pTables";
import Table from '@/components/Table';
import { behaviorRecord, getLtable } from "@/api/people/record";
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
        const re = await behaviorRecord(personId)
        this.topData = re.data
        this.getLtable()
      },
      deep:true,
      immediate:true
    }
  },
  data() {
    return {
      tableConfig: [
        { slot: "operate" },
        { prop: 'createdTime', label: '时间' },
        { prop: 'event.eventName', label: '事件名称' },
        { slot: 'eventType'},
        { prop: 'point', label: '点券', width: ''},
        { prop: 'details', label: '详细说明', width: ''},
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
        { key: "累计获得点券", value: re.point },
        { key: "累计扣除分数", value: re.totalDeductPoints },
        { key: "身份证号", value: re.idNumber },
        { key: "所在班组", value: re.teamName },
        { key: "参建单位", value: re.unitName }
      ];
    }
  },
  methods: {
    async getLtable() {
      const re = await getLtable({
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
.elImage{
  width: 80px;
  // height: 80px;
  .el-icon-picture-outline{
    font-size: 80px;
  }
}
</style>
