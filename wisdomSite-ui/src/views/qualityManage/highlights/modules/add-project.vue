<template>
  <el-dialog
    title="新增项目部亮点"
    :visible.sync="visible"
    width="70%"
    :before-close="handleClose"
  >
    <div class="add-wrap">
      <el-form
        ref="formData"
        :model="formData"
        :rules="rules"
        size="small"
        label-width="140px"
      >
        <el-form-item label="亮点部位：" prop="brightspotPosition">
          <el-input
            v-model="formData.brightspotPosition"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="亮点描述：" prop="brightspotDescribe">
          <el-input
            type="textarea"
            :rows="2"
            v-model="formData.brightspotDescribe"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="亮点图片：" prop="brightspotImageAddres">
          <div class="xkz">
            <div class="xkz-b">
<!--              <el-upload-->
<!--                action=""-->
<!--                list-type="picture-card"-->
<!--                :file-list="formData.brightspotImageAddres"-->
<!--                :on-preview="handlePictureCardPreview"-->
<!--                :on-remove="handleRemove"-->
<!--                :limit="3"-->
<!--                :before-upload="beforeUpload"-->
<!--              >-->
<!--                <i class="el-icon-plus"></i>-->
<!--              </el-upload>-->
              <mul-pic-upload @input="picChange" :value="formData.brightspotImageAddres"/>
            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button
        size="small"
        type="primary"
        @click="submitForm()"
        >保存</el-button
      >
    </div>
    <el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body>
      <img
        width="100%"
        v-for="(item, index) in formData.brightspotImageAddres"
        :key="index"
        :src="picBase+item"
        alt=""
      />
    </el-dialog>
  </el-dialog>
</template>
<script>
import { uploadAvatar } from "@/api/system/user";
import { addProjectDept } from "@/api/quality/highlights";
import MulPicUpload from "../../../../components/MulPicUpload/index";
export default {
  name: "addForm",
  components: {MulPicUpload},
  data() {
    return {
      picBase: process.env.VUE_APP_BASE_PIC,
      formData: {
        brightspotImageAddres: []
      },
      // 表单校验
      rules: {
        brightspotPosition: [
          { required: true, message: "请输入亮点部位", trigger: "blur" }
        ],
        brightspotDescribe: [
          { required: true, message: "请输入亮点描述", trigger: "blur" }
        ],
        brightspotImageAddres: [
          { required: true, message: "请上传照片", trigger: "blur" }
        ]
      },
      dialogImageUrl: "",
      dialogVisible: false
    };
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    picChange(e) {
      this.formData.brightspotImageAddres = e;
    },
    handleClose() {
      this.$emit("on-close");
      this.$refs["formData"].resetFields();
      this.formData.brightspotImageAddres = [];
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["formData"].validate(valid => {
        if (valid) {
          addProjectDept(this.formData).then(res => {
            if (res.code === 200) {
              this.msgSuccess("新增成功");
              this.$emit("on-close", 'susess');
              // this.$refs["formData"].resetFields();
            }
          });
        }
      });
    },
    handleRemove(file, fileList) {
      this.formData.brightspotImageAddres = fileList;
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
          this.formData.brightspotImageAddres.push({
            url: imgSrc,
            uid: new Date().getTime()
          });
          // this.dialogImageUrl = imgSrc;
          // this.msgSuccess("修改成功");
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
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
