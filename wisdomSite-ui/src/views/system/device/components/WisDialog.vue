<template>
  <el-dialog  title="监测设备绑定" :visible.sync="flag" width="800px" @close="closeDialog" :close-on-click-modal="false">
    <el-form
      ref="wisForm"
      :model="wisForm"
      :rules="wisRules"
      size="medium"
      label-width="180px"
    >
      <el-form-item label="设备类型" prop="equipmentType" >
        <el-radio-group v-model="wisForm.equipmentType" :disabled="!isCreate" size="small" >
          <el-radio-button v-for="item in deviceType" :label="item.dictValue" :key="item.dictValue">{{item.dictLabel}}</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="设备编号" prop="serialNumber">
        <el-input  v-model="wisForm.serialNumber" :disabled="!isCreate"></el-input>
      </el-form-item>
      <el-form-item label="工程名称" prop="projectId">
        <project-select type="all" :disabled="!isCreate && !isMove" :formProjId.sync="wisForm.projectId"/>
      </el-form-item>
      <el-form-item label="安装位置（原施工楼号）" prop="installationPosition">
        <el-input  v-model="wisForm.installationPosition"></el-input>
      </el-form-item>
      <el-form-item label="流量卡号" prop="flowcardNo">
        <el-input :disabled="false" v-model="wisForm.flowcardNo"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="isCreate === false?handleUpdate():handleAdd()">保存</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import {
    addDevice,
    updateDevice
  } from "@/api/system/device";
  import ProjectSelect from "@/components/CommonSelect/ProjectSelect";
  export default {
    name: 'WisDialog',
    components: {
      ProjectSelect
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
      wisForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return {
        wisForm: {
          equipmentType: '',
        },
        treeListProps: {
          label: 'name',
          value: 'id',
        },
        deviceType: [],
        /* 车辆框验证规则 */
        wisRules: {
          equipmentType: [
            { required: true, message: '请选择设备类型', trigger: 'blur' },
          ],
          serialNumber: [
            { required: true, message: '请填写设备编号', trigger: 'blur' },
          ],
          projectId: [
            { required: true, message: '请填写工程名称', trigger: 'blur' },
          ],
          installationPosition: [
            { required: true, message: '请填写安装位置', trigger: 'blur' },
          ]
        },
      };
    },
    created() {
      this.getDicts("ws_equipment_type").then(response => {
        this.deviceType = response.data;
      });
    },
    methods: {
      /* 添加 */
      handleAdd() {
        this.$refs.wisForm.validate(async (valid) => {
          if (!valid) return;
          try {
            console.log(this.wisForm)
            await addDevice(this.wisForm);
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
        this.$refs.wisForm.validate(async (valid) => {
          if (!valid) return;
          try {
            await updateDevice(this.wisForm);
            this.msgSuccess('编辑成功');
            this.flag = false;
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /* 关闭 */
      closeDialog() {
        this.wisForm = {};
        this.$refs.wisForm.resetFields();
      },
    },
    computed: {
      flag: {
        get() {
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
          this.$emit('update:wisForms', {});
          this.isMove ? this.$emit('update:isMove', false) : ''
        },
      },
    },
    watch: {
      wisForms(val) {
        console.log(val)
        this.wisForm = Object.assign({}, val);
        // this.wisForm.officeId = Number(this.wisForm.officeId);
      },
    },
  };
</script>

<style lang='less' scoped>

</style>
