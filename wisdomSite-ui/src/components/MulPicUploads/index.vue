<template>
  <div class="mul-upload">
    <el-upload
      :action="action"
      name="avatarfile"
      :headers="headers"
      :limit="limit"
      list-type="picture-card"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
      :on-success="handleUploadSuccess"
      :file-list="imageList"
      :before-upload="beforeAvatarUpload">
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body>
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>
<script>
  import { getToken } from "@/utils/auth";

  export default {
    name: 'MulPicUpload',
    data () {
      return {
        action: process.env.VUE_APP_BASE_API + '/system/user/profile/avatar',
        dialogImageUrl: '',
        dialogVisible: false,
        resourcesUrl: 'http://39.103.140.108:8080',
        headers: { Authorization: getToken() },
    }
    },
    props: {
      value: {
        default: () => [],
        type: [String, Array]
      },
      limit: {
        default: 5,
        type: Number
      },
      readOnly: {
        default: false,
        type: Boolean
      },
    },
    computed: {
      imageList () {
        let res = []
        // console.log('我是value',this.value)
        if (!!this.value && this.value.length > 0) {
          let imageArray = this.value
          for (let i = 0; i < imageArray.length; i++) {
            res.push({
              url: `${this.value[i].url.indexOf('http://')===-1?this.resourcesUrl:''}${imageArray[i].url}`,
              response: {
                imgUrl: this.value[i].url
              }
            })
          }
        }
        this.$emit('input', this.value)
        return res
      }
    },
    methods: {
      // 图片上传
      handleUploadSuccess (response, file, fileList) {
        let pics = fileList.map(file => {
          return { url: file.response.imgUrl }
        })
        this.$emit('input', pics)
      },
      // 限制图片上传大小
      beforeAvatarUpload (file) {
        if (this.readOnly) {
          return false;
        }
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isLt2M
      },
      handleRemove (file, fileList) {
        console.log(fileList)
        let pics = fileList.map(file => {
          return { url: file.url }
        })
        this.$emit('input', pics)
      },
      handlePictureCardPreview (file) {
        this.dialogImageUrl = file.url
        this.dialogVisible = true
      }
    }
  }
</script>

<style scoped lang="scss">
  .mul-upload {
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
