<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="实时监控" name="first">
        <real-time v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="监控统计" name="second">
        <statistic v-if="activeName === 'second'"/>
      </el-tab-pane>
      <el-tab-pane label="设备管理" name="third">
        <device-manage v-if="activeName === 'third'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import RealTime from "./RealTime";
  import Statistic from "./Statistic";
  import DeviceManage from "./DeviceManage";

  export default {
    name: 'TowerMonitor',
    components: {
      DeviceManage,
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

    },
  }
</script>

<style scoped lang="scss">
  .wrap{
    padding: 0 20px;
    height: calc(100vh - 50px);
    overflow: auto;
  }
  ::v-deep{
    .el-tabs__content {
      overflow: inherit;
      position: relative;
    }
  }
</style>
