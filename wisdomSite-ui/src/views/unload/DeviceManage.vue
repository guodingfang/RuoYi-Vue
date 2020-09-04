<template>
  <div class="device-mana-wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="设备管理" name="first">
        <presence v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="场布图设置" name="second">
        <layout-map v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import Presence from "./modules/Presence";
  import LayoutMap from "./modules/LayoutMap";
  export default {
    name: "DeviceManage",
    components: {
      Presence,
      LayoutMap
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
