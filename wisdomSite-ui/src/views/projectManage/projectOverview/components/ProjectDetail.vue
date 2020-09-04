<template>
  <el-dialog :visible.sync="flag" width="70%" @close="closeDialog" :close-on-click-modal="false">
    <div class="add-project-wrap">
      <el-form ref="projectForm" :model="projectForm" :rules="rules" size="small" label-width="140px">
          <el-form-item label="项目名称：">
            <el-input v-model="projectForm.projectName" placeholder=""></el-input>
          </el-form-item>
          <el-form-item label="简称：">
            <el-input v-model="projectForm.projectAbbreviation" placeholder=""></el-input>
          </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="归属公司：">
              <el-input v-model="projectForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目负责人：">
              <el-select placeholder="请选择项目负责人" v-model="projectForm.projectPersonName" class="w100">
                <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目状态：">
              <el-select placeholder="请选择项目状态" v-model="projectForm.projectStatus"  class="w100">
                <el-option label="未建" value="3"></el-option>
                <el-option label="完工" value="2"></el-option>
                <el-option label="在建" value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="建设规模：">
              <el-radio-group v-model="projectForm.buildScale" class="w100">
                <el-radio :label="3">大型</el-radio>
                <el-radio :label="2">中型</el-radio>
                <el-radio :label="1">小型</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="投资总额(万元)：">
          <el-input v-model="projectForm.totalInvestment" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="项目LOGO：">
          <el-upload
            action="https://jsonplaceholder.typicode.com/posts/"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="isCreate == false?updateProject():addProject()">保 存</el-button>
      </div>
      <el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body>
        <img width="100%" :src="dialogImageUrl" alt="">
      </el-dialog>
    </div>
  </el-dialog>
</template>

<script>
  import {getProjectById, addProject, updateProject, deleteProject, exportProject} from "@/api/project/project";
  export default {
    name: 'ProjectDetail',
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
      projectForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return{
        projectForm: {},
        dialogImageUrl: '',
        dialogVisible: false,
        cities: [
          {value: 1, label: '北京'},
          {value: 'Shanghai', label: '上海'},
          {value: 'Nanjing', label: '南京'},
          {value: 'Chengdu', label: '成都'},
          {value: 'Shenzhen', label: '深圳'},
          {value: 'Guangzhou', label: '广州'}
        ],
        // 表单校验
        rules: {
          projectName: [
            { required: true, message: "项目名称", trigger: "blur" }
          ],
          invokeTarget: [
            { required: true, message: "调用目标字符串不能为空", trigger: "blur" }
          ],
          cronExpression: [
            { required: true, message: "cron执行表达式不能为空", trigger: "blur" }
          ]
        }
      }
    },
    methods: {
      /** 添加项目*/
      addProject() {
        this.$refs["projectForm"].validate( valid => {
          if (!valid) return;
            addProject(this.projectForm).then(res => {
              this.msgSuccess("添加成功");
              this.flag = false;
              this.$emit('query');
            }).catch(e => {
              console.log(e)
            })
        });
      },
      /** 编辑项目*/
      updateProject() {
        this.$refs["projectForm"].validate((valid) => {
          if (!valid) return;
            updateProject(this.projectForm).then(res => {
              this.msgSuccess("编辑成功");
              this.flag = false;
              this.$emit('query');
            }).catch(e => {
              console.log(e)
            })

        });
      },
      /* 关闭项目框 */
      closeDialog() {
        this.projectForm = {};
        this.$refs["projectForm"].resetFields();
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["projectForm"].validate(valid => {
          if (valid) {
            if (this.form.jobId != undefined) {
              updateJob(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addJob(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          }
        });
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
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
      projectForms(val) {
        console.log('我是val',val)
        this.projectForm = Object.assign({}, val);
        this.projectForm.id = Number(this.projectForm.id);
        this.projectForm.projectStatus = this.projectForm.projectStatus.toString();
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
