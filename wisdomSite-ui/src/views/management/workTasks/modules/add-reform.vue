<template>
  <el-dialog
    title="验证"
    :visible.sync="isShow"
    width="500px"
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
        <el-form-item label="验证结果：" prop="verifyResult">
          <el-radio-group v-model="detailData.verifyResult" class="w100">
            <el-radio :label="1">合格</el-radio>
            <el-radio :label="2">不合格</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="验证结果描述：" prop="verifyResultDescription">
          <el-input
            type="textarea"
            :rows="2"
            v-model="detailData.verifyResultDescription"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="上传图片：" ref='upload'>
          <!-- <mul-pic-upload :value="item.repairImageAddres" :readOnly=true></mul-pic-upload> -->
          <mul-pic-upload
            :value="detailData.verifyImageAddres"
            :limit="5"
            @input="picChange"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="r_button" @click="handleClose">取消</el-button>
        <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
      </div>
  </el-dialog>
</template>
<script>
import {
  addReform,
  getOtable,
} from "@/api/Inspection/record";
import StatusTag from "@/components/StatusTag/index";
import MulPicUpload from "@/components/MulPicUpload/index";
import cloneDeep from "lodash/cloneDeep";
import unionBy from "lodash/unionBy";
import isArray from "lodash/isArray";
export default {
  name: 'detail',
  components: {MulPicUpload, StatusTag},
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
        verifyResult: [
          { required: true, message: "请选择验证结果", trigger: "blur" }
        ],
        verifyResultDescription: [
          { required: true, message: "请输入验证结果描述", trigger: "blur" }
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
    picChange(e) {
      this.detailData.verifyImageAddres = e
      console.log(e);
    },
    _submit() {
      this.$refs["detailData"].validate(valid => {
        if (!valid) return;
        this.loading = true
        addReform({
          ...this.detailData,
          inspectionId: this.detailData.id
        })
          .then(res => {
            this.msgSuccess(res.msg);
            this.$emit("on-close", true);
          })
          .catch(e => {
            console.log(e);
          });
          this.loading = false
      });
    },

  }
}
</script>
<style lang='stylus' scoped>

</style>
