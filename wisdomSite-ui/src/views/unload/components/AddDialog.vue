<template>
  <el-dialog  :title="isCreate?'新增卸料':'编辑卸料'" :visible.sync="flag" width="880px" @close="closeDialog" :close-on-click-modal="false">
      <el-form
      ref="camForm"
      :model="camForm"
      :rules="camRules"
      size="medium"
      label-width="160px"
    >
      <el-form-item label="设备名称" prop="equipmentName" class="c_l cl_tw">
        <el-input
          v-model="camForm.equipmentName"
        ></el-input>
      </el-form-item>
      <el-form-item label="监控设备" class="c_l cl_tw">
        <el-input :disabled="false" :value="`${jkSel.installationPosition||''}${!!jkSel.serialNumber?'|':''}${jkSel.serialNumber||''}`">
          <el-button type="text" slot="prefix" @click="selDevice('jk')" >选择</el-button>
        </el-input>
      </el-form-item>
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
    getJkList,
    addUnload,
    editUnload,
  } from "@/api/unload/index";
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
        selType: 'jk',
        showSel:false,
        jkSel: {},
        selList: [],
        areaList: [],
        camForm: {
          equipmentName: '',
          fieldlayoutId:'',
          coordinateX: '',
          coordinateY: '',
          fieldlayoutAddress: '',
        },
        camRules: {
          equipmentName: [
            {required: true, message: '请输入设备名称',  trigger: 'blur' },
          ],
          // propertyRecordno: [
          //   { required: true, message: '请输入产权备案号', trigger: 'blur' },
          // ]
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
            await addUnload(this.camForm);
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
            this.isCreate? await addUnload(this.camForm) : await editUnload(this.camForm) ;
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
            result = await getJkList();
            break;
        }

        this.selList = result.rows;
        this.showSel = true;

      },
      getSel(e) {
        console.log(e)
        switch (this.selType) {
          case 'jk':
            this.jkSel = e;
            this.camForm.deviceNumber = e.serialNumber;
            break;
        }
      },
      /* 关闭 */
      closeDialog() {
        this.camForm = {};
        this.jkSel = {};
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
        },
      },
    },
    watch: {
      camForms(val) {
        console.log(val)
        this.camForm = Object.assign({}, val);
        this.jkSel = { serialNumber: val.deviceNumber };
      },
    },
  };
</script>

<style lang='scss' scoped>


</style>
