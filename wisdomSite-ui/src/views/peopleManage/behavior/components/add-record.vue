<template>
  <el-dialog
    :title="`新增${detailData.title}行为`"
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
        <el-form-item label="选择人员：" prop="personData">
          <el-select
            placeholder="请选择人员"
            v-model="detailData.personData"
            @change="personChange"
            style="width: 100%"
            value-key='id'
          >
            <el-option
              v-for="item in personList"
              :label="item.name"
              :value="item"
              :key="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班组名称：" prop="personId">
          <el-input
            v-model="detailData.teamName"
            disabled
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="劳务公司：" prop="personId">
          <el-input
            v-model="detailData.unitName"
            disabled
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="事件" prop="eventId">
          <el-select
            placeholder="请选择事件"
            v-model="detailData.eventData"
            @change="eventChange"
            style="width: 100%"
            value-key='id'
          >
            <el-option
              v-for="item in eventList"
              :label="item.eventName"
              :value="item"
              :key="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="detailData.eventType == 1 ? '增加点券：': '扣除分数'" prop="point">
          <el-input
            v-model="detailData.point"
            placeholder=""
          ></el-input>
        </el-form-item>
        <el-form-item label="详细说明：">
          <el-input
            type="textarea"
            :rows="2"
            v-model="detailData.details"
            placeholder=""
          ></el-input>
        </el-form-item>

        <el-form-item label="上传图片：">
          <mul-pic-upload
            :value="detailData.imageUrls"
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
  addInspec,
} from "@/api/Inspection/record";
import { teamPerson,eventName,getRecord } from "@/api/people/record";
import MulPicUpload from "@/components/MulPicUploads/index";
export default {
  name: 'detail',
  components: {MulPicUpload},
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
        personData: [
          { required: true, message: "请选择人员", trigger: "change" }
        ],
        eventId: [
          { required: true, message: "请选择事件", trigger: "change" }
        ],
        point: [
          { required: true, message: "请输入点卷", trigger: "blur" }
        ],
      },
      personList: [],
      eventList: [],
    }
  },
  watch: {
    isShow: {
      handler(flag) {
        if (flag) {
          // this.ruleForm = this.detailData
          this.getSelect()
        }
      },
      immediate: true
    },
  },
  created() {

  },
  methods: {
    async getSelect() {
      const re = await teamPerson({projectId: this.detailData.projectId})
      this.personList = re.rows
      const res = await eventName({projectId: this.detailData.projectId,eventType:this.detailData.eventType})
      this.eventList = res.rows
    },
    personChange() {
      const { id, teamName, unitName } = this.detailData.personData
      this.detailData.personId = id
      this.detailData.teamName = teamName
      this.detailData.unitName = unitName
    },
    eventChange() {
      console.log(this.detailData.personData);
      const { id, point } = this.detailData.eventData
      this.detailData.eventId = id
      this.detailData.point = point
    },
    handleClose() {
      this.$emit("on-close");
    },
    picChange(e) {
      this.detailData.imageUrls = e
    },
    _submit() {
      const {eventId,eventType,projectId,imageUrls,personId,point,details} = this.detailData
      let parm = {
        projectId,personId,eventId,point,details,imageUrls,eventType
      }
      this.$refs["detailData"].validate(valid => {
        if (!valid) return;
        const {eventId,eventType,projectId,imageUrls,personId,point,details} = this.detailData
        let parm = {
          projectId,personId,eventId,point,details,imageUrls,eventType
        }
        this.loading = true
        getRecord(parm)
          .then(res => {
            this.msgSuccess(res.msg);
            this.$emit("on-close", 'success');
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
