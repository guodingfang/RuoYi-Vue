<template>
  <section>
    <div class="query-box">
      <span>全部（{{ total }}）</span>
      <div class="r-query">
        <el-select  @change="getCtable()" v-model="queryParams.projectId" placeholder="项目名称">
          <el-option
            v-for="item in projectList"
            :key="item.id"
            :label="item.projectName"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <el-date-picker
          style="width: 157px; margin-right: 10px"
          v-model="queryParams.month"
          @pagination="getCtable"
          type="month"
          value-format="yyyy-MM"
        />
        <el-input
          placeholder="输入姓名或身份证号码搜索"
          v-model="queryParams.keyWordName"
          style="width: 217px;"
        >
          <i slot="suffix" @click="getCtable()" class="el-input__icon el-icon-search"></i>
        </el-input>

        <el-button
          type="primary"
          icon="el-icon-search"
          @click="getCtable"
        >查询</el-button>
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="restItable"
        >重置</el-button>
      </div>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getCtable"
    />
  </section>
</template>
<script>
import moment from 'moment';
import Table from "@/components/Table";
// import detailRecord from './detail-record'
import { listProject } from "@/api/people/attendance";
import { getCtable } from "@/api/people/record";

export default {
  name: 'record',
  components: { Table },
  data () {
    return {
      tableConfig: [
          { prop: 'exchangeMount', label: '兑换数量', width: ''},
          { prop: 'personName', label: '姓名' },
          { prop: 'idNumber', label: '身份证号' },
          { prop: 'teamName', label: '所在班组', width: ''},
          { prop: 'unitName', label: '参建单位', width: ''},
          { prop: 'createdTime', label: '兑换时间', width: ''},
          { prop: 'costPoint', label: '使用点券', width: ''},
          { prop: 'itemName', label: '兑换物品', width: ''},
      ],
      total: 0,
      tableData: [],
      //项目查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: '',
        month: moment().format("yyyy-MM"),
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
          this.getCtable();
        }
        //TODO
        // this.queryParams.projectId = response.rows[0].id
      });
    },
    restItable() {
      this.queryParams.pageNum = 1
      this.queryParams.pageSize = 10
      this.queryParams.keyWordName = ''
      this.queryParams.month = moment().format("yyyy-MM")
      this.getCtable()
    },
    async getCtable() {
      const re = await getCtable(this.queryParams)
      console.log(re);
      this.tableData = re.rows;
      this.total = re.total;
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
