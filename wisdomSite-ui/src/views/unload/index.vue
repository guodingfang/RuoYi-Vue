<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="实时监控" name="first">
        <real-time v-if="activeName === 'first'"/>
      </el-tab-pane>

      <el-tab-pane label="设备管理" name="second">
         <device-manage v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import RealTime from "./RealTime";
  import DeviceManage from "./DeviceManage";

  export default {
    name: 'TowerMonitor',
    components: {
      RealTime,
      DeviceManage
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

<style scoped  lang="scss">
.wrap{
  padding: 0 20px;
}
::v-deep{
  .el-tabs__content{
    overflow: inherit;
  }
}
</style>
