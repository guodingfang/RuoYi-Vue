<template>
  <div class="access-wrap">
    <el-button type="primary" class="addBtn" @click="addModel">新增</el-button>
    <!-- <div class="query-box">
      <div class="r-query">
        <el-select
          placeholder="项目名称"
          v-model="queryParams.projectId"
          @pagination="getEtable"
          style="width: 157px; margin-right: 10px"
          filterable
          clearable
        >
          <el-option
            v-for="item in projectList"
            :key="item.id"
            :label="item.projectName"
            :value="item.id"
          />
        </el-select>
        <el-input
          placeholder="输入姓名或身份证号码搜索"
          v-model="queryParams.keyWordName"
          style="width: 217px;"
        >
          <i slot="suffix" @click="getEtable()" class="el-input__icon el-icon-search"></i>
        </el-input>
        <el-button
          type="primary"
          @click="getEtable"
        >查询</el-button>
        <el-button
          type="primary"
          @click="restItable"
        >重置</el-button>
      </div>
    </div> -->
    <div class="table-wrap">
      <Table
        :tableData="tableData"
        :tableConfig="tableConfig"
      >
        <template v-slot:eventType>
          <el-table-column label="类型" align="left">
            <template slot-scope="scope">
              <span v-if="scope.row.eventType == 1">正向</span>
              <span v-else>反向</span>
            </template>
          </el-table-column>
        </template>
        <template v-slot:operate>
          <el-table-column label="操作" align="left">
            <template slot-scope="scope">
              <span
                class="jl-green mr10 pointer"
                @click="modifyModel(scope.row)"
                >修改</span
              >
              <span
                class="jl-green mr10 pointer"
                @click="deleteFn(scope.row.id)"
                >删除</span
              >
            </template>
          </el-table-column>
        </template>
      </Table>
    </div>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getEtable"
    />
    <component
      :is="model"
      v-if="popShow"
      :state="state"
      :detailData="detailData"
      :is-show="popShow"
      @on-close="closePop"
    />
  </div>
</template>

<script>
  import moment from 'moment';
  import {
    listProject,
  } from "@/api/people/attendance";

  import { getEtable,deleteInstall,detailInstall } from "@/api/people/b-install";
  import Table from '@/components/Table';
  import addInstall from './../components/add-install';
  export default {
    name: 'AccessRecord',
    components: {
      Table,addInstall
    },
    data() {
      return{
        tableConfig: [
          { slot: "operate" },
          { prop: 'eventName', label: '事件' },
          { slot: 'eventType' },
          { prop: 'point', label: '扣除分数', width: ''},
          { prop: 'createdTime', label: '创建时间', width: ''},
        ],
        total: 0,
        tableData: [],
        //TODO 这个查询参数感觉有问题，后面再写
        //项目查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          projectId: '',
          eventType: 0
          // projectId: '',
          // unitId: '',
        },
        projectList: [],
        detailData: {},
        popShow: false,
        model: null,
        state: null,
      }
    },
    created() {
      this.getProjectList();
    },
    methods: {
      showPop(flag) {
        this.popShow = flag;
      },
      addModel() {
        this.state = 'add'
        this.model = 'addInstall'
        this.detailData = {
          title: '负向',
          eventType: 0,
          projectId: this.queryParams.projectId
        }
        this.showPop(true)
      },
      async modifyModel(row) {
        this.state = 'modify'
        this.model = 'addInstall'
        const re = await detailInstall(row.id)
        console.log(re);
        this.detailData = {
          ...re.data,
          title: '负向',
          eventType: 0,
          projectId: this.queryParams.projectId
        }
        this.showPop(true)
      },
      closePop(resful) {
        this.showPop(false);
        if (resful == "success") {
          this.getEtable(this.queryParams);
        }
        this.model = null;
      },
      restItable() {
        this.queryParams.pageNum = 1
        this.queryParams.pageSize = 10
        this.queryParams.keyWordName = ''
        this.getEtable()
      },
      /** 获取项目下拉查询 */
      getProjectList() {
        listProject().then(response => {
          this.projectList = response.rows;
          if (!this.queryParams.projectId) {
            this.queryParams.projectId = response.rows[0].id;
            this.getEtable();
          }
          //TODO
          // this.queryParams.projectId = response.rows[0].id
        });
      },

      /** 查询 列表 */
      getEtable() {
        getEtable(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },
      deleteFn(ids) {
        return new Promise(res => {
          this.$confirm("是否确认删除?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(async () => {
              const re = await deleteInstall(ids);
              if (re.code === 200) {
                this.$message({
                  message: re.msg,
                  type: "success"
                });
                this.getEtable();
              }
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消删除"
              });
            });
        });
      }
    },
  }
</script>

<style scoped lang="scss">
  .access-wrap{
    padding: 20px 0;
    /deep/.el-table{
      border-radius: 0;
    }
    .addBtn{
      position: absolute;
      right: 0;
      top: -92px;
      width: 97px;
    }
    .query-box {
      height: 60px;
      line-height: 60px;
      display: flex;
      justify-content: space-between;
      position: absolute;
      right: 0;
      top: -55px;
    }
    .tb-footer{
      border: 1px solid #dddcdc;
      border-top: 0;
      height: 42px;
      line-height: 42px;
      text-align: right;
    }
  }
</style>
