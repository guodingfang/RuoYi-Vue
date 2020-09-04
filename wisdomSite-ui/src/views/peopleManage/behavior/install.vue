<template>
  <div class="tabs-wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="正向行为事件" name="first">
        <installJust v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="负向行为事件" name="second">
        <installBack v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
// import evaluate from './modules/evaluate'
import installJust from './modules/install-just'
import installBack from './modules/install-back'
export default {
  name: 'Inspection',
  components: {
    installJust,
    installBack,
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
