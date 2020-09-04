<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="待整改" name="first">
        <NoRectification v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="待验证" name="second">
        <WaitVerify v-if="activeName === 'second'"/>
      </el-tab-pane>
      <el-tab-pane label="待验收" name="third">
        <NoAcceptance v-if="activeName === 'third'"/>
      </el-tab-pane>
      <el-tab-pane label="验收不合格" name="fourth">
        <Unqualified v-if="activeName === 'fourth'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>

  import NoRectification from './components/NoRectification'
  import WaitVerify from './components/WaitVerify'
  import Unqualified from './components/Unqualified'
  import NoAcceptance from './components/NoAcceptance'
  export default {
    name: 'WorkTask',
    components: {
      NoAcceptance,
      Unqualified,
      WaitVerify,
      NoRectification
    },
    data() {
      return {
        activeName: 'first',
      }
    },
    mounted() {
    },
    methods: {

    },
  }
</script>

<style scoped>
  .wrap{
    padding: 0 20px;
  }
</style>
