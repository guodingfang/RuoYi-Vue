<template>
  <div class="wrap">
    <el-tabs v-model="activeName">
      <el-tab-pane label="实测实量" name="first">
        <Measure v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="分项工程设置" name="second">
        <setting v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import Measure from './components/Measure'
  import Setting from './components/Setting'

  export default {
    name: 'QualityMeasure',
    components: {
      Setting,
      Measure
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
    height: 100%;
  }
</style>
