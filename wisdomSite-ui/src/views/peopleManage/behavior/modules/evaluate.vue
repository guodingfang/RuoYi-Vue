<template>
  <section style="position: relative;">
    <div class="query-box">
      <span>全部（{{ total }}）</span>
      <div class="r-query">
        <el-select  @change="getEtable()" v-model="queryParams.projectId" placeholder="项目名称">
          <el-option
            v-for="item in projectList"
            :key="item.id"
            :label="item.projectName"
            :value="item.id"
          >
          </el-option>
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
          icon="el-icon-search"
          @click="getEtable"
        >查询</el-button>
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="restItable"
        >重置</el-button>
      </div>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
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
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
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
    <!-- <detailRecord :isShow="isDetail" :detailData="detailData" @on-close="close" /> -->
  </section>
</template>
<script>
import Table from "@/components/Table";
// import detailRecord from './detail-record'
import { listProject } from "@/api/people/attendance";
import { getEtable } from "@/api/people/record";
import addJust from './../components/add-just';
import detailEvaluate from './detail-evaluate';
export default {
  name: 'record',
  components: { Table,addJust,detailEvaluate },
  data () {
    return {
      tableConfig: [
        { slot: "operate" },
          { prop: 'personName', label: '姓名' },
          { prop: 'idNumber', label: '身份证号' },
          { prop: 'teamName', label: '所在班组', width: ''},
          { prop: 'unitName', label: '参建单位', width: ''},
          { prop: 'point', label: '总获得点券', width: ''},
          { prop: 'exchangeablePoints', label: '可兑换点券', width: ''},
          { prop: 'totalDeductPoints', label: '累计扣分', width: ''},
      ],
      total: 0,
      tableData: [],
      //项目查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      detailData: {},
      popShow: false,
      model: null,
      state: null,
      projectList: []
    }
  },
  created() {
    this.getProjectList()
  },
  methods: {
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
    openDetail(item) {
      this.state = 'detail'
      this.model = 'detailEvaluate'
      this.detailData = {
        ...item,
        title: '行为评价'
      }
      // this.detailData = {
      //   title: '正向',
      //   eventType: 1,
      //   projectId: this.queryParams.projectId
      // }
      this.showPop(true)
    },
    restItable() {
      this.queryParams.pageNum = 1
      this.queryParams.pageSize = 10
      // this.queryParams.keyWordName = ''
      // this.queryParams.month = moment().format("yyyy-MM")
      this.getEtable()
    },
    async getEtable() {
      const re = await getEtable(this.queryParams)
      console.log(re);
      this.tableData = re.rows;
      this.total = re.total;
    },

    async openChange(row) {
      // const re = await exchange(row.personId)
      this.detailData = {
        personId: row.personId,
        projectId: row.projectId,
      }
      this.state = 'add'
      this.model = 'addJust'
      this.showPop(true)
    },
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
