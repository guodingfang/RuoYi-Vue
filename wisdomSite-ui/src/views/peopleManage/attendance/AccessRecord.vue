<template>
  <div class="access-wrap">
    <div class="query-box">
      <el-select
        placeholder="项目名称"
        v-model="queryParams.projectId"
        style="width: 180px; margin-right: 20px"
        @change="getAccessRecordList()"
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
        style="width: 180px; margin-right: 20px"
        @change="getAccessRecordList()"
        clearable
      >
        <!-- <el-option label="全部参建单位" value=""/> -->
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
        style="width: 180px; margin-right: 20px"
        @change="getAccessRecordList()"
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
        style="width: 180px; margin-right: 20px"
        @change="getAccessRecordList()"
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
        v-model="queryParams.attenDate"
        @change="getAccessRecordList"
        value-format="yyyy-MM-dd"
      />
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
      @pagination="getAccessRecordList"
    />
  </div>
</template>

<script>
  import moment from 'moment';
  import {
    getAccessRecordList,
    listProject,
    listProjectParticipatingUnit,
    exportAccessRecord
  } from "@/api/people/attendance";
  import {
    getTeam,
    getPerson,
    detailAtte,
  } from "@/api/people/checkwork";
  import Table from '@/components/Table';
  export default {
    name: 'AccessRecord',
    components: {
      Table,
    },
    data() {
      return{
        tableConfig: [
          { prop: 'machineName', label: '考勤设备', width: ''},
          { prop: 'personName', label: '姓名' },
          { prop: 'idNumber', label: '身份证号', width: '200px'},
          { prop: 'unitName', label: '参建单位', width: ''},
          { prop: 'teamName', label: '班组/部门', width: ''},
          { prop: 'workTypeName', label: '工种/岗位', width: ''},
          { prop: 'mobilePhone', label: '电话号码', width: ''},
          { prop: 'attendanceTime', label: '时间', width: ''},
          { prop: 'direction', label: '方向', width: ''},
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
          attenDate: moment().format("yyyy-MM-DD"),
          // projectId: '',
          // unitId: '',
        },
        projectList: [],
        unitList: [],
        teamList: [],
        personList: [],
        projName: '',
        timeRange: '',
        detailData: {},
        engineeringForm: {},
        dialogFlag: false,
        verifyFlag: false,
        isCreate: true,
        operateId: '',
        title: '',
      }
    },
    created() {
      // this.getAccessRecordList();
      this.getProjectList();
    },
    methods: {
      /** 获取项目下拉查询 */
      getProjectList() {
        listProject().then(response => {
          this.projectList = response.rows;
          if (!this.queryParams.projectId) {
            this.queryParams.projectId = response.rows[0].id;
            this.getAccessRecordList();
          }
          //TODO
          // this.queryParams.projectId = response.rows[0].id
        });
      },
      /** 查询项目下参建单位*/
      getUnitList(projectId) {
        listProjectParticipatingUnit(projectId).then(res => {
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
      getAccessRecordList() {
        getAccessRecordList(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      /** 打开新增框*/
      openVerifyDialog(id) {
        this.operateId = Number(id);
        this.verifyFlag = true;
      },
      /** 打开详情框*/
      openDetailDialog(id) {
        Promise.all([checkManagerDetail(id), checkManagerDetailList(id)]).then(res => {
          this.detailData = {
            ...res[0].data,
            list: res[1].data
          };
          this.dialogFlag = true;
        }).catch(err => {
          this.msgError(err);
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
          return exportAccessRecord(queryParams);
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
    /deep/.el-table{
      border-radius: 0;
    }
    .query-box{
      height: 60px;
      line-height: 60px;
      display: flex;
      .flex-basis{
        margin-right: 20px;
      }
      /*justify-content: space-between;*/
      .add-btn{
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
