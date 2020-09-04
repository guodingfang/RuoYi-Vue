<template>
  <el-dialog  :title="isCreate?'新增升降机':'编辑升降机'" :visible.sync="flag" width="980px" @close="closeDialog" :close-on-click-modal="false">
    <p class="split-tit">基本信息</p>
      <el-form
      ref="dataForm"
      :model="dataForm"
      :rules="dataRules"
      size="medium"
      label-width="160px"
    >
      <div class="t_form">
        <el-row>
        <div class="top_form">
          <el-col :span="12">
            <el-form-item label="自编号" prop="selfNumber">
              <el-input v-model="dataForm.selfNumber"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产权备案号" prop="recordNumber" class="c_l cl_tw">
              <el-input
                v-model="dataForm.recordNumber"
              ></el-input>
            </el-form-item>
          </el-col>
        </div>
      </el-row>
      <el-row>
        <div class="top_form">
          <el-col :span="12">
            <el-form-item label="设备名称" prop="deviceName">
              <el-input v-model="dataForm.deviceName"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进场日期" class="c_l cl_tw">
              <el-date-picker
                v-model="dataForm.inDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </div>
      </el-row>
      <el-row>
        <div class="top_form">
          <el-col :span="12">
            <el-form-item label="设备类型" prop="equipmentName">
              <el-radio-group v-model="dataForm.deviceType" @change="resetSel">
                <el-radio label="1">单笼</el-radio>
                <el-radio label="2">双笼</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="监控设备类型" class="c_l cl_tw">
              <el-select v-model="dataForm.equipmentType" style="width: 100%;"  @change="resetSel">
                <el-option value="2" label="监控设备"></el-option>
                <el-option value="14" label="人脸识别机"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </div>
      </el-row>
      <el-row>
        <div class="top_form" v-show="dataForm.deviceType==='1'">
          <el-col :span="24">
            <el-form-item label="监控设备" prop="equipmentName">
              <el-input :disabled="false"  :value="singleSel.serialNumber">
                <el-button type="text" slot="prefix" @click="selDevice(0)" >选择</el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </div>
      </el-row>
      <el-row>
        <div class="top_form" v-show="dataForm.deviceType==='2'">
          <el-col :span="12">
            <el-form-item label="监控设备（左笼）" prop="equipmentName">
              <el-input :disabled="false"  :value="leftSel.serialNumber">
                <el-button type="text" slot="prefix" @click="selDevice(1)" >选择</el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="监控设备（右笼）" class="c_l cl_tw">
              <el-input :disabled="false" :value="rightSel.serialNumber">
                <el-button type="text" slot="prefix" @click="selDevice(2)" >选择</el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </div>
      </el-row>
      </div>
      <p class="split-tit">设备档案</p>
      <div class="t_form">
        <el-row>
          <div class="top_form">
            <el-col :span="12">
              <el-form-item label="产权单位">
                <el-input v-model="dataForm.propertyUnit"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="安装单位" class="c_l cl_tw">
                <el-input v-model="dataForm.installationUnit"
                ></el-input>
              </el-form-item>
            </el-col>
          </div>
        </el-row>
        <el-row>
          <div class="top_form">
            <el-col :span="12">
              <el-form-item label="生产单位" prop="produceUnit">
                <el-input v-model="dataForm.produceUnit"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="规格型号" prop="models" class="c_l cl_tw">
                <el-input v-model="dataForm.models"
                ></el-input>
              </el-form-item>
            </el-col>
          </div>
        </el-row>
        <el-row>
          <div class="top_form">
            <el-col :span="12">
              <el-form-item label="出厂编号" prop="factoryNumber">
                <el-input v-model="dataForm.factoryNumber"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="出厂日期" prop="productDate" class="c_l cl_tw">
                <el-date-picker
                  v-model="dataForm.productDate"
                  type="date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </div>
        </el-row>
      </div>
