<template>
  <el-dialog
    title="项目部亮点详情"
    :visible.sync="visible"
    width="70%"
    :before-close="handleClose"
  >
    <div class="add-wrap">
      <el-form
        ref="formData"
        :model="formData"
        size="small"
        label-width="140px"
      >
        <el-form-item label="项目名称：">
          <span class="detail-val">{{
            $lGet(formData, "projectName", "")
          }}</span>
        </el-form-item>
        <el-form-item label="亮点部位：">
          <span class="detail-val">{{
            $lGet(formData, "brightspotPosition", "")
          }}</span>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="上传人：">
              <span class="detail-val">{{ formData.uploadPersonName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上传时间：">
              <span class="detail-val">{{ formData.uploadDate }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="亮点描述：">
          <span class="detail-val">{{
            $lGet(formData, "brightspotDescribe", "")
          }}</span>
        </el-form-item>
        <el-form-item label="亮点图片：">
          <!-- formData.brightspotImageAddres -->
          <ul>
            <li>
              <img
                width="90"
                height="90"
                src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591899810736&di=0d25557d98bd7d7970193707b290ad0a&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1531221012%2C95085541%26fm%3D214%26gp%3D0.jpg"
                alt=""
              />
            </li>
          </ul>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>
<script>
import { uploadAvatar } from "@/api/system/user";
import { addProjectDept } from "@/api/quality/highlights";
export default {
  name: "addForm",
  data() {
    return {};
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    formData: {
      type: Object,
      default: () => {}
    }
  },
  methods: {
    handleClose() {
      this.$emit("on-close");
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
