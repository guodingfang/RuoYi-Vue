<template>
<div>
  <el-form class="wrap" ref="engineeringForm" :model="engineeringForm" :rules="rules" size="small" label-width="140px">
    <!-- 工程属性 -->
    <h4 class="tit">工程属性</h4>
    <el-form-item label="建筑用途：">
      <el-select placeholder="请选择建筑用途" v-model="engineeringForm.projectStatus"  class="w100">
        <el-option
          v-for="dict in buildPurpose"
          :key="dict.dictValue"
          :label="dict.dictLabel"
          :value="dict.dictValue"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="建筑结构类型：">
      <el-input v-model="engineeringForm.buildStructureType" placeholder=""></el-input>
    </el-form-item>
    <el-row>
      <el-col :span="12">
        <el-form-item label="单体数量(个)：">
          <el-input v-model="engineeringForm.numMonomers" placeholder=""></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="建筑面积(m²)：">
          <el-input v-model="engineeringForm.buildArea" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="地上面积(m²)：">
          <el-input v-model="engineeringForm.groundArea" placeholder=""></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="地下面积(m²)：">
          <el-input v-model="engineeringForm.undergroundArea" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="建筑高度(m)：">
          <el-input type="number" v-model="engineeringForm.buildHeight" placeholder=""></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="地上层数：">
          <el-input type="number" v-model="engineeringForm.groundLayernum" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="地下深度(m)：">
          <el-input type="number" v-model="engineeringForm.undergroundDepth" placeholder=""></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="基坑深度(m)：">
          <el-input type="number" v-model="engineeringForm.foundationpitDepth" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <!-- 关联单位 -->
    <h4 class="tit">关联单位</h4>
    <el-row>
      <el-col :span="12">
        <el-form-item label="建设单位：">
          <el-input v-model="engineeringForm.constructionUnit" placeholder=""></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="设计单位：：">
          <el-input v-model="engineeringForm.designer" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="监理单位：">
          <el-input v-model="engineeringForm.controlUnit" placeholder=""></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="施工(总包)单位：：">
          <el-input v-model="engineeringForm.ceneralcontractUnit" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="安全监督单位：">
          <el-input v-model="engineeringForm.safetySupervision" placeholder=""></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="质量监督单位：：">
          <el-input v-model="engineeringForm.qualitySupervision" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <!-- 施工信息 -->
    <el-form-item label="投资类型：">
      <el-select placeholder="请选择投资类型" v-model="engineeringForm.lnvestmentType"  class="w100">
        <el-option label="政府投资" :value="1"></el-option>
        <el-option label="国有企业投资" :value="2"></el-option>
        <el-option label="非国有企业投资" :value="3"></el-option>
        <el-option label="其它" :value="4"></el-option>
      </el-select>
    </el-form-item>
    <el-row>
      <el-col :span="12">
        <el-form-item label="中标编号：">
          <el-input v-model="engineeringForm.bidNo" placeholder=""></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="合同造价(万元)：">
          <el-input type="number" v-model="engineeringForm.contractCost" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <!-- 许可证上传start-->
    <el-form-item label="施工许可证：">
      <div class="xkz">
        <div class="xkz-t">
          <el-input v-model="engineeringForm.constructionPermit" placeholder=""></el-input>
        </div>
        <div class="xkz-b">
          <pic-upload
            :value="engineeringForm.constructionPermitUrl"
            @input="picChange"
          />
        </div>
      </div>
    </el-form-item>
    <!-- 许可证上传end-->
    <el-row>
      <el-col :span="12">
        <el-form-item label="开工时间：">
          <el-date-picker
            v-model="engineeringForm.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="开始日期">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="完工时间：">
          <el-date-picker
            v-model="engineeringForm.completeTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="项目经理：">
          <el-input v-model="engineeringForm.projectManagerName" placeholder=""></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="联系电话：">
          <el-input type="tel" v-model="engineeringForm.contactNumber" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="发薪银行：">
          <el-select placeholder="选择发薪银行" v-model="engineeringForm.payBank"  class="w100">
            <el-option
              v-for="dict in bankType"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="开户银行：">
          <el-input  type="text" v-model="engineeringForm.bankDeposit" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="发薪账号：">
          <el-input  type="text" v-model="engineeringForm.payAccountno" placeholder=""></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="发薪日期：" prop="payDate">
          <el-input v-model.number="engineeringForm.payDate" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="质量目标：">
          <el-input v-model="engineeringForm.qualityObjectives" placeholder=""></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="安全目标：">
          <el-input v-model="engineeringForm.safetyObjectives" placeholder=""></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-form-item label="合同备案号：">
      <el-input v-model="engineeringForm.contractRecordno" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="备注：">
      <el-input  type="textarea" rows="2" v-model="engineeringForm.remarks" placeholder=""></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button size="small" type="primary" @click="submitForm()">保 存</el-button>
  </div>
</div>
</template>

<script>
  import {getProjectById, addProject, updateProject, deleteProject, exportProject} from "@/api/project/project";
  import PicUpload from "@/components/PicUpload/index";
  export default {
    name: 'Engineer',
    components: {PicUpload},
    props: {
    },
    data() {
      return{
        engineeringForm: {},
        dialogImageUrl: '',
        dialogVisible: false,
        cities: [],
        bankType: [],
        buildPurpose: [],
        // 表单校验
        rules: {
          projectName: [
            { required: true, message: "项目名称", trigger: "blur" }
          ],
          payDate: [
            { type: 'number', min: 1, max: 31, message: "请输入正确的日期", trigger: "change" }
          ]
        }
      }
    },
    mounted() {
      this.getProjectById(this.$route.query.id);
      this.getDicts("build_purpose").then(response => {
        this.buildPurpose = response.data;
      });
      this.getDicts("ws_bank_type").then(response => {
        this.bankType = response.data;
      });
    },
    methods: {
      picChange(e) {
        this.engineeringForm.constructionPermitUrl = e;
      },
      /** 获取工程信息 */
      getProjectById(id) {
        getProjectById(id).then(res => {
          res.data.projectStatus = res.data.projectStatus.toString()
          this.engineeringForm = res.data;
        })
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["engineeringForm"].validate(valid => {
          if (valid) {
            updateProject(this.engineeringForm).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.$router.go(-1);
              }
            });
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
    watch: {
      engineeringForms(val) {
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
    max-width: 800px;
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
      font-weight: normal;
      font-size: 14px;
      color: #666666;
    }
  }
</style>