<!--      <p class="split-tit">场布位置</p>-->
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
    addElevator,
    getSelList,
    editElevator,
    towerReentry
  } from "@/api/smartMonitor/elevator";
  import _ from 'lodash';
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
      dataForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return {
        projectId: this.$store.state.user.curRole.id,
        selTit: '',
        cageMark: '',
        selType: '',
        showSel:false,
        singleSel: {},
        leftSel: {},
        rightSel: {},
        selList: [],
        areaList: [],
        dataForm: {
          deviceType: '1',
          equipmentType: '2',
        },
        dataRules: {
          selfNumber: [
            {required: true, message: '请输入自编号',  trigger: 'blur' },
          ],
          recordNumber: [
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
        this.$refs.dataForm.validate(async (valid) => {
          if (!valid) return;
          try {
            await addElevator(this.reqData);
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
        this.$refs.dataForm.validate(async (valid) => {
          if (!valid) return;
          try {
            let updateData = this.reqData;
                updateData.wsElevatorManager.id = this.dataForm.id; //TODO 有点懵，应该有更好的写法
            this.isReentry? await towerReentry(this.reqData) : await editElevator(updateData) ;
            this.msgSuccess('编辑成功');
            this.flag = false;
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /*选择设备*/
      async selDevice(cageMark) {
        let result = {};
        const type = this.dataForm.equipmentType;
        switch (type) {
          case '2':
            this.selTit = '监控设备';
            this.selType = 'jk';
            break;
          case '14':
            this.selTit = '司机人脸识别';
            this.selType = 'sj';
            break;
        }
        result = await getSelList(this.projectId,type);
        this.cageMark = cageMark;
        this.selList = result.rows;
        this.showSel = true;

      },
      getSel(e) {
        if(!e) {
          return false;
        }
        const selData = {
          cageMark: this.cageMark,
          serialNumber: e.serialNumber,
          projectId: e.projectId,
          equipmentType: e.equipmentType,
        };
        switch (this.cageMark) {
          case 0:
            this.singleSel = selData;
            break;
          case 1:
            this.leftSel = selData;
            break;
          case 2:
            this.rightSel = selData;
            break;
        }
      },
      resetSel() {
        this.singleSel = {};
        this.leftSel = {};
        this.rightSel = {};
      },
      /* 关闭 */
      closeDialog() {
        this.dataForm = {
          deviceType: '1',
          equipmentType: '2'
        };
        this.resetSel();
        this.$refs.dataForm.resetFields();
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
          this.$emit('update:dataForms', {});
          // this.isMove ? this.$emit('update:isMove', false) : ''
        },
      },
      reqData() {
        const {
          selfNumber,
          recordNumber,
          deviceName,
          inDate,
          deviceType,
          propertyUnit,
          installationUnit,
          produceUnit,
          models,
          factoryNumber,
          productDate,
        } = this.dataForm;
        let devices = [];
        if (deviceType === '1') {
          devices = _.isEmpty(this.singleSel) ? null : [this.singleSel];
        } else {
          devices = [_.isEmpty(this.leftSel) ? null : this.leftSel, _.isEmpty(this.rightSel) ? null : this.rightSel];
        }
        return {
          projectId: this.projectId,
          wsElevatorManager: {
            projectId: this.projectId,
            selfNumber,
            recordNumber,
            deviceName,
            inDate,
            deviceType,
            propertyUnit,
            installationUnit,
            produceUnit,
            models,
            factoryNumber,
            productDate,
          },
          devices,
          wsProjectFieldLayout: {
            projectId: this.projectId,
            fieldlayoutAddress: 'ceshidizhi'
          }
        }
      }
    },
    watch: {
      dataForms(val) {
        if (!!val.monitorDevices && val.monitorDevices.length > 0 ) {
          val.equipmentType = val.monitorDevices[0].equipmentType;
          val.monitorDevices.forEach(ele => {
            switch (ele.cageMark) {
              case '0':
                this.singleSel = ele;
                break;
              case '1':
                this.leftSel = ele;
                break;
              case '2':
                this.rightSel = ele;
                break;
            }
          })
        }
        // this.dataForm = this.isCreate ? Object.assign({ deviceType: '1', equipmentType:'2'}, val) : Object.assign({}, val);
        this.dataForm = {deviceType: '1', equipmentType:'2', ...val};
/*        this.jkSel = { serialNumber: '' };
        this.dgSel = { serialNumber: '' };
        this.sjSel = { serialNumber: '' };*/
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
