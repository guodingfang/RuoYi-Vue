<template>
  <el-dialog title="工程信息" :visible.sync="flag" width="80%" @close="closeDialog" :close-on-click-modal="false">
      <el-form class="wrap" ref="engineeringForm" :model="engineeringForm" :rules="rules" size="small" label-width="140px">
        <!-- 工程属性 -->
        <h4 class="tit">工程属性</h4>
        <el-form-item label="建筑用途：">
          <el-select placeholder="请选择项目状态" v-model="engineeringForm.projectStatus"  class="w100">
            <el-option label="未建" value="3"></el-option>
            <el-option label="完工" value="2"></el-option>
            <el-option label="在建" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="建筑结构类型：">
          <el-input v-model="engineeringForm.projectName" placeholder=""></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单体数量(个)：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="建筑面积(m²)：">
              <el-select placeholder="请选择项目负责人" v-model="engineeringForm.projectPersonName" class="w100">
                <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="地上面积(m²)：">
<!--              <label slot="label">地上面积（m<sup>2</sup>）：</label>-->
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地下面积(m²)：">
              <el-select placeholder="请选择项目负责人" v-model="engineeringForm.projectPersonName" class="w100">
                <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="建筑高度(m)：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地上层数：">
              <el-select placeholder="请选择项目负责人" v-model="engineeringForm.projectPersonName" class="w100">
                <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="地下深度(m)：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="基坑深度(m)：">
              <el-select placeholder="请选择项目负责人" v-model="engineeringForm.projectPersonName" class="w100">
                <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 关联单位 -->
        <h4 class="tit">关联单位</h4>
        <el-row>
          <el-col :span="12">
            <el-form-item label="建设单位：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设计单位：：">
              <el-select placeholder="请选择项目负责人" v-model="engineeringForm.projectPersonName" class="w100">
                <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="监理单位：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="施工(总包)单位：：">
              <el-select placeholder="" v-model="engineeringForm.projectPersonName" class="w100">
                <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="安全监督单位：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="质量监督单位：：">
              <el-select placeholder="" v-model="engineeringForm.projectPersonName" class="w100">
                <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 施工信息 -->
        <el-form-item label="投资类型：">
          <el-select placeholder="请选择投资类型" v-model="engineeringForm.projectStatus"  class="w100">
            <el-option label="政府投资" :value="1"></el-option>
            <el-option label="国有企业投资" :value="2"></el-option>
            <el-option label="非国有企业投资" :value="3"></el-option>
            <el-option label="其它" :value="4"></el-option>
          </el-select>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="中标编号：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同造价(万元)：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 许可证上传start-->
        <el-form-item label="施工许可证：">
          <div class="xkz">
            <div class="xkz-t">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </div>
            <div class="xkz-b">
              <el-upload
                action="https://jsonplaceholder.typicode.com/posts/"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove">
                <i class="el-icon-plus"></i>
              </el-upload>
            </div>
          </div>
        </el-form-item>
        <!-- 许可证上传end-->
        <el-row>
          <el-col :span="12">
            <el-form-item label="开工时间：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="完工时间：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目经理：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="发薪银行：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开户银行：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="发薪账号：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发薪日期：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="质量目标：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="安全目标：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="合同备案号：">
          <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="备注：">
          <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="中标编号：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同造价(万元)：">
              <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="里程碑计划：">
          <div class="lcb-wrap">
            <div class="lcb-t">
              <span class="col1">里程碑</span>
              <span class="col2">时间</span>
            </div>
            <div class="lcb-b">
              <el-form-item label="开工：">
                <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
              </el-form-item>
              <el-form-item label="地下结构封顶：">
                <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
              </el-form-item>
              <el-form-item label="主体全部封顶：">
                <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
              </el-form-item>
              <el-form-item label="主体结构验收：">
                <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
              </el-form-item>
              <el-form-item label="竣工验收：">
                <el-input v-model="engineeringForm.projectUnitName" placeholder=""></el-input>
              </el-form-item>
            </div>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="isCreate == false?updateProject():addProject()">保 存</el-button>
      </div>
      <el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body>
        <img width="100%" :src="dialogImageUrl" alt="">
      </el-dialog>
  </el-dialog>
</template>

<script>
  import {getProjectById, addProject, updateProject, deleteProject, exportProject} from "@/api/project/project";
  export default {
    name: 'EngineeringDialog',
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      engineeringForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return{
        engineeringForm: {},
        dialogImageUrl: '',
        dialogVisible: false,
        cities: [],
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
      /** 保存*/

      /* 关闭框 */
      closeDialog() {
        this.engineeringForm = {};
        this.$refs["engineeringForm"].resetFields();
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["engineeringForm"].validate(valid => {
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
      engineeringForms(val) {
        console.log('我是val',val)
        this.engineeringForm = Object.assign({}, val);
        this.engineeringForm.id = Number(this.engineeringForm.id);
        this.engineeringForm.projectStatus = this.engineeringForm.projectStatus.toString();
      },
    },
  }
</script>

<style scoped lang="scss">
  .dialog-footer{
    text-align: right;
  }
  .wrap{
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
    .tit{
      border-left: 4px solid #1ab394;
      padding-left: 10px;
    }
  }
</style>
