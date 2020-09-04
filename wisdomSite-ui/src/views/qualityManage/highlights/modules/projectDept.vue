<template>
  <div>
    <div class="query-box">
      <span>全部（{{ total }}）</span>
      <div class="r-query">
        <el-select
          placeholder="项目名称"
          v-model="projectId"
          clearable
          style="width: 200px"
        >
          <el-option
            v-for="item in projectList"
            :key="item.id"
            :label="item.projectName"
            :value="item.id"
          />
        </el-select>
        <el-button type="primary" icon="el-icon-search" @click='getData()'></el-button>
        <el-button type="primary" class="add-btn" @click='showForm(true)'>新增亮点</el-button>
      </div>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:statusName>
        <el-table-column label="编号" type="index" width="50" align="left"> </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span
              class="jl-green mr10 pointer"
              @click="showLock(scope.row.id)"
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
      @pagination="getData"
    />
    <addForm :visible='visible' @on-close="closeForm" />
    <detailModel :formData='formData' :visible='detailVisible' @on-close="showDetail(false)" />
  </div>
</template>
<script>
import { get } from "@rucloud/cloud-share/dist/utils";
import { getTabel, listProject,getDetail } from "@/api/quality/highlights";
import Table from "@/components/Table";
import addForm from './add-project'
import detailModel from './detail-project'
export default {
  name: "projectDept",
  data() {
    return {
      tableConfig: [
        { slot: "operate" },
        { slot: "statusName" },
        { prop: "projectName", label: "项目名称", width: "" },
        { prop: "brightspotPosition", label: "亮点部位", width: "" },
        { prop: "brightspotDescribe", label: "亮点描述", width: "" },
        { prop: "uploadPersonName", label: "上传人", width: "" },
        { prop: "uploadDate", label: "上传日期", width: "" }
      ],
      total: 0,
      tableData: [],
      projectId: "",
      //项目查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      projectList: [],
      visible: false,
      detailVisible: false,
      formData: {}
    };
  },
  components: {
    Table,
    addForm,
    detailModel
  },
  created() {
    this.getData();
    this.getProjectList();
  },
  computed: {
    param() {
      return {
        ...this.queryParams,
        projectId: this.projectId
      };
    }
  },
  methods: {
    async showForm(flag) {
      this.visible = flag
    },
    closeForm(flag) {
      console.log(flag,123);
      if (flag) {
        console.log(flag,123);
        this.getData()
      }
      this.showForm(false)
    },
    async showDetail(flag) {
      this.detailVisible = flag
    },
    async showLock(id) {
      const re = await getDetail(id)
      console.log(re);
      this.formData = re.data
      this.showDetail(true)
    },

    /** 获取项目下拉查询 */
    getProjectList() {
      listProject().then(response => {
        this.projectList = response.rows;
      });
    },
    /** 查询 列表 */
    getData() {
      console.log(123);
      getTabel(this.param).then(response => {
        console.log(response);
        this.tableData = response.rows;
        this.total = response.total;
      });
    }
  }
};
</script>
<style lang="scss" scoped>
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
