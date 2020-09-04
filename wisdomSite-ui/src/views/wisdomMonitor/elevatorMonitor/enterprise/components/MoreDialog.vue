<template>
  <el-dialog  :visible.sync="flag" width="1000px" @close="handleClose" @open="handleOpen" :show-close="false" :close-on-click-modal="false">
    <div class="more-detail-wrap">
      <i class="el-icon-close close-btn" @click="closeDialog"></i>
      <el-tabs v-model="activeName">
        <el-tab-pane label="违章详情" name="first">
          <div class="query-box">
            <span></span>
            <div class="r-query">
              设备类型：
              <el-select size="mini"  style="width: 80px" v-model="wzParam.deviceType" placeholder="请选择">
                <el-option label="全部" value="" />
                <el-option label="单笼" :value="1" />
                <el-option label="双笼" :value="2" />
              </el-select>
              告警类型：
              <el-select size="mini" style="width: 80px" v-model="wzParam.alarmingType" placeholder="请选择">
                <el-option label="预警" :value="1" />
                <el-option label="告警" :value="2" />
              </el-select>
              时间：
              <el-date-picker
                size="mini"
                style="width: 220px;"
                v-model="timeRange"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              >
              </el-date-picker>
              <el-select size="mini" style="width: 100px" v-model="wzParam.keyType" placeholder="请选择">
                <el-option label="项目名称" :value="1" />
                <el-option label="备案号" :value="2" />
                <el-option label="设备名称" :value="3" />
                <el-option label="全部" :value="4" />
              </el-select>
              <el-input
                size="mini"
                style="width: 110px"
                v-model="wzParam.keyWordName"
                placeholder=""
                clearable
              ></el-input>
              <el-button class="add-btn" size="mini" @click="getWzList">查询</el-button>
              <button size="mini" type="button" class="more-btn jl-btn" @click="handleExport('wz')"><span>导出数据</span></button>
            </div>
          </div>
          <Table :tableData="wzData" :tableConfig="wzConfig" ref="table">
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
            <span></span>
            <div class="r-query">
              <button type="button" class="more-btn jl-btn" @click="handleExport('sb')"><span>导出数据</span></button>
            </div>
          </div>
          <Table :tableData="sbData" :tableConfig="sbConfig" ref="table">
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
              设备类型：
<!--              <el-select size="mini"  style="width: 80px" v-model="gzParam.keyType" placeholder="请选择">
                <el-option label="单笼" :value="1" />
                <el-option label="双笼" :value="2" />
              </el-select>-->
              <el-input
                size="mini"
                style="width: 200px"
                v-model="gzParam.keyWordName"
                placeholder="请输入..."
                clearable
              >
                <el-button slot="append" icon="el-icon-search" @click="getGzList"></el-button>
              </el-input>
              <button type="button" class="more-btn jl-btn" @click="handleExport('gz')"><span>导出数据</span></button>
            </div>
          </div>
          <Table :tableData="gzData" :tableConfig="gzConfig" ref="table">
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
    illegalsListE,
    deviceListE,
    getWorkList,

    violationDetailsE,
    violationDetailsP,
    deviceDetailsE,
    getGzListP,
    exportWzDetailE,
    exportSbDetailE,
    exportGzDetailP
  } from "@/api/smartMonitor/elevator";
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
      },
      days: {
        type: Array,
        default: () => []
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
          alarmingType: '', //1=预警，2=告警
          deviceType: '', //单笼=1，双笼=2
          keyWordName: '', //关键字名称
          keyType: 2, //项目名称=1，备案号=2，设备名称=3，全部=4
          areaCode: '', //区域
        },
        wzConfig: [
          { prop: "companyName", label: "所属公司" },
          { prop: "violationTime", label: "违章时间", width: "150px" },
          { prop: "warningTag", label: "违章内容" },
          { prop: "projectName", label: "项目名称" },
          { prop: "recordNumber", label: "产权备案号" },
          { prop: "deviceName", label: "设备名称" },
          { prop: "serialNumber", label: "监控设备(SN)" },
          // { slot: "onlineType" },
        ],
        wzTotal: 0,
        wzData: [],

        sbParam: {
          pageNum: 1,
          pageSize: 10,
        },
        sbConfig: [
          { prop: "monitoringOnlineRate", label: "监控在线率(%)" },
          { prop: "projectName", label: "项目名称" },
          { prop: "deviceNumber", label: "升降机设备(台)" },
          { prop: "totalMonitorDeviceNumber", label: "监控设备(台)" },
          { prop: "onlineMonitorDeviceNumber", label: "在线监控设备(台)" },
        ],
        sbTotal: 0,
        sbData: [],

        gzParam: {
          pageNum: 1,
          pageSize: 10,
        },
        gzConfig: [
          { prop: "weight", label: "吊重(kg)" },
          { prop: "sTime", label: "开始时间", width: "150px" },
          { prop: "deviceName", label: "设备名称" },
          { prop: "minutes", label: "工作时长(分钟)" },
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
        const { rows, total } = await illegalsListE(this.wzParams);
        rows.map(ele => {
          ele.violationTime = moment(new Date(ele.insertTime)).format('YYYY-MM-DD HH:mm:ss')
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
        const { rows, total } = await deviceListE(this.sbParams);
        rows.map(ele => {
          ele.monitoringOnlineRate = Math.round(ele.onlineMonitorDeviceNumber*100 / ele.totalMonitorDeviceNumber)
        })
        this.sbData = rows;
        this.sbTotal = total;
      },
      /** 获取工作列表*/
      async getGzList() {
        const { rows, total } = await getWorkList(this.gzParams);
        rows.map(ele => {
          ele.sTime = moment(new Date(ele.insertTime)).format('YYYY-MM-DD HH:mm:ss')
        })
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
              return exportWzDetailE(_this.wzParams);
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
        this.timeRange = this.days;
        if (this.role === 0) {
          this.getWzList();
          this.getSbList();
        } else {
          this.getWzList();
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
      wzParams: {
        get() {
          let params = {
            ...this.wzParam,
            beginTime: this.timeRange[0],
            endTime: this.timeRange[1],
          }
          this.role === 0 ? params.companyId = this.companyId : params.projectId = this.projectId
          return params
        },
        set(val) {
          console.log('woshiset', val)
        }
      },
      sbParams() {
        return {
          ...this.sbParam,
          companyId: this.companyId
        }
      },
      gzParams() {
        return {
          ...this.gzParam,
          beginTime: this.timeRange[0],
          endTime: this.timeRange[1],
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
      height: 28px;
      line-height: 1;
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
