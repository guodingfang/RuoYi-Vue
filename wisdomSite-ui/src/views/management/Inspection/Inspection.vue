<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="巡视记录" name="first">
        <record v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="安全类型" name="second">
        <jobtype v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import record from './modules/record'
import jobtype from './modules/jobtype'
export default {
  name: 'Inspection',
  components: {
    record,
    jobtype,
  },
  data () {
    return {
      activeName: 'first',
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
