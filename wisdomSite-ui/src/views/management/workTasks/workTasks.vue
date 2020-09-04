<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane
        v-for="item in tabList"
        :key="item.id"
        :label="item.name"
        :name="item.id"
      ></el-tab-pane>
    </el-tabs>
    <span class="table-title">全部（{{ total }}）</span>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:statusName>
        <el-table-column label="整改状态" width="80px">
          <template slot-scope="scope">
            <status-tag :status="scope.row.statusName" />
          </template>
        </el-table-column>
      </template>
      <template v-slot:qualityInfo>
        <el-table-column label="隐患等级 | 作业类型 | 问题描述" width="260px">
          <template slot-scope="scope">
            <StatusTag :status="scope.row.hazardLevelName" />|
            {{ scope.row.operationName || "暂无" }} |
            <span v-if="!scope.row.hazardDescribe">暂无</span>
            <span v-else-if="scope.row.hazardDescribe.length <= 6">{{
              scope.row.hazardDescribe
            }}</span>
            <el-popover
              v-else
              trigger="hover"
              placement="top"
              style="display: inline-block"
            >
              <p>{{ scope.row.hazardDescribe }}</p>
              <div slot="reference" class="name-wrapper">
                <span>{{
                  scope.row.hazardDescribe.substring(0, 6) + "..."
                }}</span>
              </div>
            </el-popover>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left" width="137">
          <template slot-scope="scope">
            <span
              class="jl-green mr10 pointer"
              @click="openDetail(scope.row.id)"
              >查看</span
            >
            <span
              class="jl-green mr10 pointer"
              v-if="activeName == 2"
              @click="addRectify(scope.row)"
              >进行整改</span
            >
            <span
              class="jl-green mr10 pointer"
              v-if="activeName == 3"
              @click="addReform(scope.row)"
              >进行验证</span
            >
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getItable"
    />
    <component
      :is="model"
      v-if="popShow"
      :state="state"
      :detailData="detailData"
      :is-show="popShow"
      @on-close="close"
    />
  </div>
</template>
<script>
// import record from './modules/record'
import { getItable } from "@/api/Inspection/record";
import { detailInspecTest } from "@/api/Inspection/record";
import Table from "@/components/Table";
import StatusTag from "@/components/StatusTag/index";
import detailRecord from "./../Inspection/modules/detail-record";
import addRectify from "./modules/add-rectify";
import addReform from "./modules/add-reform";
export default {
  name: "workTasks",
  components: {
    Table,
    StatusTag,
    detailRecord,
    addRectify,
    addReform
  },
  data() {
    return {
      activeName: "1",
      tabList: [
        { name: "巡视记录", id: "1" },
        { name: "待我整改", id: "2" },
        { name: "待我验证", id: "3" },
        { name: "抄送信息", id: "4" }
      ],
      userId: this.$store.state.user.id,
      tableConfig: [
        { slot: "operate", width: "120" },
        { slot: "statusName" },
        { prop: "rectifyLimitDate", label: "整改时限", width: "" },
        { prop: "projectName", label: "项目名称", width: "" },
        { prop: "checkPositionName", label: "检查人", width: "" },
        { prop: "checkDate", label: "检查时间", width: "" },
        { prop: "checkPosition", label: "检查部位", width: "" },
        { slot: "qualityInfo" },
        { prop: "repairPersonName", label: "整改人", width: "" },
        { prop: "verifyPersonName", label: "验证人", width: "" },
        { prop: "senderName", label: "抄送至", width: "" }
      ],
      total: 0,
      tableData: [],
      //项目查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      popShow: false,
      detailData: {},
      model: null,
      state: null
    };
  },
  watch: {
    activeName: {
      immediate: true,
      handler(val) {
        {
          let obj;
          if (val == "1") {
            obj = { checkPositionId: this.userId };
          }
          if (val == "2") {
            obj = { rectifyPersonId: this.userId };
          }
          if (val == "3") {
            obj = { verifyPersonId: this.userId };
          }
          if (val == "4") {
            obj = { senderId: this.userId };
          }
          const {pageNum,pageSize} = this.queryParams
          this.queryParams = {
            pageNum,pageSize
          }
          Object.assign(this.queryParams, obj)
          this.getItable();
        }
      }
    }
  },
  created() {
    // this.getItable()
  },
  methods: {
    async getItable() {
      const re = await getItable(this.queryParams);
      this.tableData = re.rows;
      this.total = re.total;
    },
    async openDetail(id) {
      // const re = await detailInspec(id)
      this.model = "detailRecord";
      const res = await detailInspecTest(id);
      this.detailData = res.data;
      this.showModel(true);
    },
    addRectify(item) {
      this.model = "addRectify";
      this.detailData = item;
      this.showModel(true);
    },
    addReform(item) {
      this.model = "addReform";
      this.detailData = item;
      this.showModel(true);
    },
    showModel(flag) {
      this.popShow = flag;
    },
    close(rest) {
      if (rest) {
        this.getItable();
      }
      this.model = null;
      this.showModel(false);
    }
  }
};
</script>
<style lang="scss" scoped>
.wrap {
  padding: 0 20px;
}
::v-deep {
  .el-tabs__content {
    overflow: inherit;
  }
}
.table-title {
  display: inline-block;
  margin: 16px 0;
}
</style>
