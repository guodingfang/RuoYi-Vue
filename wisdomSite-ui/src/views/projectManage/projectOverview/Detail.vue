<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="项目概况" name="overview">
        <OverView :id="id" v-if="activeName === 'overview'"/>
      </el-tab-pane>
      <el-tab-pane label="工程信息" name="engineer">
        <Engineer :id="id" v-if="activeName === 'engineer'"/>
      </el-tab-pane>
      <el-tab-pane label="里程碑设置" name="stone">
        <Stone :id="id" v-if="activeName === 'stone'"/>
      </el-tab-pane>
      <el-tab-pane label="工地分区" name="area">
        <Area :id="id" v-if="activeName === 'area'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import OverView from './components/OverView';
  import Engineer from './components/Engineer'
  import Stone from './components/Stone'
  import Area from './components/Area'
  export default {
    name: 'Detail',
    components: {
      OverView,
      Engineer,
      Stone,
      Area,
    },
    data() {
      return {
        activeName: 'first',
        id: '',
      }
    },
    created() {
      let { name, id } =  this.$route.query;
      this.activeName = name;
      this.id = id;
    },
    methods: {

    },
  }
</script>

<style scoped>
  .wrap{
    padding: 0 20px;
  }
</style>
