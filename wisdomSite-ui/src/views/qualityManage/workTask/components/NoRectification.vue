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
        <el-table-column label="整改状态" width="80px">
          <template slot-scope="scope">
            <StatusTag :status="scope.row.statusName"/>
          </template>
        </el-table-column>
      </template>
      <template v-slot:qualityInfo>
        <el-table-column label="质量问题等级 | 问题类型 | 问题描述" width="260px">
          <template slot-scope="scope">
            <StatusTag :status="scope.row.qualityProblemLevelName"/>| {{scope.row.qualityProblemName||'暂无'}} |
            <span v-if="!scope.row.comment">暂无</span>
            <span v-else-if="scope.row.comment.length <= 6">{{scope.row.comment}}</span>
            <el-popover v-else trigger="hover" placement="top" style="display: inline-block">
              <p>{{ scope.row.comment }}</p>
              <div slot="reference" class="name-wrapper">
                <span>{{ scope.row.comment.substring(0,6)+'...'}}</span>
              </div>
            </el-popover>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span  class="jl-green mr10 pointer" @click="openDetailDialog(scope.row.id)">查看</span>
            <span  class="jl-green mr10 pointer" @click="openRectDialog(scope.row.id)">进行整改</span>
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
    <rect-detail-dialog
      :dialogFlag.sync="dialogFlag"
      :detailDatas.sync="detailData"
    />
    <rectification-dialog
      :dialogFlag.sync="rectFlag"
      :id="operateId"
      @query="getQualityList"
    />
  </div>
</template>

<script>
  import {
    getRepairList,
    checkManagerDetail,
    checkManagerDetailList,
  } from "@/api/quality/workTask";
  import Table from '@/components/Table';
  import StatusTag from '@/components/StatusTag';
  import Pagination from '@/components/Pagination';
  import RectificationDialog from './RectificationDialog'
  import RectDetailDialog from "./RectDetailDialog";
  export default {
    name: 'NoRectification',
    components: {
      RectDetailDialog,
      RectificationDialog,
      Table,
      Pagination,
      StatusTag
    },
    data() {
      return{
        tableConfig: [
          { slot: 'operate' },
          { prop: 'repairLimitDate', label: '整改时限', width: '' },
          { slot: 'statusName' },
          { prop: 'projectName', label: '项目名称', width: ''},
          { prop: 'checkPositionName', label: '检查人', width: ''},
          { prop: 'checkDate', label: '检查时间', width: ''},
          { prop: 'checkPosition', label: '检查部位', width: ''},
          { slot: 'qualityInfo' },
          { prop: 'repairPersonName', label: '整改人', width: ''},
          { prop: 'verifyPersonName', label: '验证人', width: ''},
        ],
        total: 0,
        tableData: [],
        //项目查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          status: 1,
        },
        projectList: [],
        projName: '',
        timeRange: '',
        detailData: {},
        engineeringForm: {},
        dialogFlag: false,
        rectFlag: false,
        title: '',
        operateId: 0,
      }
    },
    created() {
      this.getQualityList();
    },
    methods: {
      /** 查询 列表 */
      getQualityList() {
        getRepairList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      /** 打开整改框*/
      openRectDialog(id) {
        console.log(id)
        this.operateId = Number(id);
        this.rectFlag = true;
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
