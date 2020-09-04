<template>
  <div class="access-wrap">
    <div class="query-box">
      <el-input
        placeholder="请按名称或身份证号码操作"
        style="width: 220px;"
        v-model="queryParams.keyWordName"
        clearable
      >
        <i @click="getPtable()" slot="suffix" class="el-input__icon el-icon-search"></i>
      </el-input>
    </div>
    <Table
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
      <template v-slot:statusName>
        <el-table-column label="照片">
          <template slot-scope="scope">
            <img style="width: 98px;" v-if="scope.row.faceUrl" :src="scope.row.faceUrl" alt="">
            <span v-else></span>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span  class="jl-green mr10 pointer" @click="detailWorker(scope.row.id)">详情</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getPtable"
    />
    <component
      :is="model"
      v-if="popShow"
      :state="state"
      :detailData='detailData'
      :is-show="popShow"
      @on-close="closePop"
    />
  </div>
</template>
<script>
import {
  getPtable,
  detailWork,
} from "@/api/people/workerLib";
import Table from '@/components/Table';
import detailWorker from './detail-worker';
export default {
  name: "worlerLib",
  components: {
    Table,
    detailWorker,
  },
  data() {
    return {
      tableConfig: [
        { slot: 'operate' },
        { slot: 'statusName' },
        { prop: 'name', label: '姓名' },
        { prop: 'idNumber', label: '身份证号', width: '200px'},
        { prop: 'sexName', label: '性别', width: ''},
        { prop: 'mobilePhone', label: '手机号码', width: ''},
        { prop: 'age', label: '年龄', width: ''},
        { prop: 'cultureStandard', label: '学历', width: ''},
        { prop: 'address', label: '住址', width: ''},
        { prop: 'workTypeName', label: '状态', width: ''}
      ],
      total: 0,
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyWordName: "",
      },
      popShow: false,
      model: null,
      state: null,
      detailData: {},
    };
  },
  created() {
    this.getPtable()
  },
  methods: {
    async getPtable() {
      const re = await getPtable(this.queryParams)
      this.tableData = re.rows
      this.total = re.total
    },
    showModel(flag) {
      this.popShow = flag
    },
    closePop() {
      this.showModel(false)
    },
    async detailWorker(id) {
      this.model = 'detailWorker'
      const re = await detailWork(id)
      this.detailData = re.data
      this.showModel(true)
    }
  }
};
</script>
<style lang="scss" scoped>
.access-wrap{
  // height:77vh;
  // overflow: auto;
}
.query-box {
  text-align: right;
  margin: 18px;
}
</style>
