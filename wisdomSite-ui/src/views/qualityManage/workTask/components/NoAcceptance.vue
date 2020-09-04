<template>
  <div>
    <div class="query-box">
      <span>全部（{{total}}）</span>
    </div>
    <Table
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
      <template v-slot:statusName>
        <el-table-column label="验收状态" width="80px">
          <template slot-scope="scope">
            <StatusTag :status="scope.row.acceptanceStateName"/>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span  class="jl-green mr10 pointer" @click="openDetailDialog(scope.row.id)">查看</span>
            <span  class="jl-green mr10 pointer" @click="openAcceDialog(scope.row.id)">进行验收</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getQualityList"
    />
    <acce-detail-dialog
      :dialogFlag.sync="dialogFlag"
      :detailDatas.sync="detailData"
    />
    <accept-dialog
      :dialogFlag.sync="acceFlag"
      :id="operateId"
      @query="getQualityList"
    />
  </div>
</template>

<script>
  import {
    listCheckManager,
    addRepair,
    addVerify,
    checkManagerDetail,
    checkManagerDetailList,
    listAcceptanceApply,
    ddRecord,
    getCheckUser,
    addAcceptIncrease,
    acceptanceApplyDetail,
    acceptanceRecordDetail
  } from "@/api/quality/workTask";
  import Table from '@/components/Table';
  import StatusTag from '@/components/StatusTag';
  import Pagination from '@/components/Pagination';
  import AcceDetailDialog from "./AcceDetailDialog";
  import AcceptDialog from "./AcceptDialog";
  // import DetailDialog from './DetailDialog'
  // import AddDialog from './AddDialog'
  export default {
    name: 'NoAcceptance',
    components: {
      AcceptDialog,
      AcceDetailDialog,
      // AddDialog,
      // DetailDialog,
      Table,
      Pagination,
      StatusTag
    },
    data() {
      return{
        tableConfig: [
          { slot: 'operate' },
          { prop: 'planverificationDate', label: '验收时限', width: '' },
          { slot: 'statusName' },
          { prop: 'projectName', label: '项目名称', width: ''},
          { prop: 'applyPersonName', label: '申请人', width: ''},
          { prop: 'applyDate', label: '申请时间', width: ''},
          { prop: 'acceptancePosition', label: '验收部位', width: ''},
          { prop: 'acceptanceContent', label: '验收内容', width: ''},
          { prop: 'acceptanceNum', label: '验收次数', width: ''},
        ],
        total: 0,
        tableData: [],
        //项目查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          acceptanceState: 3,
        },
        projectList: [],
        projName: '',
        timeRange: '',
        detailData: {},
        engineeringForm: {},
        dialogFlag: false,
        addDialogFlag: false,
        acceFlag: false,
        title: '',
        operateId: ''
      }
    },
    created() {
      this.getQualityList();
    },
    methods: {
      /** 查询 列表 */
      getQualityList() {
        listAcceptanceApply(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      /** 打开新增框*/
      openAcceDialog(id) {
        this.operateId = Number(id);
        this.acceFlag = true;
      },
      /** 打开详情框*/
      openDetailDialog(id) {
        Promise.all([acceptanceApplyDetail(id), acceptanceRecordDetail(id)]).then(res => {
          this.detailData = {
            ...res[0].data,
            list: res[1].data
          };
          this.dialogFlag = true;
        }).catch(err => {
          this.msgError(err);
        })
      },
    }
  }
</script>

<style scoped lang="scss">
  .query-box{
    height: 60px;
    line-height: 60px;
    display: flex;
    justify-content: space-between;
    .add-btn{
      margin-top: 14px;
    }
  }
</style>
