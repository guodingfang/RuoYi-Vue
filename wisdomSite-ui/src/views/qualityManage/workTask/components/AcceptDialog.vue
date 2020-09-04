<template>
  <el-dialog title="验收" :visible.sync="flag" width="70%" @close="closeDialog" :close-on-click-modal="false">
    <div class="add-project-wrap">
      <el-form ref="acceptForm" :model="acceptForm" :rules="rules" size="small" label-width="120px">
        <el-form-item label="验收结果：" prop="acceptanceState">
          <el-radio-group v-model="acceptForm.acceptanceState" class="com-right">
            <el-radio :label="1">合格</el-radio>
            <el-radio :label="2">不合格</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="验收结果描述：" prop="acceptanceDescribe">
          <el-input type="textarea" :rows="2" v-model="acceptForm.acceptanceDescribe" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="上传图片：" prop="verifyImageAddres">
          <mul-pic-upload @input="picChange"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancel()">取消</el-button>
        <el-button size="small" type="primary" @click="addAcceptance()">保存</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
  import MulPicUpload from '@/components/MulPicUpload/index';
  import { addRecord } from '@/api/quality/workTask';

  export default {
    name: 'AcceptDialog',
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
      acceptForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return{
        acceptForm: {
          applyId: '',
          verifyImageAddres: '',
          verifyResultDescription: '',
          verifyResult: ""
        },
        dialogImageUrl: '',
        dialogVisible: false,
        // 表单校验
        rules: {
          acceptanceState: [
            { required: true, message: "请选择结果", trigger: "blur" }
          ],
          acceptanceDescribe: [
            { required: true, message: "请输描述", trigger: "blur" }
          ]
        }
      }
    },
    methods: {
      /** 验收*/
      addAcceptance() {
        this.$refs["acceptForm"].validate( valid => {
          if (!valid) return;
          this.acceptForm.applyId = this.id;
          addRecord(this.acceptForm).then(res => {
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
        this.acceptForm = {};
        this.$refs["acceptForm"].resetFields();
      },
      cancel() {
        this.flag = false;
      },
      picChange(pic) {
        this.acceptForm.acceptanceImageAddres = pic;
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
      acceptForms(val) {
        this.acceptForm = Object.assign({}, val);
      },
    }
  }
</script>

<style scoped>

</style>
