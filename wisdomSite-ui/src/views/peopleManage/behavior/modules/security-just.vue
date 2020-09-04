<template>
  <div class="access-wrap">
    <div class="query-box">
      <div class="r-query">
        <el-select
          placeholder="项目名称"
          v-model="queryParams.projectId"
          @pagination="getJtable"
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
        <el-date-picker
          style="width: 157px; margin-right: 10px"
          v-model="queryParams.month"
          @pagination="getJtable"
          type="month"
          value-format="yyyy-MM"
        />
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="getJtable"
        >查询</el-button>
        <el-button
          type="primary"
          @click="restItable"
        >重置</el-button>
      </div>
    </div>
    <div class="table-wrap">
      <Table
        :tableData="tableData"
        :tableConfig="tableConfig"
      >
        <template v-slot:operate>
          <el-table-column label="操作" align="left">
            <template slot-scope="scope">
              <span
                class="jl-green mr10 pointer"
                @click="openDetail(scope.row)"
                >详情</span
              >
              <span
                class="jl-green mr10 pointer"
                @click="openChange(scope.row)"
                >兑换</span
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
      @pagination="getJtable"
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

  import { getJtable,exchange } from "@/api/people/security";
  import Table from '@/components/Table';
  import addJust from './../components/add-just';
  import detailAll from './detail-all';
  export default {
    name: 'AccessRecord',
    components: {
      Table,addJust,detailAll
    },
    data() {
      return{
        tableConfig: [
          { slot: "operate" },
          { prop: 'personName', label: '姓名' },
          { prop: 'idNumber', label: '身份证号' },
          { prop: 'teamName', label: '所在班组', width: ''},
          { prop: 'unitName', label: '参建单位', width: ''},
          { prop: 'point', label: '该月累计点券', width: ''},
        ],
        total: 0,
        tableData: [],
        //TODO 这个查询参数感觉有问题，后面再写
        //项目查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          projectId: '',
          month: moment().format("yyyy-MM"),
          eventType: 1
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
      closePop(resful) {
        this.showPop(false);
        if (resful == "success") {
          this.getJtable();
        }
        this.model = null;
      },
      restItable() {
        this.queryParams.pageNum = 1
        this.queryParams.pageSize = 10
        this.queryParams.month = moment().format("yyyy-MM")

        this.getJtable()
      },
      /** 获取项目下拉查询 */
      getProjectList() {
        listProject().then(response => {
          this.projectList = response.rows;
          if (!this.queryParams.projectId) {
            this.queryParams.projectId = response.rows[0].id;
            this.getJtable();
          }
          //TODO
          // this.queryParams.projectId = response.rows[0].id
        });
      },
      openDetail(item) {
        this.state = 'detail'
        this.model = 'detailAll'
        this.detailData = {
          ...item,
          title: '正向激励累计'
        }
        this.showPop(true)
      },
      async openChange(row) {
        console.log(row);
        // const re = await exchange(row.personId)
        this.detailData = {
          personId: row.personId,
          projectId: row.projectId,
        }
        this.state = 'add'
        this.model = 'addJust'
        this.showPop(true)
      },

      /** 查询 列表 */
      getJtable() {
        getJtable(this.queryParams).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      },

    },
    watch: {

    }
  }
</script>

<style scoped lang="scss">
  .access-wrap{
    padding: 20px 0;
    /deep/.el-table{
      border-radius: 0;
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
