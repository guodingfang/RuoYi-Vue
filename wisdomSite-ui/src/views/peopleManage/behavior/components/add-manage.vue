<template>
  <el-dialog
    :title="`物品`"
    :visible.sync="isShow"
    width="700px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
      <el-form
        ref="detailData"
        :model="detailData"
        :rules="rules"
        size="small"
        label-width="140px"
      >
        <el-form-item label="物品名称：" prop="itemName">
          <el-input
            v-model="detailData.itemName"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="计量单位：" prop="personId">
          <el-input
            v-model="detailData.unit"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="所需点券：" prop="personId">
          <el-input
            v-model="detailData.changePoint"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="库存量：" prop="personId">
          <el-input
            v-model="detailData.inventoryMount"
            placeholder=""
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="r_button" @click="handleClose">取消</el-button>
        <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
      </div>
  </el-dialog>
</template>
<script>
import { addManage,modifyManage } from "@/api/people/manage";
// import MulPicUpload from "@/components/MulPicUploads/index";
export default {
  name: 'detail',
  // components: {MulPicUpload},
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
  data () {
    return {
      rules: {
        itemName: [
          { required: true, message: "请输入物品名", trigger: "blur" }
        ],
      },
    }
  },
  watch: {
    isShow: {
      handler(flag) {
        if (flag) {
          // this.ruleForm = this.detailData
        }
      },
      immediate: true
    },
  },
  created() {

  },
  methods: {

    handleClose() {
      this.$emit("on-close");
    },

    _submit() {
      this.$refs["detailData"].validate(async valid => {
        if (!valid) return;
        // const {eventId,eventType,projectId,imageUrls,personId,point,details} = this.detailData
        // let parm = {
        //   projectId,personId,eventId,point,details,imageUrls,eventType
        // }
        let re
        if (this.state == 'modify') {
          re = await modifyManage(this.detailData)
        } else {
          re = await addManage({
            ...this.detailData,
          })
        }
        if (re.code == 200) {
          this.$message({
            message: re.msg,type: "success",
          });
          this.$emit("on-close",'success');
        }
      });
    },
  }
}
</script>
<style lang='stylus' scoped>

</style>
