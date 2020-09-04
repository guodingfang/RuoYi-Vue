<template>
  <section class="warps">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item >
        <el-link :underline="false" @click="close">参建单位</el-link>
      </el-breadcrumb-item>
      <el-breadcrumb-item>
        <span  class="jl-green mr10 pointer" style="font-size: 14px;">企业详情</span>
      </el-breadcrumb-item>
    </el-breadcrumb>
    <!--  -->
    <pTable :tableData="tableData" :tableStyle="{ width:'900px' }"></pTable>
  <!--  -->
    <el-tabs v-model="activeName">
      <el-tab-pane label="项目经历" name="first">
        <undergo :parmId='detailData.unitOrgcode' v-if="activeName === 'first'" />
      </el-tab-pane>
    </el-tabs>
  </section>
</template>
<script>
import undergo from './../components/undergo'
import pTable from './../components/pTable'
export default {
  name: '',
  components: {
    undergo,
    pTable,
  },
  props: {
    isShow: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: Object,
      default: () => {}
    },
    state: {
      type: String,
      default: () => null
    }
  },
  data () {
    return {
      activeName: 'first',
    }
  },
  computed: {
    tableData() {
      const re = this.detailData
      return [
        {key: '企业名称', value: re.unitName},
        {key: '企业类型', value: re.unitTypeName},
        {key: '社会统一信用代码', value: re.unitOrgcode},
        {key: '注册日期', value: re.regDate},
        {key: '注册资本（万元）', value: re.regCapital},
        {key: '注册所在地', value: re.regAreaName},
        {key: '企业性质', value: re.unitNature},
        {key: '法定代表人', value: re.legalPerson},
        {key: '营业地址', value: re.address},
        {key: '联系人', value: re.contacts},
        {key: '联系电话', value: re.contactNumber},
        {key: '办公电话', value: re.officeNumber},
        {key: '传真号', value: re.faxNumber},
        {key: '备注', value: re.comment},
      ]
    }
  },
  methods: {
    close() {
      this.$emit("on-close");
    }
  }
}
</script>
<style lang='scss' scoped>
$--border-size: 1px solid #dddcdc;
.warps{
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  // min-height: 100%;
  background: #fff;
  z-index: 999;
  .el-breadcrumb{
    padding: 20px 0;
    font-size: 13px;
    border-bottom: $--border-size;
  }
}
</style>
