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
      </div>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:statusName>
        <el-table-column label="编号" type="index" width="50" align="center"> </el-table-column>
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
      @pagination="getData"
    />
  </div>
</template>
<script>
import { get } from "@rucloud/cloud-share/dist/utils";
import { getTabel, listProject } from "@/api/quality/highlights";
import Table from "@/components/Table";
export default {
  name: "external",
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
      projectList: []
    };
  },
  components: {
    Table
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
    /** 获取项目下拉查询 */
    getProjectList() {
      listProject().then(response => {
        this.projectList = response.rows;
      });
    },
    /** 查询 列表 */
    getData() {
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