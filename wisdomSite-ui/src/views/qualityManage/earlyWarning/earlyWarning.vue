<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="超期未整改" name="first">
        <rectified v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="超期未验证" name="second">
        <verified v-if="activeName === 'second'"/>
      </el-tab-pane>
      <el-tab-pane label="多次验收未通过" name="distc">
        <failednot v-if="activeName === 'distc'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import rectified from './modules/rectified'
  import verified from './modules/verified'
  import failednot from './modules/failednot'

  export default {
    name: 'EarlyWarning',
    components: {
      rectified,
      verified,
      failednot,
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
