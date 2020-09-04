<template>
  <div class="access-wrap">
    <div class="query-box">
      人数：{{spreadData.personNumber}}人    应发金额：{{spreadData.planningAmount}}万    已发金额：{{spreadData.payingAmount}}万
      <div class="r-query">
        <el-select
          placeholder="项目名称"
          v-model="queryParams.projectId"
          style="width: 157px; margin-right: 10px"
          @change="getStable()"
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
          placeholder="参建单位"
          v-model="queryParams.unitId"
          style="width: 157px; margin-right: 10px"
          @change="getStable()"
          clearable
        >
          <el-option
            v-for="item in unitList"
            :key="item.id"
            :label="item.unitName"
            :value="item.id"
          />
        </el-select>
        <el-select
          placeholder="全部班组/部门"
          v-model="queryParams.teamId"
          style="width: 157px; margin-right: 10px"
          @change="getStable()"
          clearable
        >
          <el-option
            v-for="item in teamList"
            :key="item.id"
            :label="item.teamName"
            :value="item.id"
          />
        </el-select>
        <el-select
          placeholder="全部人员"
          v-model="queryParams.teamPersonId"
          style="width: 157px; margin-right: 10px"
          @change="getStable()"
          clearable
        >
          <el-option
            v-for="item in personList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
        <el-date-picker
          style="width: 157px; margin-right: 10px"
          v-model="queryParams.month"
          @change="getStable"
          type="month"
          value-format="yyyy-MM"
        />
        <el-button @click="addLeading">批量导入</el-button>
      </div>
    </div>
    <div class="table-wrap">
      <Table
        :tableData="tableData"
        :tableConfig="tableConfig"
      >
      </Table>
      <div class="tb-footer"><el-button type="text" @click="handleExport">导出数据</el-button>&emsp;</div>
    </div>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getStable"
    />
    <component
      :is="model"
      v-if="popShow"
      :state="state"
      :detailData="detailData"
      :is-show="popShow"
      @on-close="closePop"
    />
  </div>
</template>

<script>
  import moment from 'moment';
  import {
    listProject,
    listProjectParticipatingUnit,
  } from "@/api/people/attendance";
  import {
    getTeam,
    getPerson,
  } from "@/api/people/checkwork";
  import { getStable,exportJob,getSdata } from "@/api/people/salary";
  import Table from '@/components/Table';
  import leading from "./modules/leading";
  export default {
    name: 'AccessRecord',
    components: {
      Table,leading
    },
    data() {
      return{
        tableConfig: [
          { prop: 'payingDate', label: '发放时间', width: ''},
          { prop: 'personName', label: '姓名' },
          { prop: 'idNumber', label: '身份证号' },
          { prop: 'unitName', label: '劳务公司', width: '200px'},
          { prop: 'teamName', label: '班组/部门', width: ''},
          { prop: 'planningAmount', label: '应发金额', width: ''},
          { prop: 'payingAmount', label: '已发金额', width: ''},
          { prop: 'salaryYear', label: '年份', width: ''},
          { prop: 'salaryMonth', label: '月份', width: ''},
          { prop: 'attendanceDays', label: '出勤天数', width: ''},
          { prop: 'monthHours', label: '当月工时', width: ''},
        ],
        total: 0,
        tableData: [],
        //TODO 这个查询参数感觉有问题，后面再写
        //项目查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          projectId: '',
          unitId: '',
          teamId: '',
          teamPersonId: '',
          month: moment().format("yyyy-MM"),
          // projectId: '',
          // unitId: '',
        },
        projectList: [],
        unitList: [],
        teamList: [],
        personList: [],
        detailData: {},
        popShow: false,
        model: null,
        state: null,
        spreadData: {}
      }
    },
    created() {
      this.getProjectList();
    },
    methods: {
      showPop(flag) {
        this.popShow = flag;
      },
      closePop(resful) {
        this.showPop(false);
        if (resful == "setStaff") {
          this.getData(this.queryParams);
        }
        this.model = null;
      },
      // 批量导入
      addLeading() {
        this.model = "leading";
        this.showPop(true);
        this.detailData = this.queryParams;
        // if (this.unitId) {
        //   // this.model = "leading";
        //   // this.showPop(true);
        //   // this.detailData = this.paramInit;
        // } else {
        //   this.$message({
        //     showClose: true,
        //     message: "请先选择参建单位"
        //   });
        // }
      },
      /** 获取项目下拉查询 */
      getProjectList() {
        listProject().then(response => {
          this.projectList = response.rows;
          if (!this.queryParams.projectId) {
            this.queryParams.projectId = response.rows[0].id;
            this.getStable();
          }
          //TODO
          // this.queryParams.projectId = response.rows[0].id
        });
      },
      /** 查询项目下参建单位*/
      getUnitList(projectId) {
        listProjectParticipatingUnit(projectId).then(res => {
          console.log(res);
          this.unitList = res.rows;
          this.queryParams.unitId = ''
          this.queryParams.teamId = ''
          this.queryParams.teamPersonId = ''
        })
      },
      /** 查询单位下班组部门*/
      getTeamList(unitId) {
        getTeam({unitId: unitId}).then(res => {
          this.teamList = res.rows;
          this.queryParams.teamId = ''
          this.queryParams.teamPersonId = ''
        })
      },
      /** 查询班组部门下人员*/
      getPersonList(teamid) {
        getPerson({teamid: teamid}).then(res => {
          this.personList = res.rows;
          this.queryParams.teamPersonId = ''
        })
      },
      /** 查询 列表 */
      getStable() {
        getStable(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
        getSdata(this.queryParams).then(res => {
          this.spreadData = res.data
          console.log(response);
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有用户数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportJob(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
    },
    watch: {
      'queryParams.projectId': {
        handler(newVal) {
          this.getUnitList(newVal)
        },
        deep: true
      },
      'queryParams.unitId': {
        handler(newVal) {
          this.getTeamList(newVal)
        },
        deep: true
      },
      'queryParams.teamId': {
        handler(newVal) {
          this.getPersonList(newVal)
        },
        deep: true
      },
    }
  }
</script>

<style scoped lang="scss">
  .access-wrap{
    padding: 0 20px;
    /deep/.el-table{
      border-radius: 0;
    }
    .query-box {
      height: 60px;
      line-height: 60px;
      display: flex;
      justify-content: space-between;
      .r-query{
        margin-left: auto;
      }
      .add-btn {
        margin-top: 14px;
      }
    }
    .tb-footer{
      border: 1px solid #dddcdc;
      border-top: 0;
      height: 42px;
      line-height: 42px;
      text-align: right;
    }
  }
</style>
