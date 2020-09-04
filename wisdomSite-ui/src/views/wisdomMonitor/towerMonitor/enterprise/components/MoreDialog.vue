<template>
  <el-dialog  :visible.sync="flag" width="900px" @close="handleClose" @open="handleOpen" :show-close="false" :close-on-click-modal="false">
    <div class="more-detail-wrap">
      <i class="el-icon-close close-btn" @click="closeDialog"></i>
      <el-tabs v-model="activeName">
        <el-tab-pane label="违章详情" name="first">
          <div class="query-box">
            <span></span>
            <div class="r-query">
              时间：
              <el-date-picker
                style="width: 220px;"
                v-model="timeRange"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              >
              </el-date-picker>
              <el-input placeholder="" v-model="wzParam.searchVal" class="input-with-select" style="width: 300px;">
                <el-select v-model="wzParam.searchKey" slot="prepend" placeholder="请选择" style="width: 120px">
                  <el-option label="产权备案号" value="propertyRightRecordNo"></el-option>
                  <el-option label="项目名称" value="projectName"></el-option>
                  <el-option label="设备名称" value="equipmentName"></el-option>
                  <el-option label="监控设备" value="monitoringEquipment"></el-option>
                </el-select>
              </el-input>
              <el-button class="add-btn" @click="role===0?getWzList():getProjWzList()">查询</el-button>
              <button type="button" class="more-btn jl-btn" @click="handleExport('wz')"><span>导出数据</span></button>
            </div>
          </div>
          <Table :tableData="wzData" :tableConfig="wzConfig" ref="table" :maxHeight="500">
          </Table>
          <Pagination
            :total="wzTotal"
            :page.sync="wzParam.pageNum"
            :limit.sync="wzParam.pageSize"
            @pagination="getWzList"
          />
        </el-tab-pane>
        <el-tab-pane v-if="role===0"  label="设备详情" name="second">
          <div class="query-box">
            <div class="r-query">
              <button type="button" class="more-btn jl-btn" style="margin-bottom: 14px" @click="handleExport('sb')"><span>导出数据</span></button>
            </div>
          </div>
          <Table :tableData="sbData" :tableConfig="sbConfig" ref="table" :maxHeight="500">
          </Table>
          <Pagination
            :total="sbTotal"
            :page.sync="sbParams.pageNum"
            :limit.sync="sbParams.pageSize"
            @pagination="getSbList"
          />
        </el-tab-pane>
        <el-tab-pane v-if="role===1" label="工作详情" name="third">
          <div class="query-box">
            <span></span>
            <div class="r-query">
              <el-input
                style="width: 200px"
                v-model="gzParam.equipmentName"
                placeholder="设备名称"
                clearable
              >
                <el-button slot="append" icon="el-icon-search" @click="getGzList"></el-button>
              </el-input>
              <button type="button" class="more-btn jl-btn" @click="handleExport('sb')"><span>导出数据</span></button>
            </div>
          </div>
          <Table :tableData="gzData" :tableConfig="gzConfig" ref="table" :maxHeight="500">
          </Table>
          <Pagination
            :total="gzTotal"
            :page.sync="gzParam.pageNum"
            :limit.sync="gzParam.pageSize"
            @pagination="getGzList"
          />
        </el-tab-pane>
      </el-tabs>
    </div>
  </el-dialog>
</template>

