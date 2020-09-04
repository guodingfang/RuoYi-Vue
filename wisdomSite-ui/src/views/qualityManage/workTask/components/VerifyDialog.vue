<template>
  <el-dialog title="验证" :visible.sync="flag" width="70%" @close="closeDialog" :close-on-click-modal="false">
    <div class="add-project-wrap">
      <el-form ref="verifyForm" :model="verifyForm" :rules="rules" size="small" label-width="120px">
        <el-form-item label="验证结果：" prop="verifyResult">
          <el-radio-group v-model="verifyForm.verifyResult" class="com-right">
            <el-radio :label="1">合格</el-radio>
            <el-radio :label="2">不合格</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="验证结果描述：" prop="verifyResultDescription">
          <el-input type="textarea" :rows="2" v-model="verifyForm.verifyResultDescription" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="上传图片：" prop="verifyImageAddres">
          <mul-pic-upload @input="picChange"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancel()">取消</el-button>
        <el-button size="small" type="primary" @click="addRectification()">保存</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
  import MulPicUpload from '@/components/MulPicUpload/index';
  import { doVerify } from '@/api/quality/workTask';
  import { getAreaCode } from '@/utils/jlkj';

  export default {
    name: 'VerifyDialog',
    components: { MulPicUpload },
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      id: {
        required: true,
      },
      verifyForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return{
        verifyForm: {
          checkmanagerId: '',
          verifyImageAddres: '',
          verifyResultDescription: '',
          verifyResult: ""
        },
        dialogImageUrl: '',
        dialogVisible: false,
        // 表单校验
        rules: {
          verifyResult: [
            { required: true, message: "请选择结果", trigger: "blur" }
          ],
          verifyResultDescription: [
            { required: true, message: "请输描述", trigger: "blur" }
          ]
        }
      }
    },
    methods: {
      /** 整改*/
      addRectification() {
        this.$refs["verifyForm"].validate( valid => {
          if (!valid) return;
          this.verifyForm.checkmanagerId = this.id;
          doVerify(this.verifyForm).then(res => {
            this.msgSuccess("添加成功");
            this.flag = false;
            this.$emit('query');
          }).catch(e => {
            console.log(e)
          })
        });
      },
      /** 关闭框 */
      closeDialog() {
        this.verifyForm = {};
        this.$refs["verifyForm"].resetFields();
      },
      cancel() {
        this.flag = false;
      },
      picChange(pic) {
        this.verifyForm.verifyImageAddres = pic;
      }
    },
    computed: {
      flag: {
        get() {
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
        },
      },
    },
    watch: {
      verifyForms(val) {
        this.verifyForm = Object.assign({}, val);
      },
    }
  }
</script>

<style scoped>

</style>
