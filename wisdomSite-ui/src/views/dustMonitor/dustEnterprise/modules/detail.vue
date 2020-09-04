<template>
  <section class="detailPage">
    <el-tabs v-model="activeName" class="warp_tab" @tab-click='handleClick'>
      <el-tab-pane label="环境监测" name="first">
        <realTime />
      </el-tab-pane>
      <el-tab-pane label="监测统计" name="dic">
        <monitor v-if="activeName === 'dic'" />
      </el-tab-pane>
    </el-tabs>
  </section>
</template>
<script>
import realTime from './../components/realTime'
import monitor from './../components/monitor'
export default {
  name: 'dustEnterprise',
  components: {
    realTime,
    monitor,
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
  watch: {
    activeName(newVal, oldVal) {
      this.flag = newVal
      // console.log(newVal, oldVal);
    },
  },
  methods: {
    handleClick(tab, event) {
      // console.log(this.flag, this.activeName);
      if (this.flag == this.activeName && this.flag == 'first') {
        this.$emit('on-close')
      }
    }
  },
  data () {
    return {
      activeName: "first",
      flag: "first",
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