<script>
  import moment from 'moment';
  import {
    violationDetailsE,
    violationDetailsP,
    deviceDetailsE,
    getGzListP,
    exportWzDetailE,
    exportWzDetailP,
    exportSbDetailE,
    exportGzDetailP
  } from "@/api/smartMonitor/tower";
  import Table from "@/components/Table";
  import Pagination from "@/components/Pagination";
  export default {
    name: "MoreDialog",
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      role: {
        type: Number,
      },
      projectId: {
        type: [Number, String]
      },
      companyId: {
        type: [Number, String],
      },
      queryDate: {
        type: String,
      }
    },
    components: {
      Table,
      Pagination
    },
    data() {
      return {
        activeName: 'first',
        timeRange: ['2020-08-01', '2020-08-22'],
        wzParam: {
          pageNum: 1,
          pageSize: 10,
          searchKey: 'propertyRightRecordNo',
          searchVal: '',
        },
        wzConfig: [
          { prop: "companyName", label: "所属公司" },
          { prop: "violationTime", label: "违章时间", width: "150px" },
          { prop: "violationContent", label: "违章内容" },
          { prop: "projectName", label: "项目名称" },
          { prop: "propertyRightRecordNo", label: "产权备案号" },
          { prop: "equipmentName", label: "设备名称" },
          { prop: "monitoringEquipment", label: "监控设备(SN)" },
          // { slot: "onlineType" },
        ],
        wzTotal: 0,
        wzData: [],

        sbConfig: [
          { prop: "monitoringOnlineRate", label: "监控在线率(%)" },
          { prop: "companyOrprojectName", label: "所属公司/项目" },
          { prop: "towerCraneEquipmentNum", label: "塔吊设备(台)" },
          { prop: "towerCraneMonitoringEquipmentNum", label: "监控设备(台)" },
          { prop: "monitoringOnlineNum", label: "在线监控设备(台)" },
        ],
        sbTotal: 0,
        sbData: [],

        gzParam: {
          pageNum: 1,
          pageSize: 10,
          equipmentName: '',
        },
        gzConfig: [
          { prop: "liftingDistance", label: "吊程(m)" },
          { prop: "startTime", label: "开始时间", width: "150px" },
          { prop: "equipmentName", label: "设备名称" },
          { prop: "workingHours", label: "工作时长" },
          { prop: "liftingWeight", label: "吊重(t)" },
        ],
        gzTotal: 0,
        gzData: [],
      }
    },
    created() {
    },
    methods: {
      /** 获取违章列表*/
      async getWzList() {
       const { rows, total } = await violationDetailsE(this.wzParams);
       rows.map(ele => {
         ele.violationTime = moment(new Date(ele.violationTime)).format('YYYY-MM-DD HH:mm:ss')
       })
       this.wzData = rows;
       this.wzTotal = total;
      },
      /** 获取项目违章列表*/
      async getProjWzList() {
        const { rows, total } = await violationDetailsP(this.wzParams);
        rows.map(ele => {
          ele.violationTime = moment(new Date(ele.violationTime)).format('YYYY-MM-DD HH:mm:ss')
        })
        this.wzData = rows;
        this.wzTotal = total;
      },
      /** 获取设备列表*/
      async getSbList() {
        const { rows, total } = await deviceDetailsE(this.sbParams);
        this.sbData = rows;
        this.sbTotal = total;
      },
      /** 获取工作列表*/
      async getGzList() {
        const { rows, total } = await getGzListP(this.gzParams);
        this.gzData = rows;
        this.gzTotal = total;
      },
      /* 关闭 */
      closeDialog() {
        this.$emit('update:dialogFlag', false);
      },
      handleExport(type) {
        const _this = this;
        this.$confirm('是否确认导出数据？', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          switch (type) {
            case 'wz':
              return _this.role === 0 ? exportWzDetailE(_this.wzParams) : exportWzDetailP(_this.wzParams);
            case 'sb':
              return exportSbDetailE(_this.sbParams);
            case 'gz':
              return exportGzDetailP(_this.gzParams);
          }
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      handleOpen() {
        console.log(this.companyId)
        if (this.role === 0) {
          this.getWzList();
          this.getSbList();
        } else {
          this.getProjWzList();
          this.getGzList();
        }
      },
      handleClose() {
        console.log('弹框关闭了哦')
      }
    },
    computed: {
      flag: {
        get() {
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
        },
      },
      wzParams() {
        const { pageNum, pageSize } = this.wzParam;
        let params = {
          pageNum,
          pageSize,
          startTime: this.timeRange[0],
          endTime: this.timeRange[1],
          [this.wzParam.searchKey]: this.wzParam.searchVal
          // projectName: '',
          // propertyRightRecordNo: '',
          // equipmentName: '',
          // monitoringEquipment: '',
        }
        this.role === 0 ? params.companyId = this.companyId : params.projectId = this.projectId
        return params
      },
      sbParams() {
        return {
          pageNum: 1,
          pageSize: 10,
          companyId: this.companyId
        }
      },
      gzParams() {
        return {
          ...this.gzParam,
          projectId: this.projectId,
        }
      }
    },
  }
</script>

<style scoped lang="scss">
  /deep/.el-dialog__header{
    border-bottom: 0;
  }
  /deep/.el-dialog__body{
    padding-top: 0;
    margin-top: -50px;
    .input-with-select .el-input-group__prepend {
      background-color: #fff;
    }
  }
  .query-box{
    margin: 15px 0;
  }
  .more-detail-wrap{
    .close-btn{
      float: right;
      cursor: pointer;
      position: absolute;
      top: 20px;
      right: 20px;
      font-size: 16px;
      z-index: 99;
    }
    .more-btn{
      color: #00b589;
      border-color: #00b589;
      border-radius: 0;
      float: right;
    }
    /deep/.el-tabs__item{
      font-size: 14px !important;
      font-weight: normal;
      color: #666;
    }
    /deep/.el-tabs__nav-wrap::after{
      height: .5px;
    }
  }
</style>
