<template>
  <el-dialog title="整改" :visible.sync="flag" width="70%" @close="closeDialog" :close-on-click-modal="false">
    <div class="add-project-wrap">
      <el-form ref="rectificationForm" :model="rectificationForm" :rules="rules" size="small" label-width="120px">
        <el-form-item label="整改结果描述：" prop="repairResultDescription">
          <el-input type="textarea" :rows="2" v-model="rectificationForm.repairResultDescription" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="上传图片：" prop="repairImageAddres">
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
  import { addRepair } from '@/api/quality/workTask';

  export default {
    name: 'RectificationDialog',
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
      rectificationForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return{
        rectificationForm: {
          checkmanagerId: '',
          repairImageAddres: '',
          repairResultDescription: ''
        },
        dialogImageUrl: '',
        dialogVisible: false,
        // 表单校验
        rules: {
          repairResultDescription: [
            { required: true, message: "请输描述", trigger: "blur" }
          ]
        }
      }
    },
    methods: {
      /** 整改*/
      addRectification() {
        this.$refs["rectificationForm"].validate( valid => {
          if (!valid) return;
          this.rectificationForm.checkmanagerId = this.id;
          addRepair(this.rectificationForm).then(res => {
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
        this.rectificationForm = {};
        this.$refs["rectificationForm"].resetFields();
      },
      cancel() {
        this.flag = false;
      },
      picChange(pic) {
        this.rectificationForm.repairImageAddres = pic;
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
      rectificationForms(val) {
        this.rectificationForm = Object.assign({}, val);
      },
    }
  }
</script>

<style scoped>

</style>
