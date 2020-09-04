<template>
  <el-dialog  title="视频设备绑定" :visible.sync="flag" width="800px" @close="closeDialog" :close-on-click-modal="false">
    <el-form
      ref="camForm"
      :model="camForm"
      :rules="camRules"
      size="medium"
      label-width="240px"
    >
      <el-form-item label="设备序列号" prop="equipmentSerialnumber" >
        <el-input  v-model="camForm.equipmentSerialnumber" :disabled="!isCreate"></el-input>
      </el-form-item>
      <el-form-item label="设备验证码：" prop="verificationCode">
        <el-input  v-model="camForm.verificationCode" :disabled="!isCreate"></el-input>
      </el-form-item>
      <el-form-item label="工程名称：" prop="projectId">
        <project-select type="all" :disabled="!isCreate && !isMove" :formProjId.sync="camForm.projectId"/>
      </el-form-item>
      <el-form-item label="安装位置：" prop="installationPosition">
        <el-input  v-model="camForm.installationPosition"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="isCreate === false?handleUpdate():handleAdd()">保存</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import {
    addCamear,
    updateCamear,
    getCamearWithSN,
    moveCamear,
  } from "@/api/system/device";
  import ProjectSelect from "@/components/CommonSelect/ProjectSelect";
  export default {
    name: 'VideoDialog',
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
      camForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      let checkSN = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('设备序列号不能为空'));
        }
        console.log('====================',this.isCreate)
        if(this.isCreate) {
          getCamearWithSN(value).then(res => {
            if (res.data) {
              return callback(new Error('该设备已存在'));
            }
          })
        }
        callback();
      };
      return {
        camForm: {
          equipmentType: '',
        },
        treeListProps: {
          label: 'name',
          value: 'id',
        },
        deviceType: [],
        camRules: {
          equipmentSerialnumber: [
            {required: true,  validator: checkSN, trigger: 'blur' },
          ],
          verificationCode: [
            { required: true, message: '请填写设备验证码', trigger: 'blur' },
          ],
          projectId: [
            { required: true, message: '请填写工程名称', trigger: 'blur' },
          ],
          installationPosition: [
            { required: true, message: '请填写安装位置', trigger: 'blur' },
          ],
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
        this.$refs.camForm.validate(async (valid) => {
          if (!valid) return;
          try {
            await addCamear(this.camForm);
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
            this.isMove ? await moveCamear(this.camForm) : await updateCamear(this.camForm);
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
        this.camForm = {};
        this.$refs.camForm.resetFields();
      },
    },
    computed: {
      flag: {
        get() {
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
