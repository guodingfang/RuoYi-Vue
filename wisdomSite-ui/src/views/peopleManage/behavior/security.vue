<template>
  <div class="tabs-wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="正向激励累计" name="first">
        <securityJust v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="负向扣分累计" name="second">
        <securityBack v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
// import evaluate from './modules/evaluate'
import securityJust from './modules/security-just'
import securityBack from './modules/security-back'
export default {
  name: 'Inspection',
  components: {
    securityJust,
    securityBack,
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
