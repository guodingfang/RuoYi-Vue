<template>
  <div>
    <div class="query-box">
      <span>全部（{{ total }}）</span>
      <div class="r-query">
        <el-date-picker
          style="width: 300px;"
          v-model="timeRange"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
        <el-select
          placeholder="项目名称"
          v-model="queryParams.projectId"
          style="width: 200px"
          filterable
          clearable
        >
          <el-option
            v-for="item in projectList"
            :key="item.id"
            :label="item.projectName"
            :value="item.id"
          />
        </el-select>
        <el-select
          placeholder="整改状态"
          v-model="queryParams.status"
          style="width: 100px"
          clearable
        >
          <el-option label="待整改" value="1" />
          <el-option label="待验证" value="2" />
          <el-option label="已完成" value="3" />
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
          >新增整改任务
        </el-button>
      </div>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:statusName>
        <el-table-column label="整改状态" width="80px">
          <template slot-scope="scope">
            <status-tag :status="scope.row.statusName"/>
          </template>
        </el-table-column>
      </template>
      <template v-slot:qualityInfo>
        <el-table-column
          label="质量问题等级 | 问题类型 | 问题描述"
          width="240px"
        >
          <template slot-scope="scope">
            <status-tag :status="scope.row.qualityProblemLevelName||'无'"/> |
            {{ scope.row.qualityProblemName||'暂无' }} | {{ scope.row.comment||'暂无' }}
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
    <!-- 添加编辑项目框 -->
    <!--    <ProjectDetail-->
    <!--      :isCreate="isCreate"-->
    <!--      :dialogFlag.sync="dialogFlag"-->
    <!--      :projectForms.sync="projectForm"-->
    <!--      @query="getQualityList"-->
    <!--    />-->
  </div>
</template>

<script>
import {
  listCheckManager,
  getCheckManagerById,
  addCheckManager,
  updateCheckManager,
  problemTypeList,
  exportCheckManager,
  getCheckType,
  getCheckUser,
  addProblemType,
  updateProblemType,
  handleClickToForbiddenProblemType,
  deleteProblemType,
  checkManagerDetail,
  checkManagerDetailList,
  listProject
} from "@/api/quality/checkManager";
import { get } from "@rucloud/cloud-share/dist/utils";
import Table from "@/components/Table";
import Pagination from "@/components/Pagination";
import DetailDialog from "./DetailDialog";
import AddDialog from "./AddDialog";
import StatusTag from '../../../../components/StatusTag/index'
export default {
  name: "Patrol",
  components: {
    StatusTag,
    AddDialog,
    DetailDialog,
    Table,
    Pagination
  },
  data() {
    return {
      tableConfig: [
        { slot: "operate" },
        { slot: "statusName" },
        { prop: "repairLimitDate", label: "整改时限", width: "" },
        { prop: "projectName", label: "项目名称", width: "" },
        { prop: "checkPositionName", label: "检查人", width: "" },
        { prop: "checkDate", label: "检查时间", width: "" },
        { prop: "checkPosition", label: "检查部位", width: "" },
        { slot: "qualityInfo" },
        { prop: "repairPersonName", label: "整改人", width: "" },
        { prop: "verifyPersonName", label: "验证人", width: "" }
      ],
      total: 0,
      tableData: [],
      //项目查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        status: undefined
      },
      projectList: [],
      projName: "",
      timeRange: "",
      detailData: {},
      engineeringForm: {},
      dialogFlag: false,
      addDialogFlag: false,
      isCreate: true,
      title: ""
    };
  },
  created() {
    this.getProjectList();
    this.getQualityList();
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
      let obj
      if (this.timeRange && this.timeRange.length > 1) {
        obj = {
          kssj: this.timeRange[0],
          jssj: this.timeRange[1]
        }
      } else {
        obj = {}
      }
      listCheckManager({
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
      const re = await checkManagerDetail(id);
      const res = await checkManagerDetailList(id);
      this.detailData = {
        ...re.data,
        list: res.data
      };
      this.dialogFlag = true;
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出质量巡检数据！", "导出", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportCheckManager(queryParams);
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
.query-box {
  height: 60px;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
  .add-btn {
    margin-top: 14px;
  }
}
</style>
