<template>
  <div>
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
    <!-- 添加编辑项目框 -->
    <ProjectDetail
      :isCreate="isCreate"
      :dialogFlag.sync="dialogFlag"
      :projectForms.sync="projectForm"
      @query="getProjectList"
    />
    <EngineeringDialog
      :dialogFlag.sync="EngDialogFlag"
      :engineeringForms.sync="engineeringForm"
      @query="getProjectList"
    />
  </div>
</template>

<script>
  import {listProject, getProjectById, addProject, updateProject, deleteProject, exportProject} from "@/api/project/project";
  import Table from '@/components/Table';
  import Pagination from '@/components/Pagination';
  import ProjectDetail  from './ProjectDetail';
  import EngineeringDialog  from './EngineeringDialog';
  export default {
    name: 'ProjectLib',
    components: {
      Table,
      Pagination,
      ProjectDetail,
      EngineeringDialog
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
        EngDialogFlag: false,
        isCreate: true,
        title: '',
      }
    },
    created() {
      this.getProjectList()
    },
    methods: {
      /** 进入详情页 */
      goDetail(name, id) {
        this.$router.push({path: `/project/detail`, query: { name, id }})
      },
      /** 查询项目 列表 */
      getProjectList() {
        listProject(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      /** 打开新增项目框*/
      openAddDialog(id) {
        this.dialogFlag = true;
        this.isCreate = true;
      },
      /** 打开编辑项目框*/
      openUpdateDialog(id) {
        getProjectById(id).then(res => {
          this.projectForm = res.data;
          this.dialogFlag = true;
          this.isCreate = false;
        })
      },
      /** 打开工程框*/
      openEngerDialog(id) {
        this.EngDialogFlag = true;
      }
    }
  }
</script>

<style scoped lang="scss">
  .query-box{
    height: 60px;
    line-height: 60px;
    .add-btn{
      margin-top: 14px;
    }
  }
</style>
