<template>
  <div class="wrap">
    <el-tabs v-model="activeName" class="warp_tab">
      <el-tab-pane label="用电安全" name="first">
        <electricity v-if="activeName === 'first'" />
      </el-tab-pane>
      <el-tab-pane label="能耗监测" name="dic">
        <monitoring v-if="activeName === 'dic'" />
      </el-tab-pane>
      <el-tab-pane label="电能监测配置中心" name="second">
        <configure v-if="activeName === 'second'" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
// import {} from "@/api/quality/cadManage";
import ECharts from "vue-echarts";
import "echarts/lib/chart/bar";
import "echarts/lib/chart/line";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import monitoring from './modules/monitoring'
import electricity from './modules/electricity'
import configure from './modules/configure'

export default {
  name: "scada",
  components: {
    chart: ECharts,
    electricity,
    monitoring,
    configure,
    // roster,
    // blacklist,
    // exit,
    // external
  },
  data() {
    return {
      activeName: "first",
      // activeName: "second",
    };
  },
  mounted() {
    this.$DStore.refresh(['getProject'], true);
    // [
    //   {
    //     date: '1',
    //     arr:[xxx,xxx]
    //   },
    //   {
    //     date: '2',
    //     arr:[xxx,xxx]
    //   },
    // ]
  },
  methods: {}
};
</script>

<style scoped lang="scss">
.wrap {
  padding: 0 10px;
  height: 100%;
}
::v-deep {
  .warp_tab {
    .el-tabs__content {
      overflow: inherit;
    }
  }
}
// .el-tabs{
//   height: 100%;
//   ::v-deep{
//     .el-tabs__content{
//       height: calc(100% - 52px);
//       .el-tab-pane{
//         height: 100%;
//       }
//     }
//   }

// }
</style>
