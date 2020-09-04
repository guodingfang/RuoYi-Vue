<template>
  <div class="stone-wrap">
    <div class="head">
      <span></span>
      <span class="jl-green pointer" @click="openAddDialog">新增</span>
    </div>
    <Table
      :tableData="areaList"
      :tableConfig="tableConfig"
      align="left"
      :border="false"
    >
      <template v-slot:operate>
        <el-table-column label="操作" align="left" width="160px">
          <template slot-scope="scope">
            <span class="jl-green mr10 pointer"  @click="handleEdit(scope.row)">编辑</span>
            <span class="jl-green mr10 pointer"  @click="handleDelete(scope.$index, scope.row)">删除</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <area-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="dialogFlag"
      :areaForms.sync="areaForm"
      @query="getAreaList"
    />
  </div>
</template>

<script>
  import {
    getAreaList,
    delArea
  } from "@/api/project/projectArea";
  import Table from '@/components/Table';
  import AreaDialog from "./AreaDialog";
  export default {
    name: 'Area',
    components: {
      AreaDialog,
      Table
    },
    data() {
      return{
        isCreate: true,
        dialogFlag: false,
        areaList: [],
        tableConfig: [
          { slot: 'operate'},
          { prop: 'partitionName', label: '分区名称', width: "300px"},
        ],
        queryParams: {
          pageNum: 1,
          pageSize: 20,
          partitionName: '',
          milepostSort: null,
        },
        areaForm: {},
      }
    },
    mounted() {
      this.getAreaList();
    },
    methods: {
      /** 查询项目里程碑模板 列表 */
      getAreaList() {
        getAreaList(this.$route.query.id).then(res => {
            this.areaList = res.data;
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
          delArea(ids).then(response => {
            this.getAreaList();
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
      handleEdit(areaForm) {
        this.areaForm = areaForm;
        this.dialogFlag = true;
        this.isCreate = false;
      },
      /** 上移 */
      handleClickToUp(index, row) {
        if (index > 0) {
          let upDate = this.areaList[index - 1];
          let reqData = [{
            id: row.id,
            milepostSort: upDate.milepostSort
          },{
            id: upDate.id,
            milepostSort: row.milepostSort
          }]
          removeProjectMilepost(reqData).then(response => {
              this.getProjectMeilpostList()
            }
          );
        } else {
          this.msgError('已经是第一条了，移不动了')
        }
      },
      /** 下移 */
      // 下移
      handleClickToDown(index, row) {
        if ((index + 1) === this.areaList.length) {
          this.msgError('已经是最后一条，不可下移');
        } else {
          let upDate = this.areaList[index + 1];
          let reqData = [{
            id: row.id,
            milepostSort: upDate.milepostSort
          },{
            id: upDate.id,
            milepostSort: row.milepostSort
          }]
          removeProjectMilepost(reqData).then(response => {
              this.getProjectMeilpostList()
            }
          );
        }
      },
    }
  }
</script>

<style scoped lang="scss">
  .stone-wrap{
    width: 462px;
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
