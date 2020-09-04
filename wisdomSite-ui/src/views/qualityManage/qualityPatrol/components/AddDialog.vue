<template>
  <el-dialog
    title="新增整改任务"
    :visible.sync="flag"
    width="70%"
    @close="closeDialog"
    :close-on-click-modal="false"
  >
    <div class="add-wrap">
      <el-form
        ref="detailData"
        :model="detailData"
        :rules="rules"
        size="small"
        label-width="140px"
      >
        <el-form-item label="检查部位：" prop="checkPosition">
          <el-input
            v-model="detailData.checkPosition"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="问题等级：" prop="qualityProblemLevel">
          <el-radio-group v-model="detailData.qualityProblemLevel" class="w100">
            <el-radio :label="1">一般</el-radio>
            <el-radio :label="2">严重</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="问题分类：" prop="qualityProblemId">
          <el-select
            placeholder
            v-model="detailData.qualityProblemId"
            class="w100"
          >
            <el-option
              v-for="item in checkManagerTypeList"
              :label="item.problemTypeName"
              :value="item.id"
              :key="item.problemTypeName"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题描述：">
          <el-input
            type="textarea"
            :rows="2"
            v-model="detailData.comment"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="整改要求：">
          <el-input
            type="textarea"
            :rows="2"
            v-model="detailData.rectificationRequirements"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="整改人：" prop="repairPersonId">
              <el-select
                placeholder="请选择整改人"
                v-model="detailData.repairPersonId"
                class="w100"
              >
                <el-option
                  v-for="item in userList"
                  :label="item.nickName"
                  :value="item.userId"
                  :key="item.userId"
                >
                  <span style="float: left">{{ item.nickName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.phonenumber }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="整改时限：" prop="repairLimitDate">
              <el-date-picker
                v-model="detailData.repairLimitDate"
                type="date"
                placeholder=""
                :picker-options="pickerOptions"
                value-format="yyyy-MM-dd"
                style="width: 100%"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="验证人：" prop="verifyPersonId">
          <el-select
            placeholder="请选择验证人"
            v-model="detailData.verifyPersonId"
            class="w100"
          >
            <el-option
              v-for="item in userList"
              :label="item.nickName"
              :value="item.userId"
              :key="item.userId"
                >
                <span style="float: left">{{ item.nickName }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.phonenumber }}</span>
              </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上传图片：" ref='upload' prop="checkPositionAddres">
          <div class="xkz">
            <div class="xkz-b">
              <mul-pic-upload @input="picChange"/>

            </div>
          </div>
        </el-form-item>
      </el-form>

    </div>
    <div slot="footer" class="dialog-footer">
      <el-button
        size="small"
        type="primary"
        @click="isCreate == false ? updateProject() : addProject(true)"
        :loading="loading"
        >保存</el-button
      >
      <el-button
        size="small"
        type="primary"
        @click="isCreate == false ? updateProject() : addProject(false)"
        :loading="loading"
        >保存并继续新增</el-button
      >
    </div>
    <el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body>
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </el-dialog>
</template>

