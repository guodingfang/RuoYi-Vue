<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="项目库" name="first">
        <ProjectLib v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="里程碑设置" name="second">
        <Milestone v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import ProjectLib from './components/ProjectLib';
  import Milestone from './components/Milestone'
  export default {
    name: 'projectManage',
    components: {
      Milestone,
      ProjectLib,
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

<style scoped>
.wrap{
  padding: 0 20px;
}
</style>
