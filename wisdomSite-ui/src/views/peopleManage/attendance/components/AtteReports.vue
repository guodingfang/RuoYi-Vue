<template>
  <div class="access-wrap">
    <div class="query-box">
      <el-select
        placeholder="项目名称"
        v-model="queryParams.projectId"
        style="width: 180px; margin-right: 20px"
        @change="getAtteReport()"
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
        @change="getAtteReport()"
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
        @change="getAtteReport()"
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
        @change="getAtteReport()"
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
        v-model="queryParams.yearAndMonth"
        type="month"
        style="width: 180px; margin-right: 20px"
        @change="getAtteReport"
        value-format="yyyy-MM"
        placeholder="选择月"
      />
      <el-select clearable style="width: 180px; margin-right: 20px" v-model="queryParams.workType" placeholder="工种/岗位">
        <el-option
          v-for="item in $DStore.state.getWorkType"
          :key="item.dictValue"
          :label="item.dictLabel"
          :value="item.dictValue"
        >
        </el-option>
      </el-select>
      <el-select clearable v-model="queryParams.flag">
        <el-option label="按考勤统计" value="1"></el-option>
        <el-option label="按工时统计" value="2"></el-option>
      </el-select>
    </div>
    <div class="table-wrap">
    <el-table :data="tableData" style="width: 100%" class="month_table">
      <el-table-column prop="name" align="left" label="姓名" />
      <el-table-column prop="workTypeName" align="left" label="工种" />
      <el-table-column
        align="left"
        v-for="(item, index) in tableConfig"
        :key="index + 1"
        width="36"
        :label="String(index + 1)"
        class=""
      >
        <template slot-scope="scope">
          <span v-if="queryParams.flag == '1'">
            <i v-if="scope.row.manHours[index] || scope.row.manHours[index] == 0" class="el-icon-check icon-cell" />
          </span>
          <span v-else> {{ scope.row.manHours[index] }} </span>

        </template>
      </el-table-column>
      <el-table-column prop="totalAttendanceDays" align="left" label="合计(天)" />
      <el-table-column prop="totalAttendanceHours" align="left" label="当月在线时长" />
      <el-table-column label="明细" align="left" width="90">
        <template slot-scope="scope">
          <el-button type="text" @click="detailAtte(scope.row.id)">查看</el-button>
          <!-- scope.row -->
        </template>
      </el-table-column>
    </el-table>
      <div class="tb-footer"><el-button type="text" @click="handleExport">导出数据</el-button>&emsp;</div>
    </div>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getAtteReport"
    />
    <component
      :is="model"
      v-if="popShow"
      :detailData='detailData'
      :is-show="popShow"
      @on-close="closePop"
    />
  </div>
</template>

<script>
  import moment from 'moment';
  import {
    getAtteReport,
    listProject,
    listProjectParticipatingUnit,
  } from "@/api/people/attendance";
  import {
    getTeam,
    getPerson,
    detailAtte,
    exportAccessRecord
  } from "@/api/people/checkwork";
  import Table from '@/components/Table';
  import detailAtteRe from './detailAtte';
  export default {
    name: 'AtteReports',
    components: {
      Table,
      detailAtteRe,
    },
    data() {
      return{
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
          yearAndMonth: this.$dayjs(new Date()).startOf('month').format('YYYY-MM'),
          flag: '1'
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
        model: null,
        popShow: false,
      }
    },
    computed: {
      tableConfig() {
        return this.tableData[0] && this.tableData[0].manHours
      }
    },
    created() {
      this.getAtteReport();
      this.getProjectList();
      console.log(this.queryParams.yearAndMonth);
    },
    methods: {
      showModel(flag) {
        this.popShow = flag
      },
      closePop() {
        this.showModel(false)
      },
      /** 获取项目下拉查询 */
      getProjectList() {
        listProject().then(response => {
          this.projectList = response.rows;
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
      getAtteReport() {
        getAtteReport(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      /* detailAtte */
      async detailAtte(id) {
        // const re = await detailAtte(id,{yearAndMonth:this.queryParams.yearAndMonth})
        const re = await detailAtte(id,{yearAndMonth:this.queryParams.yearAndMonth})
        this.detailData = re.data
        console.log(this.detailData);
        this.model = 'detailAtteRe'
        this.showModel(true)
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有用户数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportAccessRecord(queryParams.flag,queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
    },
    watch: {
      'queryParams.projectId': {
        handler(newVal) {
          this.getUnitList(newVal)
          // this.queryParams.unitId = null
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
  ::v-deep{
    .month_table {
      .cell{
        padding: 0;
      }
      .icon-cell{
        color: rgb(9, 161, 123);
        font-size: 16px;
        font-weight: bold;
      }
    }
  }
</style>
