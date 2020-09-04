<template>
  <section>
    <el-upload
      action="https://jsonplaceholder.typicode.com/posts/"
      list-type="picture-card"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove">
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible" size="tiny" append-to-body>
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </section>
</template>
<script>
import { get } from '@rucloud/cloud-share/dist/utils';

export default {
  name: 'ImgLists',
  props: {
    imageUrl: {
      type: String,
      default: () => undefined,
    },
  },
  data() {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
    }
  },
  watch: {
    imageUrl: {
      handler(val) {
        val ? this.images = val : this.images = undefined
      },
      // immediate: true
    }
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    }
  }
}
</script>
<style lang='scss' scoped>

</style>
