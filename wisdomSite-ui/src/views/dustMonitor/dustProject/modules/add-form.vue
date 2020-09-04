<template>
  <section>
    <el-dialog
      title="监测点"
      :visible.sync="isShow"
      width="500px"
      :before-close="handleClose"
      :close-on-click-modal="false"
      append-to-body
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="监测点名称" prop="monitoringpointName">
          <el-input
            v-model="ruleForm.monitoringpointName"
            placeholder="监测点名称"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item ref='equipmentno' label="监测设备" prop="equipmentnoName">
          <!-- <el-select clearable filterable v-model="ruleForm.equipmentnoId" placeholder="监测设备">
            <el-option
              v-for="item in equipmentno"
              :key="item.id"
              :label="item.serialNumber"
              :value="item.id"
            ></el-option>
          </el-select> -->
          <el-input class="btnInput" :disabled="!isCreate" v-model="ruleForm.equipmentnoName">
            <el-button type="text" slot="prefix" @click="selCams">选择</el-button>
          </el-input>
        </el-form-item>
        <!-- <el-form-item label="监测点视频">
          <el-select clearable filterable v-model="ruleForm.videoEquipmentId" placeholder="监测点视频">
            <el-option
              v-for="item in videoEquipment"
              :key="item.id"
              :label="item.equipmentSerialnumber"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="监测点视频">
          <el-input class="btnInput" :disabled="!isCreate" v-model="ruleForm.videoEquipmentName">
            <el-button type="text" slot="prefix" @click="selCam">选择</el-button>
          </el-input>
          <!-- <el-select clearable filterable v-model="ruleForm.videoEquipmentId" placeholder="监测点视频">
            <el-option
              v-for="item in videoEquipment"
              :key="item.id"
              :label="item.equipmentSerialnumber"
              :value="item.id"
            ></el-option>
          </el-select> -->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="r_button" @click="handleClose">取消</el-button>
        <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
      </div>
    </el-dialog>
    <sel-cam
      :listData="camList"
      :dialogFlag.sync="showSel"
      @change="getCam"
    />
    <sel-cams
      :listData="camLists"
      :dialogFlag.sync="showSels"
      @changes="getCams"
    />
  </section>
</template>
<script>
import {
  getChannelList,
} from "@/api/smartMonitor/video";
import { getDust,getVideo,addPoint,modifyPoint,hooksVideo } from "@/api/dustMonitor";
import SelCam from "./../components/SelCam";
import SelCams from "./../components/SelCams";

export default {
  name: '',
  components: {
    SelCam,
    SelCams,
  },
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
  watch: {
    isShow: {
      async handler(val){
        if(val) {
          if (this.state === 'add') {
            this.ruleForm = this.detailData
          } else {
            this.ruleForm = {
              ...this.detailData,
              videoEquipmentName: this.detailData.videoSerialNumber,
              equipmentnoName: this.detailData.serialNumber,
            }
          }
        }
      },
      immediate:true
    }
  },
  computed: {
    isCreate() {
      return this.state === 'add'? true: false
    }
  },
  data () {
    return {
      ruleForm: {},
      rules: {
        monitoringpointName: [{ required: true, message: "请输入设备名称", trigger: "blur" }],
        equipmentnoName: [{ required: true, message: "请选择监测设备" }],
      },
      equipmentno: [],
      videoEquipment: [],
      showSel:false,
      camList: [],
      showSels:false,
      camLists: [],
    }
  },
  methods: {
    /*选择设备*/
    async selCam() {
      // const { rows:camList } = await getChannelList(0);
      this.camList = this.videoEquipment
      this.showSel = true;
      // const { rows:camList } = this.videoEquipment
    },
    async selCams() {
      // const { rows:camList } = await getChannelList(0);
      this.camLists = this.equipmentno
      console.log(this.camLists);
      this.showSels = true;
      // const { rows:camList } = this.videoEquipment
    },
    getCams(e) {
      this.ruleForm.equipmentnoName = e.serialNumber
      this.ruleForm.equipmentnoId = e.id
      this.$nextTick(()=>{
        this.$refs.equipmentno.clearValidate();
      })
    },
    getCam(e) {
      this.ruleForm.videoEquipmentName = e.equipmentSerialnumber
      this.ruleForm.videoEquipmentId = e.id

      // this.selItem = e;
      // this.camForm = {
      //   ...this.camForm,
      //   videoSerialNumber: e.equipmentSerialnumber,
      //   videoChannel: e.channelNumber,
      //   projectId: e.projectId,
      //   videoMonitoryPointName: e.installationPosition
      // }
    },
    handleClose() {
      this.$emit("on-close");
    },
    _submit() {
      this.$refs["ruleForm"].validate(async valid => {
        if (valid) {
          let show = true
          if (this.ruleForm.videoEquipmentId) {
            const res = await hooksVideo({
              videoEquipmentId:this.ruleForm.videoEquipmentId
            })
            console.log(res);
            if (res.rows.length > 0) {
              this.$message({
                message: '视频已存在，请重新选择',type: "warning",
              });
              show = false
            } else  {
              show = true
            }
          }
          if (show) {
            let re
            if (this.state === 'add') {
              re = await addPoint({
                ...this.ruleForm,
              });
            } else {
              re = await modifyPoint(this.ruleForm);
            }
            if (re.code == 200) {
              this.$message({
                message: re.msg,type: "success",
              });
              this.$emit("on-close", 'setStaff');
            }
          }


        }
      });
    },
  },
  async created() {
    const re = await getDust()
    this.equipmentno = re.rows
    const res = await getVideo()
    this.videoEquipment = res.rows
  }
}
</script>
<style lang='scss' scoped>
::v-deep{
  .btnInput{
    .el-input__inner{
      padding-left: 40px;
    }
  }
}
</style>
