<template>
  <el-dialog  :title="isCreate?'新增塔吊':'编辑塔吊'" :visible.sync="flag" width="980px" @close="closeDialog" :close-on-click-modal="false">
    <p class="split-tit">实时监控</p>
      <el-form
      ref="camForm"
      :model="camForm"
      :rules="camRules"
      size="medium"
      label-width="160px"
    >
      <div class="t_form">
        <el-row>
        <div class="top_form">
          <el-col :span="12">
            <el-form-item label="自编号" prop="selfNumbering">
              <el-input v-model="camForm.selfNumbering"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产权备案号" prop="propertyRecordno" class="c_l cl_tw">
              <el-input
                v-model="camForm.propertyRecordno"
              ></el-input>
            </el-form-item>
          </el-col>
        </div>
      </el-row>
      <el-row>
        <div class="top_form">
          <el-col :span="12">
            <el-form-item label="设备名称" prop="equipmentName">
              <el-input v-model="camForm.equipmentName"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进场日期" class="c_l cl_tw">
              <el-date-picker
                v-model="camForm.enterData"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </div>
      </el-row>
      <el-form-item label="监控设备" prop="monitoringEquipment">
        <el-input :disabled="false" :value="`${jkSel.installationPosition||''}${!!jkSel.serialNumber?'|':''}${jkSel.serialNumber||''}`">
          <el-button type="text" slot="prefix" @click="selDevice('jk')">选择</el-button>
        </el-input>
      </el-form-item>
      <el-row>
        <div class="top_form">
          <el-col :span="12">
            <el-form-item label="吊钩视频" prop="equipmentName">
              <el-input :disabled="false" :value="`${dgSel.installationPosition||''}${!!dgSel.serialNumber?'|':''}${dgSel.serialNumber||''}`">
                <el-button type="text" slot="prefix" @click="selDevice('dg')" >选择</el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="司机人脸识别" class="c_l cl_tw">
              <el-input :disabled="false" :value="`${sjSel.installationPosition||''}${!!sjSel.serialNumber?'|':''}${sjSel.serialNumber||''}`">
                <el-button type="text" slot="prefix" @click="selDevice('sj')" >选择</el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </div>
      </el-row>
      </div>
      <p class="split-tit">实时监控</p>
      <div class="t_form">
        <el-row>
          <div class="top_form">
            <el-col :span="12">
              <el-form-item label="产权单位">
                <el-input v-model="camForm.propertyUnit"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="安装单位" class="c_l cl_tw">
                <el-input v-model="camForm.installUnit"
                ></el-input>
              </el-form-item>
            </el-col>
          </div>
        </el-row>
        <el-row>
          <div class="top_form">
            <el-col :span="12">
              <el-form-item label="生产单位" prop="productionUnit">
                <el-input v-model="camForm.productionUnit"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="规格型号" prop="specificationsModel" class="c_l cl_tw">
                <el-input v-model="camForm.specificationsModel"
                ></el-input>
              </el-form-item>
            </el-col>
          </div>
        </el-row>
      </div>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="isCreate === false?handleUpdate():handleAdd()">保存</el-button>
    </span>
    <sel-device
      :title="selTit"
      :type="selType"
      :listData="selList"
      :dialogFlag.sync="showSel"
      @change="getSel"
    />
  </el-dialog>
</template>

