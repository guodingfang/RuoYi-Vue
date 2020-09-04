<template>
  <div class="wrap">
    <div class="query-box">
      <span>全部（{{ total }}）</span>
      <div class="r-query">
        <el-date-picker
          style="width: 300px;"
          value-format="yyyy-MM-dd"
          v-model="timeRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
        <el-select
          placeholder="项目名称"
          clearable
          v-model="queryParams.projectId"
          style="width: 200px"
        >
          <el-option
            v-for="item in projectList"
            :key="item.id"
            :label="item.projectName"
            :value="item.id"
          />
        </el-select>
          <el-select
            v-model="queryParams.acceptanceState"
            placeholder="验收状态"
            clearable
          >
            <el-option key="合格" label="合格" value="1" />
            <el-option key="不合格" label="不合格" value="2" />
            <el-option key="待验收" label="待验收" value="3" />
          </el-select>
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="getQualityList"
        ></el-button>
        <el-button type="primary" @click="handleExport"
          >批量导出
        </el-button>
        <el-button type="primary" class="add-btn" @click="openAddDialog"
          >申请验收
        </el-button>
      </div>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:statusName>
        <el-table-column label="验收状态" width="80px">
          <template slot-scope="scope">
            <status-tag :status="scope.row.acceptanceStateName" />
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span
              class="jl-green mr10 pointer"
              @click="openDetailDialog(scope.row.id)"
              >查看</span
            >
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getQualityList"
    />
    <DetailDialog
      :dialogFlag.sync="dialogFlag"
      :detailDatas.sync="detailData"
    />
    <AddDialog
      :isCreate="isCreate"
      :dialogFlag.sync="addDialogFlag"
      @query="getQualityList"
    />
  </div>
</template>

<script>
import { get } from "@rucloud/cloud-share/dist/utils";
import {
  listQualityAccept,
  getCheckUser,
  addApply,
  exportApplyAccept,
  acceptanceApplyDetail,
  acceptanceRecordDetail,
  listProject
} from "@/api/quality/qualityAccept";
import Table from "@/components/Table";
import Pagination from "@/components/Pagination";
import DetailDialog from "./components/DetailDialog";
import AddDialog from "./components/AddDialog";
import StatusTag from '@/components/StatusTag/index'
export default {
  name: "QualityAcceptance",
  components: {
    AddDialog,
    DetailDialog,
    Table,
    Pagination,
    StatusTag
  },
  data() {
    return {
      tableConfig: [
        { slot: "operate" },
        { slot: "statusName" },
        { prop: "planverificationDate", label: "验收时限", width: "" },
        { prop: "projectName", label: "项目名称", width: "" },
        { prop: "applyPersonName", label: "申请人", width: "" },
        { prop: "applyDate", label: "申请时间", width: "" },
        { prop: "acceptancePosition", label: "验收部位", width: "" },
        { prop: "acceptanceContent", label: "验收内容", width: "" },
        { prop: "verifyPersonName", label: "验证人", width: "" }
      ],
      total: 0,
      tableData: [],
      //项目查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      projectId: "",
      timeRange: "",
      detailData: {},
      engineeringForm: {},
      dialogFlag: false,
      addDialogFlag: false,
      isCreate: true,
      title: "",
      status: null,
      projectList: []
    };
  },
  created() {
    this.getQualityList();
    this.getProjectList();
  },
  methods: {
    /** 获取项目下拉查询 */
    getProjectList() {
      listProject().then(response => {
        this.projectList = response.rows;
      });
    },
    /** 查询 列表 */
    getQualityList() {
      let obj;
      if (this.timeRange && this.timeRange.length > 1) {
        obj = {
          kssj: this.timeRange[0],
          jssj: this.timeRange[1]
        };
      } else {
        obj = {};
      }
      listQualityAccept({
        ...this.queryParams,
        ...obj
      }).then(response => {
        this.tableData = response.rows;
        this.total = response.total;
      });
    },
    /** 打开新增框*/
    openAddDialog(id) {
      this.addDialogFlag = true;
      this.isCreate = true;
    },
    /** 打开详情框*/
    async openDetailDialog(id) {
      const re = await acceptanceApplyDetail(id);
      const res = await acceptanceRecordDetail(id);
      this.detailData = {
        ...re.data,
        list: res.data
      };
      this.dialogFlag = true;
    },
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出质量检查数据！", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportApplyAccept(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    }
  }
};
</script>

<style scoped lang="scss">
.wrap {
  padding: 0 20px;
  .query-box {
    height: 60px;
    line-height: 60px;
    display: flex;
    justify-content: space-between;
    .add-btn {
      margin-top: 14px;
    }
  }
}
</style>
