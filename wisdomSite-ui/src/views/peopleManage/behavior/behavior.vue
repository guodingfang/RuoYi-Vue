<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="安全之星" name="first">
        <security v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="行为记录" name="record">
        <record v-if="activeName === 'record'"/>
      </el-tab-pane>
      <el-tab-pane label="行为评价" name="evaluate">
        <evaluate />
      </el-tab-pane>
      <el-tab-pane label="兑换记录" name="convert">
        <convert />
      </el-tab-pane>
      <el-tab-pane label="物品管理" name="manage">
        <manage />
      </el-tab-pane>
      <el-tab-pane label="设置" name="install">
        <install />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import security from './security'
import record from './record'
import evaluate from './modules/evaluate'
import convert from './modules/convert'
import manage from './modules/manage'
import install from './install'

import jobtype from './modules/jobtype'
export default {
  name: 'Inspection',
  components: {
    security,
    record,
    evaluate,
    convert,
    manage,
    install,
    jobtype,
  },
  data () {
    return {
      activeName: 'install',
    }
  }
}
</script>
<style lang='scss' scoped>
  .wrap{
    padding: 0 20px;
  }
  ::v-deep{
    .el-tabs__content{
      overflow: inherit;
    }
  }

</style>
