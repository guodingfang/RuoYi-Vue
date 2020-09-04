<template>
  <div class="access-wrap">
    <div class="query-box">
      全部（{{ total }}人）
      <div class="r-query">
        <el-select
          placeholder="项目名称"
          v-model="queryParams.projectId"
          style="width: 180px; margin-right: 20px"
          @change="getDtable()"
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
          @change="getDtable()"
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
          @change="getDtable()"
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
          style="width: 180px"
          @change="getDtable()"
          clearable
        >
          <el-option
            v-for="item in personList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </div>
    </div>
    <div class="funBtn">
      <el-button type="primary" @click="addModel">考勤机授权</el-button>
      <el-button type="primary" @click="relieve">考勤机解绑</el-button>
    </div>
    <div class="table-wrap">
      <el-table :data="tableData" style="width: 100%" class="month_table" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column prop="name" align="left" label="姓名" />
        <el-table-column prop="idNumber" align="left" label="身份证" />
        <el-table-column prop="mobilePhone" align="left" label="手机号" />
        <el-table-column  label="职务/权限" align="left" prop="personType" :show-overflow-tooltip="true" >
          <template slot-scope="scope">
            <span v-if="scope.row.personType == 0">建筑工人</span>
            <span v-else>管理人员</span>
          </template>
        </el-table-column>
        <el-table-column label="工种/岗位" align="left" prop="workTypeName" :show-overflow-tooltip="true" />
        <el-table-column label="班组/部门" align="left" prop="teamName" :show-overflow-tooltip="true" />
        <el-table-column label="参建单位" align="left" prop="unitName" :show-overflow-tooltip="true" />
        <el-table-column label="项目名称" align="left" prop="projectName" :show-overflow-tooltip="true" />
        <el-table-column label="授权状态" align="left" prop="participatingUnitName" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>成功（{{scope.row.authNumber}}）</span>
            <span>失败（{{scope.row.unauthNumber}}）</span>
          </template>
        </el-table-column>
        <el-table-column label="明细" align="left" width="90">
          <template slot-scope="scope">
            <el-button type="text" @click="detailDance(scope.row)">查看</el-button>
            <!-- scope.row -->
          </template>
        </el-table-column>
      </el-table>
    </div>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getDtable"
    />
    <component
      :is="model"
      v-if="popShow"
      :detailData='detailData'
      :guids="guids"
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
  import {
    getDtable,
    addDance,
    relieveDance,
    detailDance,
  } from "@/api/people/authorize";
  import Table from '@/components/Table';
  import addAuth from './add-authorize';
  import detailAuth from './detail-authorize';
  export default {
    name: 'authorize',
    components: {
      Table,
      addAuth,
      detailAuth,
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
        tableSelection: [],
        guids: [],
      }
    },
    computed: {
      tableConfig() {
        return this.tableData[0] && this.tableData[0].manHours
      }
    },
    created() {
      this.getDtable();
      this.getProjectList();
    },
    methods: {
      showModel(flag) {
        this.popShow = flag
      },
      closePop(flag) {
        this.showModel(false)
        if (flag) {
          this.getDtable()
        }
      },
      addModel() {
        if (this.tableSelection && this.tableSelection.length > 0) {
          this.guids = (this.tableSelection.map(item => item.guid))
          this.model = "addAuth";
          this.state = 'add'
          this.showModel(true)
        } else {
          this.$message('请勾选人员');
        }
      },
      async relieve() {
        if (this.tableSelection && this.tableSelection.length > 0) {
          let guids = (this.tableSelection.map(item => item.guid)).join(',')
          return new Promise(res => {
            this.$confirm("是否要解绑所选人员绑定的考勤机么?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            })
              .then(async () => {
                console.log(guids);
                const re = await relieveDance(guids);
                if (re.code === 200) {
                  this.$message({
                    message: re.msg,
                    type: "success",
                  });
                  this.getDtable();
                }
              })
              .catch(() => {
                this.$message({
                  type: "info",
                  message: "已取消"
                });
              });
          });
        } else {
          this.$message('请勾选人员');
        }

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
      getDtable() {
        getDtable(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      /* detailAtte */
      async detailDance(item) {
        const {id,guid} = item
        const re = await detailDance(id)
        this.detailData = {
          ...re.data,
          guid: guid
        }
        this.model = 'detailAuth'
        this.showModel(true)
      },
      handleSelectionChange(val) {
        this.tableSelection = val
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
    .funBtn{
      margin: 16px 0;
    }
    .query-box{
      height: 60px;
      line-height: 60px;
      display: flex;
      border-bottom: 1px solid #dddcdc;
      .flex-basis{
        margin-right: 20px;
      }
      /*justify-content: space-between;*/
      .add-btn{
        margin-top: 14px;
      }
      .r-query{
        margin-left: auto;
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