<script>
import {
  addCheckManager,
  getCheckType,
  getCheckUser
} from "@/api/quality/checkManager";
import MulPicUpload from '@/components/MulPicUpload/index';
import { uploadAvatar } from "@/api/system/user";
export default {
  name: "AddDialog",
  props: {
    dialogFlag: {
      required: true,
      type: Boolean,
      default: false
    },
    detailDatas: {
      type: Object,
      default: () => {}
    },
    isCreate: {
      type: Boolean,
      default: false
    }
  },
  components: {
    MulPicUpload
  },
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          // let curDate = (new Date()).getTime()
          // return time.getTime() < Date.now() - 8.64e7
          return time.getTime() + 24 * 3600 * 1000 < Date.now();
        },
      },
      detailData: {
        checkPositionAddres: [],
        qualityProblemLevel: 2,
        repairPersonId: this.$store.state.user.id,
        verifyPersonId: this.$store.state.user.id,
        repairLimitDate: this.$dayjs(new Date()).format('YYYY-MM-DD'),
      },
      dialogImageUrl: "",
      dialogVisible: false,
      cities: [
        { value: 1, label: "北京" },
        { value: "Shanghai", label: "上海" },
        { value: "Nanjing", label: "南京" },
        { value: "Chengdu", label: "成都" },
        { value: "Shenzhen", label: "深圳" },
        { value: "Guangzhou", label: "广州" }
      ],
      // 表单校验
      rules: {
        checkPosition: [
          { required: true, message: "请输入检查部位", trigger: "blur" }
        ],
        qualityProblemLevel: [
          { required: true, message: "请选择问题等级", trigger: "change" }
        ],
        qualityProblemId: [
          { required: true, message: "请选择问题分类", trigger: "change" }
        ],
        checkPositionAddres: [
          { required: true, message: "请上传照片", trigger: "change" }
        ],
        repairPersonId: [
          { required: true, message: "请选择整改人", trigger: "change" }
        ],
        repairLimitDate: [
          { required: true, message: "请选择整改时限", trigger: "change" }
        ],
        verifyPersonId: [
          { required: true, message: "请选择验证人", trigger: "change" }
        ]
      },
      checkManagerTypeList: [],
      userList: [],
      loading: false
    };
  },
  created() {
    // console.log(this.$store.state.user.enterpriseId);
    this.getType();
  },
  methods: {
    //查询下拉
    getType() {
      getCheckType({
        isDelete:"N"
      }).then(res => {
        this.checkManagerTypeList = res.rows;
      });
      getCheckUser({
        enterpriseId: this.$store.state.user.enterpriseId
      }).then(response => {
        this.userList = response.rows;
      });
      // getCheckType().then(response => {
      //   this.checkManagerTypeLists = response.rows;
      // });
    },
    /** 添加项目*/
    addProject(show) {
      this.$refs["detailData"].validate(valid => {
        if (!valid) return;
        this.loading = true
        addCheckManager(this.detailData)
          .then(res => {
            this.msgSuccess("添加成功");
            if (show) {
              this.flag = false;
            } else {
              this.detailData = {
                checkPositionAddres: [],
                qualityProblemLevel: 2,
                repairPersonId: this.$store.state.user.id,
                verifyPersonId: this.$store.state.user.id,
                repairLimitDate: this.$dayjs(new Date()).format('YYYY-MM-DD'),
              };
            }
            this.$emit("query");
          })
          .catch(e => {
            console.log(e);
          });
          this.loading = false
      });
    },
    /** 编辑项目*/
    updateProject() {
      this.$refs["detailData"].validate(valid => {
        if (!valid) return;
        updateProject(this.detailData)
          .then(res => {
            this.msgSuccess("编辑成功");
            this.flag = false;
            this.$emit("query");
          })
          .catch(e => {
            console.log(e);
          });
      });
    },
    /* 关闭项目框 */
    closeDialog() {
      this.detailData = {};
      this.$refs["detailData"].resetFields();
      this.detailData.checkPositionAddres = [];
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["detailData"].validate(valid => {
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
    handleAvatarSuccess(file, fileList) {
      console.log(123);
      this.$refs.detailData.validateField('checkPositionAddres')
    },
    handleRemove(file, fileList) {
      this.detailData.checkPositionAddres = fileList;
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 上传预处理
    beforeUpload(file) {
      this.dialogVisibleimg = true;
      if (file.type.indexOf("image/") == -1) {
        this.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        const timeStamp = new Date() - 0;
        const copyFile = new File([file], `${timeStamp}_${file.name}`);
        this.uploadFile(copyFile);
        return false;
      }
    },
    // 上传图片
    uploadFile(file) {
      let formData = new FormData();
      formData.append("avatarfile", file);
      uploadAvatar(formData).then(response => {
        if (response.code === 200) {
          let imgSrc = response.imgUrl;
          this.detailData.checkPositionAddres.push({
            url: imgSrc,
            uid: new Date().getTime()
          });
          this.$refs.upload.clearValidate();
          // this.dialogImageUrl = imgSrc;
          // this.msgSuccess("修改成功");
        }
      });
    },
    picChange(pic) {
      this.detailData.checkPositionAddres = pic;
    }
  },
  computed: {
    flag: {
      get() {
        return this.dialogFlag;
      },
      set(val) {
        this.$emit("update:dialogFlag", val);
      }
    }
  },
  watch: {
    dialogFlag: {
      immediate:true,
      handler(val) {
        if (val) {
          this.detailData = Object.assign({
            checkPositionAddres: [],
            qualityProblemLevel: 2,
            repairPersonId: this.$store.state.user.id,
            verifyPersonId: this.$store.state.user.id,
            repairLimitDate: this.$dayjs(new Date()).format('YYYY-MM-DD'),
          }, this.detailDatas);
          this.detailData.id = Number(this.detailData.id);
        }
      }
    },
  }
};
</script>

<style scoped lang="scss">
.dialog-footer {
  text-align: right;
}
.add-wrap {
  /deep/.el-upload-list--picture-card {
    .el-upload-list__item {
      width: 100px;
      height: 100px;
      line-height: 102px;
      border-radius: 0;
    }
  }
  /deep/.el-upload--picture-card {
    width: 100px;
    height: 100px;
    line-height: 102px;
    border-radius: 0;
  }
  .tit {
    border-left: 4px solid #1ab394;
    padding-left: 10px;
  }
}
</style>
