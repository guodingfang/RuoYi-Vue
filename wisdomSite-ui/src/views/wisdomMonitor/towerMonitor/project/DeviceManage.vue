<template>
  <div class="device-mana-wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="在场设备" name="first">
        <presence v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="离场设备" name="second">
        <absence v-if="activeName === 'second'"/>
      </el-tab-pane>
      <el-tab-pane label="防碰撞设置" name="third">
        <anti-collision  v-if="activeName === 'third'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import AntiCollision from "./components/AntiCollision";
  import Absence from "./components/Absence";
  import Presence from "./components/Presence";
  export default {
    name: "DeviceManage",
    components: {
      AntiCollision,
      Absence,
      Presence
    },
    data() {
      return {
        activeName: 'first',
      }
    },
  }
</script>

<style scoped lang="scss">
  .device-mana-wrap{
    /deep/.el-tabs__item{
      font-size: 14px !important;
      font-weight: normal;
      color: #666;
    }
    /deep/.el-tabs__nav-wrap::after{
      height: .5px;
    }
  }
</style>
