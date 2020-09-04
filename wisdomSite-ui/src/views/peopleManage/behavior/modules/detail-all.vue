<template>
  <div class="tabs-wrap">
    <el-breadcrumb separator-class="el-icon-arrow-right" style="line-height: 50px;border-bottom: 1px solid #dddcdc;">
      <el-breadcrumb-item >
        <el-link :underline="false" @click="close">
          {{detailData.title}}
        </el-link>
      </el-breadcrumb-item>
      <el-breadcrumb-item>
        <span  class="jl-green mr10 pointer" style="font-size: 14px;">企业详情</span>
      </el-breadcrumb-item>
    </el-breadcrumb>
    <el-tabs v-model="activeName">
      <el-tab-pane label="信息详情" name="first">
        <detailInformation :detailData='detailData' />
        <!-- <pTable :tableData="labelData" :tableStyle="{ width:'860px' }" style="margin-bottom: 20px;"></pTable> -->
        <!-- <recordJust v-if="activeName === 'first'"/> -->
      </el-tab-pane>
      <el-tab-pane label="兑换记录" name="second">
        <detailExchange :detailData='detailData' />
        <!-- <recordBack v-if="activeName === 'second'"/> -->
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
// import evaluate from './modules/evaluate'
// import recordJust from './modules/record-just'
// import recordBack from './modules/record-back'
// import pTable from './../components/pTables';
import detailInformation from './../components/detail-information';
import detailExchange from './../components/detail-exchange';
export default {
  name: 'Inspection',
  components: {
    // pTable,
    detailInformation,
    detailExchange,
    // recordBack,
  },
  props: {
    detailData: {
      type: Object,
      default: () => {}
    },
  },
  data () {
    return {
      activeName: 'first',
    }
  },
  methods: {
    close() {
      this.$emit('on-close')
    }
  }
}
</script>
<style lang='scss' scoped>
::v-deep{
  .el-tabs__content{
    overflow: inherit;
  }
}
.tabs-wrap{
  position: absolute;
  top: -100px;
  left: 0;
  right: 0;
  bottom: 0;
  min-height: 89vh;
  height: calc(100% + 100px);
  background: #fff;
  z-index: 999;
}

</style>
