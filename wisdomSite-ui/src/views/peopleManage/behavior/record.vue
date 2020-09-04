<template>
  <div class="tabs-wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="正向行为列表" name="first">
        <recordJust v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="负向行为列表" name="second">
        <recordBack v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
// import evaluate from './modules/evaluate'
import recordJust from './modules/record-just'
import recordBack from './modules/record-back'
export default {
  name: 'Inspection',
  components: {
    recordJust,
    recordBack,
    // jobtype,
  },
  data () {
    return {
      activeName: 'first',
    }
  }
}
</script>
<style lang='scss' scoped>
  // .wrap{
  //   padding: 0 20px;
  // }
  ::v-deep{
    .el-tabs__content{
      overflow: inherit;
    }
  }

</style>
