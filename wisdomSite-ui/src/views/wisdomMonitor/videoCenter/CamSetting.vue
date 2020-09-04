<template>
  <div>
    <div class="query-box">
      <span>全部（{{ tableData.length }}）</span>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:enableStatus>
        <el-table-column
          label="启用禁用"
          width="100"
        >
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.enabled"
              active-value="1"
              inactive-value="2"
              @change="setCamStatus(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <el-button @click="openLive(scope.row)" type="text">查看</el-button>
            <el-button @click="openEdit(scope.row)" type="text">编辑</el-button>
            <el-button @click="openDel(scope.row)" type="text">删除</el-button>
          </template>
        </el-table-column>
      </template>
    </Table>
<!--    编辑与转场弹框-->
    <add-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="editDialogFlag"
      :camForms.sync="detailData"
      @query="getCamList"
    />
    <live-dialog
      :dialogFlag.sync="liveDialogFlag"
      :liveInfo.sync="liveInfo"
    />
  </div>
</template>

<script>
  import {
    getCamList,
    setCamStatus,
    delMoniPoint
  } from "@/api/smartMonitor/video";
  import { getOnlineVideo } from "@/api/system/device";
  import Table from "@/components/Table";
  import Pagination from "@/components/Pagination";
  import AddDialog from "./components/AddDialog";
  import LiveDialog from "./components/LiveDialog";
  // import WisDialog from "./WisDialog";
  export default {
    name: "CamSetting",
    components: {
      LiveDialog,
      Table,
      Pagination,
      AddDialog
    },
    data() {
      return {
        tableConfig: [
          { slot: 'operate'},
          { prop: "videoMonitoryPointName", label: "视频监控点" },
          { prop: "videoSerialNumber", label: "监控设备" },
          { prop: "zoneName", label: "区域名称" },
          { slot: "enableStatus" },
        ],
        tableData: [],
        projectList: [],
        projName: "",
        timeRange: "",
        detailData: {},
        engineeringForm: {},
        liveInfo: {},
        liveDialogFlag: false,
        editDialogFlag: false,
        isCreate: false,
        isMove: false,
        title: ""
      };
    },
    created() {
      this.getCamList();
    },
    methods: {
      /** 获取项目下拉查询 */
      getProjectList() {
        listProject().then(response => {
          this.projectList = response.rows;
        });
      },
      /** 查询 列表 */
      getCamList() {
        getCamList({
          projectId: this.$store.state.videoMoni.videoProject.projectId
        }).then(response => {
          this.tableData = response.data;
        });
      },
      /** 打开编辑框*/
      openEdit(rowData) {
        this.detailData = rowData;
        this.editDialogFlag = true;
      },
      /** 打开查看框*/
      openLive(rowData) {
        const { equipmentSerialnumber, channelNumber } = rowData.video;
        this.liveInfo = {equipmentSerialnumber, channelNumber}
        this.liveDialogFlag = true;
        // getOnlineVideo({ ...rowData.video, id: '' }).then(res => {
        //   this.liveInfo = {...rowData, palyUrl: res.msg}
        //   this.liveDialogFlag = true;
        // })
      },
      /** 删除操作*/
      openDel(rowData) {
        this.$confirm('确定要删除吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          delMoniPoint(rowData.id).then(response => {
            this.getCamList();
            this.msgSuccess("删除成功");
          });
        }).catch(function() {});
      },
      setCamStatus(row) {
        setCamStatus({
          id: row.id,
          enabled: row.enabled,
          videoSerialNumber: row.videoSerialNumber,
          videoChannel: row.videoChannel,
        }).then(res => {
          this.getCamList()
        }).catch(err => {
          console.log(err)
        })
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
