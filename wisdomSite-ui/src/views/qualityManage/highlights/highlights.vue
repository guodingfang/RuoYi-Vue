<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="项目部亮点" name="first">
        <projectDept v-if="activeName === 'first'"/>
      </el-tab-pane>
      <!-- <el-tab-pane label="外部观摩亮点" name="second">
        <external v-if="activeName === 'second'"/>
      </el-tab-pane> -->
    </el-tabs>
  </div>
</template>

<script>
  import projectDept from './modules/projectDept'
  import external from './modules/external'

  export default {
    name: 'Highlights',
    components: {
      projectDept,
      external
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
