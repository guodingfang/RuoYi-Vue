<template>
  <div>
    <div class="query-box">
      <span>全部（{{ total }}）</span>
      <div class="r-query">
        <div class="sel"> <project-select :formProjId.sync="queryParams.projectId" type="all"/></div>
        <el-select
          placeholder="启用状态"
          v-model="queryParams.enableStatus"
          style="width: 100px"
          clearable
        >
          <el-option
            v-for="item in enableStatus"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue"
          />
        </el-select>
<!--        <el-select
          placeholder="运行状态"
          v-model="queryParams.onlineStatus"
          style="width: 100px"
          clearable
        >
          <el-option
            v-for="item in onlineType"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue"
          />
        </el-select>-->
        <el-select
          placeholder="设备类型"
          v-model="queryParams.equipmentType"
          style="width: 100px"
          clearable
        >
          <el-option
            v-for="item in deviceType"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue"
          />
        </el-select>
        <el-input
          style="width: 160px"
          v-model="queryParams.serialNumber"
          placeholder="设备编号"
          clearable
        ></el-input>
        <el-button type="primary" class="add-btn" @click="getDeviceList"
        >查询</el-button>
        <el-button class="add-btn" @click="resetFilter"
        >重置</el-button>
      </div>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:equipmentType>
        <el-table-column
          label="设备类型"
        >
          <template slot-scope="scope">
            {{selectDictLabel(deviceType, scope.row.equipmentType)}}
          </template>
        </el-table-column>
      </template>
      <template v-slot:onlineType>
        <el-table-column
          label="运行状态"
        >
          <template slot-scope="scope">
            {{selectDictLabel(onlineType, scope.row.onlineStatus)}}<i class="el-icon-refresh-right"  @click="getDeviceList"></i>
          </template>
        </el-table-column>
      </template>
      <template v-slot:enableStatus>
        <el-table-column
          label="启用状态"
        >
          <template slot-scope="scope">
            {{selectDictLabel(enableStatus, scope.row.enableStatus)}}
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <el-button @click="openEdit(scope.row)" type="text">编辑</el-button>
            <el-button @click="openMove(scope.row)" type="text">转场</el-button>
            <el-button @click="openDel(scope.row)" type="text">删除</el-button>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getDeviceList"
    />
    <!--编辑与转场弹框-->
    <wis-dialog
      :isCreate="isCreate"
      :isMove.sync="isMove"
      :dialogFlag.sync="wisDialogFlag"
      :wisForms.sync="detailData"
      @query="getDeviceList"
    />
  </div>
</template>

<script>
  import {
    getDeviceList,
    listProject,
    delDevice
  } from "@/api/system/device";
  import ProjectSelect from "@/components/CommonSelect/ProjectSelect";
  import Table from "@/components/Table";
  import Pagination from "@/components/Pagination";
  import WisDialog from "./WisDialog";
  export default {
    name: "WisDevice",
    components: {
      Table,
      Pagination,
      ProjectSelect,
      WisDialog
    },
    data() {
      return {
        tableConfig: [
          { slot: 'operate'},
          { prop: "id", label: "序号", width: '50px' },
          { prop: "installationPosition", label: "安装位置" },
          { slot: "equipmentType" },
          { prop: "serialNumber", label: "设备编号" },
          { prop: "projectName", label: "所属工程" },
          { prop: "enterpriseName", label: "施工单位"},
          { slot: "onlineType" },
          { slot: "enableStatus" },
        ],
        total: 0,
        tableData: [],
        //项目查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          serialNumber: '',
          equipmentType: '',
          onlineStatus: '',
          projectId: '',
          enableStatus: '',
        },
        deviceType: [],
        onlineType: [{dictValue: 1, dictLabel:'在线'}, {dictValue: 2, dictLabel:'离线'}],
        enableStatus: [{dictValue: 0, dictLabel:'未启用'}, {dictValue: 1, dictLabel:'启用'}, {dictValue: 2, dictLabel:'禁用'}],
        projectList: [],
        projName: "",
        timeRange: "",
        detailData: {},
        engineeringForm: {},
        wisDialogFlag: false,
        addDialogFlag: false,
        isCreate: false,
        isMove: false,
        title: ""
      };
    },
    created() {
      this.getDicts("ws_equipment_type").then(response => {
        this.deviceType = response.data;
      });
      this.getDeviceList();
    },
    methods: {
      /** 获取项目下拉查询 */
      getProjectList() {
        listProject().then(response => {
          this.projectList = response.rows;
        });
      },
      /** 查询 列表 */
      getDeviceList() {
        getDeviceList(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      /** 打开编辑框*/
      openEdit(rowData) {
        this.detailData = rowData;
        this.wisDialogFlag = true;
      },
      /** 打开转场框*/
      openMove(rowData) {
        if ( rowData.enableStatus !== 0) {
          this.msgError('该设备已被使用，请先联系客户删除')
          return false;
        }
        this.detailData = rowData;
        this.isMove = true;
        this.wisDialogFlag = true;
      },
      /** 删除操作*/
      openDel(rowData) {
        if ( rowData.enableStatus !== 0) {
          this.msgError('该设备已被使用，请先联系客户删除')
          return false;
        }
        this.$confirm('是否确认删除该设备?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          delDevice(rowData.id).then(response => {
            this.getDeviceList();
            this.msgSuccess("删除成功");
          });
        }).catch(function() {});
      },
      resetFilter() {
        this.queryParams = {
          pageNum: 1,
          pageSize: 10,
          serialNumber: '',
          equipmentType: '',
          onlineStatus: '',
          projectId: '',
          enableStatus: '',
        }
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
    .sel{
      display: inline-block;
    }
    .add-btn {
      margin-top: 14px;
    }
  }
</style>
