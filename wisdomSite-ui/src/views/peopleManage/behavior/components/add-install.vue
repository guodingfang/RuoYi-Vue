<template>
  <el-dialog
    :title="`${title}事件`"
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
        <el-form-item label="事件类型：" prop="eventType">
          <el-radio-group v-model="detailData.eventType" class="w100">
            <el-radio :label="1">正向</el-radio>
            <el-radio :label="0">负向</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="事件名称：" prop="eventName">
          <el-input
            v-model="detailData.eventName"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item :label="detailData.eventType == 1 ? '增加点卷：' : '扣除分数：'" prop="point">
          <el-input
            v-model="detailData.point"
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
 import { addInstall,modifyInstall  } from "@/api/people/b-install";
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
        eventType: [
          { required: true, message: "请选择事件类型", trigger: "blur" }
        ],
        eventName: [
          { required: true, message: "请输入事件名称", trigger: "blur" }
        ],
        point: [
          { required: true, message: "请输入点卷数", trigger: "blur" }
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
  computed:{
    title() {
      return this.state == 'modify' ? '修改' : '新增'
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
          re = await modifyInstall(this.detailData)
        } else {
          re = await addInstall({
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
