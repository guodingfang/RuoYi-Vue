<template>
  <el-dialog  :title="isCreate?'新增监控点':'编辑摄像头'" :visible.sync="flag" width="600px" @close="closeDialog" :close-on-click-modal="false">
    <el-form
      ref="camForm"
      :model="camForm"
      :rules="camRules"
      size="medium"
      label-width="160px"
    >
      <el-form-item label="监控设备" prop="videoSerialNumber" >
        <el-input :disabled="!isCreate" v-model="camForm.videoSerialNumber">
          <el-button type="text" slot="prefix" @click="selCam">选择</el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="视频监控点：" prop="videoMonitoryPointName">
        <el-input  v-model="camForm.videoMonitoryPointName"></el-input>
      </el-form-item>
      <el-form-item label="区域名称：" prop="zoneId">
        <el-select placeholder="" v-model="camForm.zoneId"  class="w100">
          <el-option label="默认分区" :value="0"></el-option>
          <el-option
            v-for="item in areaList"
            :key="item.id"
            :label="item.partitionName"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="isCreate === false?handleUpdate():handleAdd()">保存</el-button>
    </span>
    <sel-cam
      :listData="camList"
      :dialogFlag.sync="showSel"
      @change="getCam"
    />
  </el-dialog>
</template>

<script>
  import {
    getChannelList,
    getPartlList,
    addMoniPoint,
    updateMoniPoint
  } from "@/api/smartMonitor/video";
  import SelCam from "./SelCam";
  export default {
    name: 'AddDialog',
    components: {
      SelCam
    },
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      isCreate: {
        type: Boolean,
        default: true,
      },
      isMove: {
        type: Boolean,
        default: false,
      },
      camForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return {
        showSel:false,
        selItem: {},
        camList: [],
        areaList: [],
        camForm: {
          zoneId: 0
        },
        treeListProps: {
          label: 'name',
          value: 'id',
        },
        camRules: {
          videoSerialNumber: [
            {required: true, message: '请选择设备',  trigger: 'blur' },
          ],
          videoMonitoryPointName: [
            { required: true, message: '视频监控点', trigger: 'blur' },
          ],
          zoneId: [
            { required: true, message: '请选择区域', trigger: 'blur' },
          ],
          installationPosition: [
            { required: true, message: '请填写安装位置', trigger: 'blur' },
          ],
        },
      };
    },
    created() {
    },
    methods: {
      /* 添加 */
      handleAdd() {
        this.$refs.camForm.validate(async (valid) => {
          if (!valid) return;
          try {
            await addMoniPoint(this.camForm);
            this.msgSuccess('添加成功');
            this.flag = false;
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /* 编辑 */
      handleUpdate() {
        this.$refs.camForm.validate(async (valid) => {
          if (!valid) return;
          try {
            const {id, projectId, videoMonitoryPointName, videoSerialNumber, videoChannel, zoneId } = this.camForm;
            await updateMoniPoint({
              id,
              projectId,
              videoMonitoryPointName,
              videoSerialNumber,
              videoChannel,
              zoneId
              });
            this.msgSuccess('编辑成功');
            this.flag = false;
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /*选择设备*/
      async selCam() {
        const { rows:camList } = await getChannelList(0);
        this.camList = camList;
        this.showSel = true;
      },
      async getAreaList() {
        const { data } = await getPartlList(this.$store.state.videoMoni.videoProject.projectId);
        this.areaList = data;
      },
      getCam(e) {
        this.selItem = e;
        this.camForm = {
          ...this.camForm,
          videoSerialNumber: e.equipmentSerialnumber,
          videoChannel: e.channelNumber,
          projectId: e.projectId,
          videoMonitoryPointName: e.installationPosition
        }
      },
      /* 关闭 */
      closeDialog() {
        this.camForm = {};
        this.$refs.camForm.resetFields();
      },
    },
    computed: {
      flag: {
        get() {
          if(this.dialogFlag) {
            this.getAreaList()
          }
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
          this.$emit('update:camForms', {});
          this.isMove ? this.$emit('update:isMove', false) : ''
        },
      },
    },
    watch: {
      camForms(val) {
        this.camForm = Object.assign({}, val);
        // this.camForm.officeId = Number(this.camForm.officeId);
      },
    },
  };
</script>

<style lang='scss' scoped>

</style>
