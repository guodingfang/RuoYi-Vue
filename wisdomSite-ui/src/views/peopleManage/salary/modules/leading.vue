<template>
  <el-dialog
    title="批量导入"
    :visible.sync="isShow"
    width="500px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <p><b>操作步骤：</b></p>
    <p>1、 下载《人员批量导入模板》</p>
    <p>2、 打开下载表，将对应字段信息输入或粘贴进本表。为保障粘贴信息被有效导入，请使用纯文本或数字</p>
    <p>3、 信息输入完毕，点击 “浏览” 按钮， 选择excel文档</p>
    <p>4、 点击 “开始导入”</p>
    <!-- {{process.env.VUE_APP_BASE_API}} -->
      <el-link style="margin-bottom: 17px;" type="primary" @click="importTemplate">立即下载</el-link>
    <el-upload
      action="#"
      :limit="1"
      ref="upload"
      :before-upload="beforeAtt"
      :file-list="fileList"
      :auto-upload="false"
      :on-exceed="handleExceed"
      :on-progress='handleProgress'
      :http-request="uploadSectionFile"
      >
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">	附件支持格式(excel)，不超过1M</div>
    </el-upload>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" class="r_button" @click="submitUpload">保存</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { uploadAvatar,importTemplate } from "@/api/people/salary";
export default {
  name: "leading",
  props: {
    isShow: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: [Object, Array],
      default: () => {}
    },
    state: {
      type: String,
      default: () => null
    }
  },
  data() {
    return {
      fileList: []
    };
  },
  watch: {
    isShow: {
      handler(flag) {
        // this.formData = {
        //   detailData: this.detailData
        // };
      },
      immediate: true
    }
  },
  created() {
    console.log(this.detailData);
  },
  methods: {
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then(response => {
        this.download(response.msg);
      });
    },
    handleClose() {
      this.$emit("on-close");
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 2 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeAtt(file) {
      var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      let arr = ['xls','xlsx']
      if (arr.indexOf(testmsg) === -1) {
        alert('上传文件格式有误')
        return false
      }
      const isLt2M = file.size / 1024 / 1024 < 1
      if (!isLt2M) {
 		    alert('上传文件大小不能超过 1MB')
        return false
      }
      // const timeStamp = new Date() - 0;
      // const copyFile = new File([file], `${timeStamp}_${file.name}`);
      // this.uploadFile(copyFile,'dic');
    },
    uploadSectionFile(param) {
      let fileObj = param.file;
      // FormData 对象
      let formData = new FormData();
      // 文件对象
      formData.append("file", fileObj);
      let self = this
      uploadAvatar(self.detailData.projectId,formData).then(response => {
        if (response.code === 200) {
          let imgSrc = process.env.VUE_APP_BASE_API + response.imgUrl;
          this.$emit("on-close",'setStaff');
          this.$message({
            message: response.msg,type: "success",
          });
        }
      });
    },
    handleProgress(file) {

    },
  }
};
</script>
<style lang="scss" scoped>

</style>
