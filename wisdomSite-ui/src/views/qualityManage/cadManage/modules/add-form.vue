<template>
  <el-dialog
    title="区域平面图"
    :visible.sync="visible"
    width="70%"
    :before-close="handleClose"
  >
    <div class="add-wrap">
      <el-form
        ref="detailData"
        :model="detailData"
        :rules="rules"
        size="small"
        label-width="140px"
      >
        <el-form-item label="区域名称：" prop="regionName">
          <el-input v-model="detailData.regionName" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="所属区域：" prop="parentId">
          <el-select
            v-model="detailData.parentId"
            placeholder="请选择所属区域"
            clearable
            size="small"
            :disabled="detailData.parentId == 0"
          >
            <el-option
              v-for="item in parentList"
              :key="item.id"
              :label="item.regionName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="平面图：" prop="brightspotImageAddres">
          <div class="xkz">
            <div class="xkz-b">
              <el-upload
                action=""
                list-type="picture-card"
                :show-file-list="false"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :limit="1"
                :before-upload="beforeUpload"
              >
                <img
                  v-if="detailData.planImageAddres"
                  :src="picBase+detailData.planImageAddres"
                  class="avatar"
                />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </div>
          </div>
        </el-form-item>
      </el-form>
      <!-- <el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body>
        <img
          width="100%"
          :src="imageUrl"
          alt=""
        />
      </el-dialog> -->
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button size="small" type="primary" @click="submitForm()" :loading="loading"
        >保存</el-button
      >
    </div>
  </el-dialog>
</template>
<script>
import { uploadAvatar } from "@/api/system/user";
/* selectList */
import { addFn, modify, parentList } from "@/api/quality/cadManage";
export default {
  name: "addForm",
  data() {
    return {
      picBase: process.env.VUE_APP_BASE_PIC,
      // 表单校验
      rules: {
        regionName: [
          { required: true, message: "请输入区域名称", trigger: "blur" }
        ],
        parentId: [
          { required: true, message: "请输入所属区域", trigger: "blur" }
        ],
        dialogVisible: false
        // brightspotImageAddres: [
        //   { required: true, message: "请上传照片", trigger: "blur" }
        // ]
      },
      parentList: [],
      loading: false
    };
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: Object,
      default: () => {}
    },
    state: {
      type: String,
      default: () => null
    }
  },
  watch: {
    visible:{
      async handler(val) {
        if (val) {
          const re = await parentList(this.detailData.projectId)
          re.data.unshift({id: 0,regionName: '顶级' })
          this.parentList = re.data.map((item)=>{
            return { id: item.id, regionName: item.regionName }
          })
          console.log(this.parentList);
        }
      },
    }
  },
  async created() {
    // this.getSelect()
  },
  methods: {
    async getSelect() {
      // const re = await selectList()
      // console.log(re);
    },
    handleClose() {
      this.$refs["detailData"].resetFields();
      this.$emit("on-close");
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["detailData"].validate(async valid => {
        if (valid) {
          if (this.state === "add") {
            this.loading = true
            setTimeout(()=>{
                this.loading=false   //点击一次时隔两秒后才能再次点击
            },2000)
            const re = await addFn(this.detailData)
            // addFn(this.detailData).then(res => {
            //   if (res.code === 200) {
            //     this.msgSuccess("新增成功");
            //     // this.$emit("on-close", true);
            //   }
            // });
            if (re.code === 200) {
              this.msgSuccess("新增成功");
              this.$emit("on-close", true);
            }
          } else {
            modify(this.detailData).then(res => {
              if (res.code === 200) {
                this.msgSuccess("修改成功");
                this.$emit("on-close", true);
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
      this.detailData.planImageAddres = file.url;
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
          this.detailData.planImageAddres = imgSrc;
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
.avatar {
  width: 100%;
  height: 100%;
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
