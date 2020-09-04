<template>
<div class="reports-wrap">
  <el-tabs v-model="activeName">
    <el-tab-pane label="花名册" name="first">
      <Roster v-if="activeName === 'first'"/>
    </el-tab-pane>
    <el-tab-pane label="考勤报表" name="second">
      <AtteReports v-if="activeName === 'second'"/>
    </el-tab-pane>
  </el-tabs>
</div>
</template>

<script>
  import Roster from "./components/Roster";
  import AtteReports from "./components/AtteReports";
  export default {
    name: 'Reports',
    components: {
      AtteReports,
      Roster,
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
.reports-wrap{
  /deep/.el-tabs__item{
    font-size: 14px !important;
    font-weight: normal;
    color: #666;
  }
  /deep/.el-tabs__nav-wrap::after{
    height: 0.5px;
  }
}
</style>
