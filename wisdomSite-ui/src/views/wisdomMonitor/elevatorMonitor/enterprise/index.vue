<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="实时监控" name="first">
        <real-time v-if="activeName === 'first'" @click-stats="clickStats"/>
      </el-tab-pane>
      <el-tab-pane label="监控统计" name="second">
        <statistic ref="statistic" v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import RealTime from "./RealTime";
  import Statistic from "./Statistic";

  export default {
    name: 'ElevatorMonitor',
    components: {
      Statistic,
      RealTime,
    },
    data() {
      return {
        activeName: 'first',
      }
    },
    mounted() {
    },
    methods: {
      clickStats(e) {
        const { projectId = '' } = e;
        this.activeName = 'second';
        this.projectId = projectId;
      }
    },
  }
</script>

<style scoped>
  .wrap{
    padding: 0 20px;
  }
</style>