<script>
  import {
    moniDeviceList,
    hookVideoList,
    towerFaceList,
    addTower,
    editTower,
    towerReentry
  } from "@/api/smartMonitor/tower";
  import SelDevice from "./SelDevice";
  export default {
    name: 'AddDialog',
    components: {
      SelDevice
      // SelCam
    },
    props: {
      dialogFlag: {
        required: true,
        type: Boolean,
        default: false,
      },
      isCreate: {
        type: Boolean,
        default: true,
      },
      isReentry: {
        type: Boolean,
        default: false,
      },
      camForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return {
        selTit: '',
        selType: '',
        showSel:false,
        jkSel: {},
        dgSel: {},
        sjSel: {},
        selList: [],
        areaList: [],
        camForm: {},
        camRules: {
          selfNumbering: [
            {required: true, message: '请输入自编号',  trigger: 'blur' },
          ],
          propertyRecordno: [
            { required: true, message: '请输入产权备案号', trigger: 'blur' },
          ],
          zoneId: [
            { required: true, message: '请选择区域', trigger: 'blur' },
          ],
          installationPosition: [
            { required: true, message: '请填写安装位置', trigger: 'blur' },
          ],
        },
      };
    },
    created() {
    },
    methods: {
      /* 添加 */
      handleAdd() {
        this.$refs.camForm.validate(async (valid) => {
          if (!valid) return;
          try {
            await addTower(this.camForm);
            this.msgSuccess('添加成功');
            this.flag = false;
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /* 编辑 */
      handleUpdate() {
        this.$refs.camForm.validate(async (valid) => {
          if (!valid) return;
          try {
            // const {id, projectId, videoMonitoryPointName, videoSerialNumber, videoChannel, zoneId } = this.camForm;
            this.isReentry? await towerReentry(this.camForm) : await editTower(this.camForm) ;
            this.msgSuccess('编辑成功');
            this.flag = false;
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /*选择设备*/
      async selDevice(type) {
        let result = {};
        switch (type) {
          case 'jk':
            this.selTit = '监控设备';
            this.selType = 'jk';
            result = await moniDeviceList();
            break;
          case 'dg':
            this.selTit = '吊钩视频';
            this.selType = 'dg';
            result = await hookVideoList();
            break;
          case 'sj':
            this.selTit = '司机人脸识别';
            this.selType = 'sj';
            result = await towerFaceList();
            break;
        }

        this.selList = result.rows;
        this.showSel = true;

      },
      getSel(e) {
        switch (this.selType) {
          case 'jk':
            this.jkSel = e;
            this.camForm.monitoringEquipment = e.serialNumber;
            break;
          case 'dg':
            this.dgSel = e;
            this.camForm.cliverVideoId = e.id;
            break;
          case 'sj':
            this.sjSel = e;
            this.camForm.faceId = e.id;
            break;
        }
      },
      /* 关闭 */
      closeDialog() {
        this.camForm = {};
        this.jkSel = {};
        this.dgSel = {};
        this.sjSel = {};
        this.$refs.camForm.resetFields();
      },
    },
    computed: {
      flag: {
        get() {
          if(this.dialogFlag) {

          }
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
          this.$emit('update:camForms', {});
          this.isMove ? this.$emit('update:isMove', false) : ''
        },
      },
    },
    watch: {
      camForms(val) {
        this.camForm = Object.assign({}, val);
        this.jkSel = { serialNumber: val.monitoringEquipment };
        this.dgSel = { serialNumber: val.videoEquipmentNo };
        this.sjSel = { serialNumber: val.faceNumber };
      },
    },
  };
</script>

<style lang='scss' scoped>
  $--border-size: 1px solid #dddcdc;
  /deep/{
    // .form-warp {
    //   border-bottom: $--border-size;
    // }
    // .top_form{
    //  .el-form-item {
    //     border-top: none !important;
    //   }
    // }
    .c_l {
      .el-form-item__label {
        border-left: $--border-size;
      }
    }
    .t_form{
      .el-input--prefix {
        .el-input__inner{
          padding-left: 40px
        }
      }
    }
    .cl_tw {
      .el-select {
        width: 120px;
      }
    }
    .c_tre {
      .el-select {
        width: 80px;
      }
    }
    .f_stort {
      font-size: 14px;
      padding: 8px;
      color: #606266;
      border-bottom: $--border-size;
      .f_text {
        display: inline-block;
        width: 140px;
        text-align: center;
      }
      .b_text {
        float: right;
        padding: 0;
      }
    }

    .el-form-item {
      margin: 0;
      border-bottom: $--border-size;
    }
    .el-form-item__label {
      border-right: $--border-size;
      padding-right: 0;
      text-align: center;
      background: #f9fbfc;
    }
    .el-form-item__content {
      padding-left: 12px;
    }
    .el-input__inner {
      border: none;
    }
  }
  .t_form {
    // margin-top: 20px;
    border: $--border-size;
    border-radius: 4px;
    border-bottom: 0;
  }

</style>
