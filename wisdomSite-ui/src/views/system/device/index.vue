<template>
  <div class="wrap">
    <div class="add-device"><el-button type="primary" @click="activeName==='first'?addWis():addVideo()">新增设备绑定</el-button></div>
    <el-tabs v-model="activeName">
      <el-tab-pane label="智能检测设备" name="first">
        <WisDevice ref="wisdevice" v-if="activeName === 'first'"/>
      </el-tab-pane>
      <el-tab-pane label="视频监控设备" name="second">
        <VideoDevice ref="videodevice" v-if="activeName === 'second'"/>
      </el-tab-pane>
    </el-tabs>
    <wis-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="wisDialogFlag"
      @query="updateDeviceList"
    />
    <video-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="videoDialogFlag"
      @query="updateVideoList"
    />
  </div>
</template>

<script>
  import WisDevice from "./components/WisDevice";
  import VideoDevice from "./components/VideoDevice";
  import WisDialog from "./components/WisDialog";
  import VideoDialog from "./components/VideoDialog";

  export default {
    name: 'DeviceBind',
    components: {
      WisDevice,
      WisDialog,
      VideoDevice,
      VideoDialog
    },
    data() {
      return {
        activeName: 'first',
        isCreate: true,
        wisDialogFlag: false,
        videoDialogFlag: false,
      }
    },
    mounted() {
    },
    methods: {
      /* 打开添加 */
      addWis() {
        this.wisDialogFlag = true;
      },
      addVideo() {
        this.videoDialogFlag = true;
      },
      updateDeviceList() {
        this.$refs.wisdevice.getDeviceList()
      },
      updateVideoList() {
        this.$refs.videodevice.getCamearList()
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
      /*z-index: 99;*/
    }
  }
</style>
