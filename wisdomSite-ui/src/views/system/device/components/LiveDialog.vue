<template>
  <el-dialog  title="视频" :visible.sync="flag" width="1000px" @close="closeDialog" :close-on-click-modal="false">
    <camera-control :liveInfo="liveInfo"/>
  </el-dialog>
</template>

<script>
  import {
    addDevice,
    updateDevice,
    getCamearWithSN
  } from "@/api/system/device";
  import CameraControl from "@/components/CameraControl/index";
  export default {
    name: 'LiveDialog',
    components: {
      CameraControl
    },
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      liveInfo: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return {
      };
    },
    created() {

    },
    methods: {
      closeDialog() {

      },
    },
    computed: {
      flag: {
        get() {
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val)
        },
      },
    },
    watch: {
    },
  };
</script>

<style lang='less' scoped>

</style>
