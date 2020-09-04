<template>
  <section class="detailPage">
    <el-tabs v-model="activeName" class="warp_tab">
      <el-tab-pane label="环境监测" name="first">
        <realTime />
      </el-tab-pane>
      <el-tab-pane label="监测统计" name="dic">
        <monitor v-if="activeName === 'dic'" />
      </el-tab-pane>
      <el-tab-pane label="监测点位管理" name="second">
        <point v-if="activeName === 'second'" />
      </el-tab-pane>
    </el-tabs>
  </section>
</template>
<script>
import realTime from './components/realTime'
import monitor from './components/monitor'
import point from './modules/point'
export default {
  name: 'dustEnterprise',
  components: {
    realTime,
    monitor,
    point,
  },
  provide() {
		return {
			detailParm: this.detailData,
		};
	},
  props: {
    isShow: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: [Object, Array],
      default: () => {}
    },
    state: {
      type: String,
      default: () => null
    }
  },
  data () {
    return {
      activeName: "first",
    }
  }
}
</script>
<style lang='scss' scoped>
.detailPage{
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  height: calc(100vh - 84px);
  z-index: 2;
  background: #fff;
  padding: 0 17px;
}
::v-deep{
  .el-tabs__content{
    overflow: inherit;
  }
}
</style>
