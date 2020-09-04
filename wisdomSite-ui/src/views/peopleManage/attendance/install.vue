<template>
  <div class="reports-wrap">
    <el-tabs v-model="activeName">
      <!-- <el-tab-pane label="基础设置" name="first">
        <Roster  />
      </el-tab-pane> -->
      <el-tab-pane label="工地划分" name="second">
        <division v-if="activeName === 'second'" />
      </el-tab-pane>
      <el-tab-pane label="考勤机管理" name="dic">
        <manage  />
      </el-tab-pane>
      <el-tab-pane label="考勤机授权" name="fec">
        <authorize v-if="activeName === 'fec'" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Roster from "./components/Roster";
import AtteReports from "./components/AtteReports";
import manage from "./modules/manage";
import division from "./modules/division";
import authorize from "./modules/authorize";
export default {
  name: "Reports",
  components: {
    AtteReports,
    Roster,
    manage,
    division,
    authorize,
  },
  data() {
    return {
      activeName: 'second',
      // activeName: "second"
    };
  },
  mounted() {
    this.$DStore.refresh(['getProject'], true);
  },
  methods: {}
};
</script>

<style scoped lang="scss">

.reports-wrap {
  /deep/.el-tabs__content{
    overflow: inherit;
  }
  /deep/.el-tabs__item {
    font-size: 14px !important;
    font-weight: normal;
    color: #666;
  }
  /deep/.el-tabs__nav-wrap::after {
    height: 0.5px;
  }
}
</style>
