<template>
  <div class="stone-wrap">
    <div class="head">
      <span></span>
      <span class="jl-green pointer" @click="openAddDialog">新增</span>
    </div>
    <Table
      :tableData="milestoneList"
      :tableConfig="tableConfig"
      align="left"
      :border="false"
    >
      <template v-slot:operate>
        <el-table-column label="操作" align="left" width="230px">
          <template slot-scope="scope">
            <span  class="jl-green mr10 pointer" @click="handleClickToUp(scope.$index, scope.row)">上移</span>
            <span class="jl-green mr10 pointer"  @click="handleClickToDown(scope.$index, scope.row)">下移</span>
            <span class="jl-green mr10 pointer"  @click="handleEdit(scope.row)">编辑</span>
            <span class="jl-green mr10 pointer"  @click="handleDelete(scope.$index, scope.row)">删除</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <milestone-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="dialogFlag"
      :stoneForms.sync="stoneForm"
      @query="getProjectMileStone"
    />
  </div>
</template>

<script>
  import _ from 'lodash';
  import {
    getProjectMileStone,
    getProjectMilepostById,
    removeProjectMilepost,
    addProjectMilepost,
    updateProjectMilepost,
    deleteProjectMilepost
  } from "@/api/project/projectMilepost";
  import Table from '@/components/Table';
  import MilestoneDialog from './MilestoneDialog'
  export default {
    name: 'Stone',
    components: {
      MilestoneDialog,
      Table
    },
    data() {
      return{
        isOperate: false,
        isCreate: true,
        dialogFlag: false,
        milestoneList: [],
        tableConfig: [
          { slot: 'operate'},
          { prop: 'milepostName', label: '里程碑名称', width: "140px" },
          { prop: 'milepostDate', label: '时间', width: "240px"}
        ],
        queryParams: {
          pageNum: 1,
          pageSize: 20,
          milepostName: '',
          milepostSort: null,
        },
        stoneForm: {},
      }
    },
    mounted() {
      this.getProjectMileStone();
    },
    methods: {
      /** 查询项目里程碑模板 列表 */
      getProjectMileStone() {
        getProjectMileStone(this.$route.query.id).then(res => {
            this.milestoneList = res.data;
          }
        );
      },
      handleDelete(index, row) {
        this.handleClickToDelete(row.id);
      },
      /** 删除项目里程碑模板 */
      handleClickToDelete(ids) {
        this.$confirm('是否确认删除该模版?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          deleteProjectMilepost(ids).then(response => {
            this.getProjectMileStone();
            this.msgSuccess("删除成功");
          });
        }).catch(function() {});
      },
      /** 打开新增项目框*/
      openAddDialog() {
        this.dialogFlag = true;
        this.isCreate = true;
      },
      /** 打开编辑项目框*/
      handleEdit(stoneForm) {
          this.stoneForm = stoneForm;
          this.dialogFlag = true;
          this.isCreate = false;
      },
      /** 上移 */
      handleClickToUp: _.debounce(function (index, row) {
        if (this.isOperate) {
          return;
        }
        if (index > 0) {
          let upDate = this.milestoneList[index - 1];
          let reqData = [{
            id: row.id,
            milepostSort: upDate.milepostSort,
            projectId: row.projectId
          },{
            id: upDate.id,
            milepostSort: row.milepostSort,
            projectId: row.projectId
          }]
          this.isOperate = true;
          removeProjectMilepost(reqData).then(response => {
              this.isOperate = false;
              this.getProjectMileStone();
            }
          ).catch(err => {
            this.isOperate = false;
          });
        } else {
          this.msgError('已经是第一条了，移不动了')
        }
      }, 500),
      /** 下移 */
      handleClickToDown: _.debounce(function (index, row) {
        if (this.isOperate) {
          return;
        }
        if ((index + 1) === this.milestoneList.length) {
          this.msgError('已经是最后一条，不可下移');
        } else {
          let upDate = this.milestoneList[index + 1];
          let reqData = [{
            id: row.id,
            milepostSort: upDate.milepostSort,
            projectId: row.projectId
          },{
            id: upDate.id,
            milepostSort: row.milepostSort,
            projectId: row.projectId
          }]
          this.isOperate = true;
          removeProjectMilepost(reqData).then(response => {
              this.isOperate = false;
              this.getProjectMileStone()
            }
          ).catch(err => {
            this.isOperate = false;
          });
        }
      }, 500),
    }
  }
</script>

<style scoped lang="scss">
  .stone-wrap{
    width: 612px;
    /deep/.el-table{
      border-radius: 0;
    }
    .head{
      margin-top: 50px;
      height: 46px;
      line-height: 46px;
      font-size: 13px;
      background-color: #ecf2f2;
      border: 1px solid #e9e9e9;
      border-bottom: 0;
      display: flex;
      justify-content: space-between;
      padding: 0 10px;
      box-sizing: border-box;
    }
  }
</style>
