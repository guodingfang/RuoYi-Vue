<template>
  <div class="access-wrap">
    <div class="query-box">
      <el-select @change="getUtable" clearable v-model="queryParams.unitTypeId" placeholder="企业类型">
        <el-option
          v-for="item in unitList"
          :key="item.id"
          :label="item.typeName"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <el-input
        placeholder="请按企业名称或社会统一信用代码搜索"
        style="width: 220px;"
        v-model="queryParams.keyWordName"
        clearable
      >
        <i @click="getUtable()" slot="suffix" class="el-input__icon el-icon-search"></i>
      </el-input>
    </div>
    <Table
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span  class="jl-green mr10 pointer" @click="detailProjunit(scope.row.id)">详情</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getUtable"
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
  getUtable,
  detailProjunit
} from "@/api/people/projunit";
import {
  getEnterprise,
} from "@/api/people/company";
import Table from '@/components/Table';
import detailPro from './detail-projunit';
export default {
  name: "projunit",
  components: {
    Table,
    detailPro,
  },
  data() {
    return {
      tableConfig: [
        { slot: 'operate' },
        { prop: 'unitName', label: '企业名称' },
        { prop: 'unitOrgcode', label: '社会统一代码'},
        { prop: 'unitTypeName', label: '企业类型', width: ''},
        { prop: 'regAreaName', label: '注册所在地', width: ''},
        { prop: 'legalPerson', label: '法定代理人', width: ''},
        { prop: 'officeNumber', label: '办公电话', width: ''},
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
      unitList: []
    };
  },
  async created() {
    this.getUtable()
    const re = await getEnterprise()
    this.unitList = re.data
    console.log(re);
  },
  methods: {
    async getUtable() {
      const re = await getUtable(this.queryParams)
      console.log(this.queryParams);
      this.tableData = re.rows
      this.total = re.total
    },
    showModel(flag) {
      this.popShow = flag
    },
    closePop() {
      this.showModel(false)
    },
    async detailProjunit(id) {
      this.model = 'detailPro'
      const re = await detailProjunit(id)
      this.detailData = re.data
      this.showModel(true)
    }
  }
};
</script>
<style lang="scss" scoped>
.access-wrap{
  min-height: 88vh;;
}
.query-box {
  text-align: right;
  margin: 18px;
}
</style>
