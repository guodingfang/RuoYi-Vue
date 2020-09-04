<template>
  <section>
    <div class="title-btn">
      <el-button @click="handleExport">批量导出</el-button>
      <el-button type="primary" @click="openAdd">新增随机巡视隐患</el-button>
    </div>
    <div class="query-box">
      <span>全部（{{ total }}）</span>
      <div class="r-query">
        <el-date-picker
          v-model="queryParams.beginTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="开始日期">
        </el-date-picker>
        <el-date-picker
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="结束日期">
        </el-date-picker>

        <el-select clearable v-model="queryParams.projectId" placeholder="项目名称">
          <el-option
            v-for="item in $DStore.state.getProject"
            :key="item.id"
            :label="item.projectName"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <!-- <el-select
          placeholder="项目名称"
          clearable
          v-model="queryParams.projectId"
          style="width: 200px"
        >
          <el-option
            v-for="item in $DStore.state.getProject"
            :key="item.id"
            :label="item.projectName"
            :value="item.id"
          >
        </el-select> -->


        <el-button
          type="primary"
          icon="el-icon-search"
          @click="getItable"
        >查询</el-button>
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="restItable"
        >重置</el-button>
      </div>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:statusName>
        <el-table-column label="整改状态" width="80px">
          <template slot-scope="scope">
            <status-tag :status="scope.row.statusName" />
          </template>
        </el-table-column>
      </template>
      <template v-slot:qualityInfo>
        <el-table-column label="隐患等级 | 作业类型 | 问题描述" width="260px">
          <template slot-scope="scope">
            <StatusTag :status="scope.row.hazardLevelName"/>| {{scope.row.operationName||'暂无'}} |
            <span v-if="!scope.row.hazardDescribe">暂无</span>
            <span v-else-if="scope.row.hazardDescribe.length <= 6">{{scope.row.hazardDescribe}}</span>
            <el-popover v-else trigger="hover" placement="top" style="display: inline-block">
              <p>{{ scope.row.hazardDescribe }}</p>
              <div slot="reference" class="name-wrapper">
                <span>{{ scope.row.hazardDescribe.substring(0,6)+'...'}}</span>
              </div>
            </el-popover>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span
              class="jl-green mr10 pointer"
              @click="openDetail(scope.row.id)"
              >查看</span
            >
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getItable"
    />

    <detailRecord :isShow="isDetail" :detailData="detailData" @on-close="close" />
    <addRecord :isShow="isAdd" :detailData="detailData" @on-close="close" />
  </section>
</template>
<script>
import Table from "@/components/Table";
import StatusTag from '@/components/StatusTag/index'
import detailRecord from './detail-record'
import addRecord from './add-record'
import {
  getItable,
  addInspec,
  detailInspec,
  detailInspecTest,
  exportInspec,
} from "@/api/Inspection/record";
export default {
  name: 'record',
  components: { Table, StatusTag, detailRecord, addRecord },
  data () {
    return {
      tableConfig: [
        { slot: "operate" },
        { slot: "statusName" },
        { prop: "rectifyLimitDate", label: "整改时限", width: "" },
        { prop: "projectName", label: "项目名称", width: "" },
        { prop: "checkPositionName", label: "检查人", width: "" },
        { prop: "checkDate", label: "检查时间", width: "" },
        { prop: "checkPosition", label: "检查部位", width: "" },
        { slot: 'qualityInfo' },
        { prop: "repairPersonName", label: "整改人", width: "" },
        { prop: "verifyPersonName", label: "验证人", width: "" },
        { prop: "senderName", label: "抄送至", width: "" },
      ],
      total: 0,
      tableData: [],
      //项目查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      isDetail: false,
      isAdd: false,
      detailData: {}
    }
  },
  created() {
    this.getItable()
    this.$DStore.refresh(['getProject'], true);
  },
  methods: {
    restItable() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10
      }
      // this.queryParams.pageNum = 1
      // this.queryParams.pageSize = 10
      // this.queryParams.keyWordName = ''
      // this.queryParams.month = moment().format("yyyy-MM")
      this.getItable()
    },
    async getItable() {
      const re = await getItable(this.queryParams)
      console.log(re);
      this.tableData = re.rows;
      this.total = re.total;
    },
    showAdd(flag) {
      this.isAdd = flag
    },
    showDetail(flag) {
      this.isDetail = flag
    },
    openAdd() {
      this.detailData = {}
      this.showAdd(true)
    },
    async openDetail(id) {
      // const re = await detailInspec(id)
      const res = await detailInspecTest(id)
      this.detailData = res.data
      this.showDetail(true)
    },
    close(rest) {
      if (rest) {
        this.getItable()
      }
      this.showAdd(false)
      this.showDetail(false)
    },
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出数据！", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportInspec(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    }
  }
}
</script>
<style lang='scss' scoped>
  .title-btn{
    position: absolute;
    top: -40px;
    right: 0;
  }
  .query-box {
    height: 60px;
    line-height: 60px;
    display: flex;
    justify-content: space-between;
    .add-btn {
      margin-top: 14px;
    }
  }
</style>
