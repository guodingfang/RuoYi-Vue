<template>
  <div class="access-wrap">
    <div class="query-box">
      <el-date-picker
        v-model="queryParams.begintime"
        @change="getBtable()"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="加入日期开始">
      </el-date-picker>
      <el-date-picker
        v-model="queryParams.endTime"
        @change="getBtable()"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="加入日期结束">
      </el-date-picker>
      <el-select clearable @change="getBtable()" v-model="queryParams.projectId" placeholder="全部项目">
        <el-option
          v-for="item in $DStore.state.getProject"
          :key="item.id"
          :label="item.projectName"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <el-input
        placeholder="请按名称或身份证号码操作"
        style="width: 220px;"
        v-model="queryParams.keyWordName"
      >
        <i @click="getBtable()" slot="suffix" class="el-input__icon el-icon-search"></i>
      </el-input>
    </div>
    <Table
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getBtable"
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
  getBtable,
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
        // { slot: 'operate' },
        { prop: 'removeDate', label: '移除日期', width: ''},
        { prop: 'name', label: '姓名', width: ''},
        { prop: 'idNumber', label: '身份证号码', width: ''},
        { prop: 'projectName', label: '所属项目', width: ''},
        { prop: 'participatingUnitName', label: '所属参建单位', width: ''},
        { prop: 'teamName', label: '所属班组', width: ''},
        { prop: 'workTypeName', label: '工种', width: ''},
        { prop: 'illegalDate', label: '违规日期' },
        { prop: 'illegalReason', label: '违规原因', width: ''},
        { prop: 'createdTime', label: '加入日期', width: ''},
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
    this.getBtable()
    console.log(this.$DStore.state);
  },
  methods: {
    async getBtable() {
      const re = await getBtable(this.queryParams)
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
      console.log(re);
      this.showModel(true)
    }
  }
};
</script>
<style lang="scss" scoped>
.query-box {
  text-align: right;
  margin: 18px;
}
</style>
