<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="质量巡检" name="first">
        <patrol v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="质量问题设置" name="second">
        <setting v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import Patrol from './components/patrol'
  import Setting from './components/Setting'

  export default {
    name: 'qualityPatrol',
    components: {
      Setting,
      Patrol
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
