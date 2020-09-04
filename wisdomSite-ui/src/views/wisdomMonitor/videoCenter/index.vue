<template>
  <div class="wrap">
    <div class="add-device">
      <el-button v-if="activeName === 'second'" type="primary" @click="addVideo">新增视频监控点</el-button>
      <el-button v-if="activeName === 'first'" type="text" @click="selProj">切换项目</el-button>
    </div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="视频监控" name="first">
        <video-moni ref="moni" v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="摄像头配置" name="second">
        <cam-setting ref="setting" v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
    <add-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="addDialogFlag"
      @query="updateCamList"
    />
  </div>
</template>

<script>
  import VideoMoni from "./VideoMoni";
  import CamSetting from "./CamSetting";
  import AddDialog from "./components/AddDialog";

  export default {
    name: 'VideoCenter',
    components: {
      VideoMoni,
      CamSetting,
      AddDialog,
    },
    data() {
      return {
        activeName: 'first',
        isCreate: true,
        addDialogFlag: false,
      }
    },
    mounted() {
    },
    methods: {
      addVideo() {
        this.addDialogFlag = true;
      },
      selProj() {
        this.$refs.moni.showSel = !this.$refs.moni.showSel;
      },
      updateCamList() {
        this.$refs.setting.getCamList()
      }
    },
  }
</script>

<style scoped lang="scss">
  .wrap{
    padding: 0 20px;
    position: relative;
    .add-device{
      position: absolute;
      top: 10px;
      right: 20px;
      z-index: 99;
    }
  }
</style>
