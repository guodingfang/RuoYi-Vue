<template>
  <el-dialog append-to-body  title="视频" :visible.sync="flag" width="1000px" @close="closeDialog" :close-on-click-modal="false">
    <camera-control :dialogFlag="dialogFlag" :videoEquipmentId="videoEquipmentId"/>
  </el-dialog>
</template>

<script>
  import CameraControl from "./CameraControl";
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
      // liveInfo: {
      //   type: Object,
      //   default: () => {},
      // },
      videoEquipmentId: {
        type: [String, Number],
        default: null
      }
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
