<template>
<div class="wrap">
  <div class="head">
    <span>项目里程碑模版</span>
    <span class="jl-green pointer" @click="openAddDialog">新增</span>
  </div>
  <Table
    :tableData="milestoneList"
    :tableConfig="tableConfig"
    align="left"
  >
    <template v-slot:operate>
      <el-table-column label="操作" align="left">
        <template slot-scope="scope">
          <span  class="jl-green mr10 pointer" @click="handleClickToUp(scope.$index, scope.row)">上移</span>
          <span class="jl-green mr10 pointer"  @click="handleClickToDown(scope.$index, scope.row)">下移</span>
          <span class="jl-green mr10 pointer"  @click="handleDelete(scope.$index, scope.row)">删除</span>
        </template>
      </el-table-column>
    </template>
  </Table>
  <MilestoneDialog
    :isCreate="isCreate"
    :dialogFlag.sync="dialogFlag"
    @query="getMilespostList"
  />
</div>
</template>

<script>
  import { listProjectMilepost, getProjectMilepostById, removeProjectMilepost, addProjectMilepost, updateProjectMilepost, deleteProjectMilepost} from "@/api/project/projectMilepost";
  import Table from '@/components/Table';
  import MilestoneDialog from './MilestoneDialog'
  export default {
    name: 'Milestone',
    components: {
      MilestoneDialog,
      Table
    },
    data() {
      return{
        isCreate: true,
        dialogFlag: false,
        milestoneList: [],
        tableConfig: [
          { slot: 'operate', width: "300px" },
          { prop: 'milepostName', label: '里程碑名称', width: "200px"}
        ],
        queryParams: {
          pageNum: 1,
          pageSize: 20,
          milepostName: '',
          milepostSort: null,
        },
      }
    },
    mounted() {
      this.getMilespostList();
    },
    methods: {
      /** 查询项目里程碑模板 列表 */
      getMilespostList() {
        listProjectMilepost(this.queryParams).then(res => {
            this.milestoneList = res.rows;
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
            this.getMilespostList();
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
      openUpdateDialog(id) {
      //  暂时没有，万一后面有呢
      },
      /** 上移 */
      handleClickToUp(index, value) {
        let currentSort = index;
        if (currentSort > 0) {
          let upDate = this.milestoneList[currentSort - 1];
          this.wsProjectMilepost.id = upDate.id;
          this.wsProjectMilepost.milepostSort = value.milepostSort;
          this.arr.push(this.wsProjectMilepost);
          this.wsProjectMilepostUp.id = value.id;
          this.wsProjectMilepostUp.milepostSort = upDate.milepostSort;
          this.arr.push(this.wsProjectMilepostUp);
          removeProjectMilepost(JSON.stringify(this.arr)).then(response => {
              this.getProjectMeilpostList()
            }
          );
        } else {
          this.msgError('已经是第一条了，移不动了')
        }
      },
      /** 下移 */
      // 下移
      handleClickToDown(index, value) {
        let currentSort = index;
        if ((currentSort + 1) === this.milestoneList.length) {
          this.msgError('已经是最后一条，不可下移');
        } else {
          let downDate = this.milestoneList[currentSort + 1];
          this.wsProjectMilepostUp.id = value.id;
          this.wsProjectMilepostUp.milepostSort = downDate.milepostSort;

          this.arr.push(this.wsProjectMilepostUp);
          this.wsProjectMilepost.id = downDate.id;
          this.wsProjectMilepost.milepostSort = value.milepostSort;

          this.arr.push(this.wsProjectMilepost);

          removeProjectMilepost(JSON.stringify(this.arr)).then(response => {
              this.getProjectMeilpostList()
            }
          );
        }
      },
    }
  }
</script>

<style scoped lang="scss">
.wrap{
  width: 500px;
  .head{
    margin-top: 50px;
    height: 46px;
    line-height: 46px;
    font-size: 13px;
    background-color: #f1f5f5;
    border: 1px solid #ddd;
    border-bottom: 0;
    border-radius: 4px;
    display: flex;
    justify-content: space-between;
    padding: 0 10px;
    box-sizing: border-box;
  }
}
</style>
