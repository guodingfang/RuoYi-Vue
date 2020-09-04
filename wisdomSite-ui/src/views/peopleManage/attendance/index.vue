<template>
  <div class="att-warp" id="att-warp" >
    <el-tabs v-model="activeName">
      <el-tab-pane label="  首页" name="first">
        <component v-if="activeName === 'first'" :is="home"></component>
      </el-tab-pane>
      <el-tab-pane label="出入记录" name="second">
        <AccessRecord v-if="activeName === 'second'"/>
      </el-tab-pane>
      <el-tab-pane label="报表中心" name="third">
        <Reports v-if="activeName === 'third'"/>
      </el-tab-pane>
      <el-tab-pane label="建筑工人库" name="fourth">
        <workerLib v-if="activeName === 'fourth'" />
      </el-tab-pane>
      <el-tab-pane label="参建单位库" name="fifth">
       <projunit v-if="activeName === 'fifth'"/>
      </el-tab-pane>
      <el-tab-pane label="黑名单记录" name="sixth">
       <blacklist v-if="activeName === 'sixth'"/>
      </el-tab-pane>
      <el-tab-pane label="设置" name="seventh">
       <install v-if="activeName === 'seventh'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import Home from "./Home";
  import HomeProj from "./HomeProj";
  import AccessRecord from './AccessRecord'
  import Reports from './Reports'
  import workerLib from './modules/workerLib'
  import projunit from './modules/projunit'
  import blacklist from './modules/blacklist'
  import install from './install'
  export default {
    name: 'index',
    components: {
      AccessRecord,
      Home,
      HomeProj,
      Reports,
      workerLib,
      projunit,
      blacklist,
      install,
    },
    data() {
      return {
        home: this.$store.state.user.curRole.type === 0 ? 'Home' : 'HomeProj',
        activeName: 'first',
      }
    },
    mounted() {
    },
    methods: {

    },
  }
</script>

<style scoped lang="scss" >
.att-warp{
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
