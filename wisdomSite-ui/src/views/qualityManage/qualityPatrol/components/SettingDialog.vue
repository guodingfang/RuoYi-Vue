<template>
  <el-dialog title="新增项目里程碑模版" :visible.sync="flag" width="500px" @close="closeDialog" :close-on-click-modal="false">
    <div class="add-project-wrap">
      <el-form ref="stoneForm" :model="stoneForm" :rules="rules" size="small" label-width="120px">
        <el-form-item label="里程碑名称：" prop="milepostName">
          <el-input v-model="stoneForm.milepostName" placeholder=""></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancel()">取消</el-button>
        <el-button size="small" type="primary" @click="isCreate == false?updateStone():addStone()">确认</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
  import { getProjectMilepostById, removeProjectMilepost, addProjectMilepost, updateProjectMilepost, deleteProjectMilepost} from "@/api/project/projectMilepost";
  export default {
    name: 'SettingDialog',
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
      stoneForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return{
        stoneForm: {},
        dialogImageUrl: '',
        dialogVisible: false,
        // 表单校验
        rules: {
          milepostName: [
            { required: true, message: "请输入名称", trigger: "blur" }
          ]
        }
      }
    },
    methods: {
      /** 添加里程碑*/
      addStone() {
        this.$refs["stoneForm"].validate( valid => {
          if (!valid) return;
          addProjectMilepost(this.stoneForm).then(res => {
            this.msgSuccess("添加成功");
            this.flag = false;
            this.$emit('query');
          }).catch(e => {
            console.log(e)
          })
        });
      },
      /** 编辑里程碑*/
      updateStone() {
        this.$refs["stoneForm"].validate((valid) => {
          if (!valid) return;
          updateProjectMilepost(this.stoneForm).then(res => {
            this.msgSuccess("编辑成功");
            this.flag = false;
            this.$emit('query');
          }).catch(e => {
            console.log(e)
          })

        });
      },
      /** 关闭项目框 */
      closeDialog() {
        this.stoneForm = {};
        this.$refs["stoneForm"].resetFields();
      },
      cancel() {
        this.flag = false;
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
      stoneForms(val) {
        this.stoneForm = Object.assign({}, val);
        this.stoneForm.id = Number(this.stoneForm.id);
      },
    },
  }
</script>

<style scoped lang="scss">
  .dialog-footer{
    text-align: right;
  }
  .add-project-wrap{
    /deep/.el-upload-list--picture-card{
      .el-upload-list__item {
        width: 80px;
        height: 80px;
        line-height: 82px;
        border-radius: 0;
      }
    }
    /deep/.el-upload--picture-card {
      width: 80px;
      height: 80px;
      line-height: 82px;
      border-radius: 0;
    }
  }
</style>
