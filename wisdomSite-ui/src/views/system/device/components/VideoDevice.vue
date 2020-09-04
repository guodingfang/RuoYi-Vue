<template>
  <div>
    <div class="query-box">
      <span>全部（{{ total }}）</span>
      <div class="r-query">
        <div class="sel"> <project-select type="all" :formProjId.sync="queryParams.projectId"/></div>
<!--        <el-select
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
        </el-select>-->
        <el-input
          style="width: 160px"
          v-model="queryParams.equipmentSerialnumber"
          placeholder="设备序列号"
          clearable
        ></el-input>
        <el-button type="primary" class="add-btn" @click="getCamearList">查询</el-button>
        <el-button class="add-btn" @click="resetFilter">重置</el-button>
      </div>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig" :lazy="true" :load="load" ref="table">
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
            {{selectDictLabel(onlineType, scope.row.onlineStatus)}}<i class="el-icon-refresh-right"  @click="getCamearList"></i>
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
        <el-table-column label="操作" align="left" width="240px">
          <template slot-scope="scope">
            <el-button v-if="scope.row.equipmentType===2 " @click="openChannel(scope.row)" type="text">设置通道</el-button>
            <el-button v-if="scope.row.equipmentType===2" @click="expandChannel(scope.row, $event)" type="text">查看通道</el-button>
            <el-button v-if="scope.row.equipmentType===1 || !scope.row.equipmentType" @click="openLive(scope.row)" type="text">查看</el-button>
            <el-button v-if="!!scope.row.equipmentType" @click="openEdit(scope.row)" type="text">编辑</el-button>
            <el-button v-if="scope.row.equipmentType===1" @click="openMove(scope.row)" type="text">转场</el-button>
            <el-button v-if="!!scope.row.equipmentType" @click="openDel(scope.row)" type="text">删除</el-button>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getCamearList"
    />
    <!--编辑与转场弹框-->
    <video-dialog
      :isCreate="isCreate"
      :isMove.sync="isMove"
      :dialogFlag.sync="camDialogFlag"
      :camForms.sync="detailData"
      @query="getCamearList"
    />
    <channel-dialog
      :dialogFlag.sync="channelDialogFlag"
      :channForms.sync="channelData"
      @query="getCamearList"
    />
    <live-dialog
      :dialogFlag.sync="liveDialogFlag"
      :liveInfo.sync="liveInfo"
    />
  </div>
</template>

<script>
  import {
    getCamearList,
    delCamear,
    getOnlineVideo,
    getCameraVideo
  } from "@/api/system/device";
  import ProjectSelect from "@/components/CommonSelect/ProjectSelect";
  import Table from "@/components/Table";
  import Pagination from "@/components/Pagination";
  import VideoDialog from "./VideoDialog";
  import LiveDialog from "./LiveDialog";
  import ChannelDialog from "./ChannelDialog";
  export default {
    name: "VideoDevice",
    components: {
      ChannelDialog,
      LiveDialog,
      Table,
      Pagination,
      ProjectSelect,
      VideoDialog
    },
    data() {
      return {
        tableConfig: [
          { slot: 'operate'},
          { prop: "equipmentSerialnumber", label: "设备序列号", width: '150px' },
          { prop: "channelNumber", label: "通道编号" },
          { prop: "installationPosition", label: "安装位置" },
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
          equipmentSerialnumber: '',
          equipmentType: '',
          onlineStatus: '',
          projectId: '',
          enableStatus: '',
        },
        deviceType: [{dictValue: 1, dictLabel:'摄像仪'}, {dictValue: 2, dictLabel:'录像机'}],
        onlineType: [{dictValue: 1, dictLabel:'在线'}, {dictValue: 0, dictLabel:'离线'}],
        enableStatus: [{dictValue: 0, dictLabel:'未启用'}, {dictValue: 1, dictLabel:'启用'}, {dictValue: 2, dictLabel:'禁用'}],
        projectList: [],
        projName: "",
        timeRange: "",
        detailData: {},
        liveInfo: {},
        channelData: {},
        liveDialogFlag: false,
        engineeringForm: {},
        addDialogFlag: false,
        camDialogFlag: false,
        isCreate: false,
        isMove: false,
        title: "",
        channelDialogFlag: false,
      };
    },
    created() {
      this.getCamearList();
    },
    methods: {
      /** 加载子节点 */
      load(tree, treeNode, resolve) {
        getCameraVideo(tree).then(res => {
          //下面的操作是为了给每个通道加上序列号
            res.data.wsSetupInfoList.map(e => {
              e.equipmentSerialnumber = res.data.equipmentSerialnumber;
              e.id = e.installationPosition
            })
            resolve(res.data.wsSetupInfoList)
        })
      },
      /** 获取项目下拉查询 */
      getProjectList() {
        listProject().then(response => {
          this.projectList = response.rows;
        });
      },
      expandChannel(row, e) {
        // TODO
        // console.log(document.querySelector('.current-row .el-table__expand-icon'))
        // console.log(e)
        this.$refs.table.$refs.tableData.toggleRowExpansion(row)
      },
      /** 查询 列表 */
      getCamearList() {
        getCamearList(this.queryParams).then(response => {
          response.rows.map(e => {
            e.equipmentType ===2 ? e.hasChildren = true : false
          })
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      /** 打开查看框*/
      openLive(rowData) {
        const { equipmentSerialnumber, channelNumber } = rowData;
        this.liveInfo = {equipmentSerialnumber, channelNumber}
        console.log(this.liveInfo);
        this.liveDialogFlag = true;
        // getOnlineVideo({...rowData, id: '', playMode: 'hls'}).then(res => {
        //   this.liveInfo = {...rowData, palyUrl: res.msg}
        //   this.liveDialogFlag = true;
        // })
      },
      /** 打开编辑框*/
      openEdit(rowData) {
        this.detailData = rowData;
        this.camDialogFlag = true;
      },
      /** 打开通道框*/
      openChannel(rowData) {
        getCameraVideo(rowData).then(res => {
          this.channelData = res.data;
        })
        this.channelDialogFlag = true;
      },
      /** 打开转场框*/
      openMove(rowData) {
        if ( rowData.enableStatus !== 0) {
          this.msgError('该设备已被使用，请先联系客户删除')
          return false;
        }
        this.detailData = rowData;
        this.isMove = true;
        this.camDialogFlag = true;
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
          delCamear(rowData.equipmentSerialnumber).then(response => {
            this.getCamearList();
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
