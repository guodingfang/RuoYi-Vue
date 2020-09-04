<template>
  <div class="wrap">
    <div class="query-box">
      <span>全部（{{total}}）</span>
      <el-button size="small" type="primary" class="fr add-btn"  @click="openAddDialog">新增项目
      </el-button>
    </div>
    <Table
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span  class="jl-green mr10 pointer" @click="goDetail('overview', scope.row.id)">项目概况</span>
            <span class="jl-green mr10 pointer"  @click="goDetail('engineer', scope.row.id)">工程信息</span>
            <span class="jl-green mr10 pointer"  @click="goDetail('stone', scope.row.id)">里程碑设置</span>
            <span class="jl-green mr10 pointer"  @click="goDetail('area', scope.row.id)">工地分区</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getProjectList"
    />
    <add-project
      :dialogFlag.sync="dialogFlag"
      @query="getProjectList"
    />
  </div>
</template>

<script>
  import {listProject, getProjectById, addProject, updateProject, deleteProject, exportProject} from "@/api/project/project";
  import Table from '@/components/Table';
  import Pagination from '@/components/Pagination';
  import AddProject from "./components/AddProject";
  export default {
    name: 'index',
    components: {
      AddProject,
      Table,
      Pagination,
    },
    data() {
      return{
        tableConfig: [
          { slot: 'operate' },
          { prop: 'projectName', label: '项目名称',},
          { prop: 'projectUnitName', label: '归属分公司', width: '220px' },
          { prop: 'projectPersonName', label: '项目负责人', width: '150px'},
          { prop: 'projectAddress', label: '地址' },
        ],
        total: 0,
        tableData: [],
        //项目查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          projectNo: '',
          projectName: '',
        },
        projectForm: {},
        engineeringForm: {},
        dialogFlag: false,
        title: '',
      }
    },
    created() {
      this.getProjectList()
    },
    methods: {
      /** 进入详情页 */
      goDetail(name, id) {
        this.$router.push({name: `Detail`, query: { name, id }})
      },
      /** 查询项目 列表 */
      getProjectList() {
        listProject(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      /** 打开新增项目框*/
      openAddDialog() {
        this.dialogFlag = true;
      }
    }
  }
</script>

<style scoped lang="scss">
  .wrap{
    padding: 0 20px;
  }
  .query-box{
    height: 60px;
    line-height: 60px;
    .add-btn{
      margin-top: 14px;
    }
  }
</style>
