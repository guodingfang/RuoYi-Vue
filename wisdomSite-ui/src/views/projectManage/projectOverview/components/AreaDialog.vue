<template>
  <el-dialog title="新增工地分区" :visible.sync="flag" width="500px" @close="closeDialog" :close-on-click-modal="false">
    <div class="add-project-wrap">
      <el-form ref="areaForm" :model="areaForm" :rules="rules" size="small" label-width="120px">
        <el-form-item label="分区名称：" prop="partitionName">
          <el-input v-model="areaForm.partitionName" placeholder=""></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="cancel()">取消</el-button>
        <el-button size="small" type="primary" @click="isCreate == false?updateArea():addArea()">确认</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
  import {
    addArea,
    updateArea,
  } from "@/api/project/projectArea";
  export default {
    name: 'AreaDialog',
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
      areaForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return{
        areaForm: {
          partitionName: '',
          projectId: undefined,
        },
        dialogImageUrl: '',
        dialogVisible: false,
        // 表单校验
        rules: {
          partitionName: [
            { required: true, message: "请输入名称", trigger: "blur" }
          ],
        }
      }
    },
    methods: {
      /** 添加里程碑*/
      addArea() {
        this.$refs["areaForm"].validate( valid => {
          if (!valid) return;
          addArea(Object.assign(this.areaForm, {projectId: this.$route.query.id})).then(res => {
            this.msgSuccess("添加成功");
            this.flag = false;
            this.$emit('query');
          }).catch(e => {
            console.log(e)
          })
        });
      },
      /** 编辑里程碑*/
      updateArea() {
        this.$refs["areaForm"].validate((valid) => {
          if (!valid) return;
          updateArea(this.areaForm).then(res => {
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
        this.areaForm = {};
        this.$refs["areaForm"].resetFields();
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
      areaForms(val) {
        this.areaForm = Object.assign({}, val);
        this.areaForm.id = Number(this.areaForm.id);
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
