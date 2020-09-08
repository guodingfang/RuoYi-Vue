<template>
  <el-dialog  title="测量部位详情" destroy-on-close :visible.sync="flag" width="1000px" @close="closeDialog" :close-on-click-modal="false">
    <el-form
      ref="dataForm"
      :model="dataForm"
      :rules="dataRules"
      size="small"
      label-width="140px"
    >
      <p class="split-tit" >实测部位信息</p>
      <el-row>
        <el-col :span="12">
          <el-form-item label="项目名称：">
            <span>{{dataForm.wsEnterpriseMeasuredrealquantityMeasuredLocation.projectName||'暂无'}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="实测部位：">
            <span>{{dataForm.wsEnterpriseMeasuredrealquantityMeasuredLocation.measuredLocation||'暂无'}}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="备注" prop="respectiveRegionId" >
        <span>{{dataForm.wsEnterpriseMeasuredrealquantityMeasuredLocation.comment||'暂无'}}</span>
      </el-form-item>
      <el-form-item label="平面图标点：" prop="measuredLocation">
        <el-image :src="picBase+dataForm.wsEnterpriseMeasuredrealquantityMeasuredLocation.planImageAddres" @click="openMarker" style="width: 50%;cursor: pointer"></el-image>
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-form-item label="创建时间：">
            <span>{{dataForm.wsEnterpriseMeasuredrealquantityMeasuredLocation.createdTime||'暂无'}}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="创建人：">
            <span>{{dataForm.wsEnterpriseMeasuredrealquantityMeasuredLocation.createdName||'暂无'}}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <template v-for="(item, i) in dataForm.wsEnterpriseMeasuredrealquantityVOs">
        <p class="split-tit" >
          分项工程名称：{{item.wsEnterpriseMeasuredrealquantity.subitemProject}}
          <span class="operate-btn">
            <el-button v-show="item.isEdit" type="primary" size="mini" @click="doSave(item, i)">保存</el-button>
            <el-button v-show="!item.isEdit" type="primary" @click="doEdit(item, i)" size="mini">编辑</el-button>
            <el-button v-show="item.isEdit"  size="mini"  @click="doCancel(item, i)" class="cancel">取消</el-button>
            <el-button  size="mini" @click="delSubProj(item.wsEnterpriseMeasuredrealquantity.id)">删除</el-button>
          </span>
        </p>
        <div class="d-list">
          <div class="d-item" v-for="(col, inx) in item.wsEnterpriseMeasuredrealquantityContentList">
            <div class="d-item-head">{{col.contentField}}</div>
            <div class="d-item-cont" v-show="!item.isEdit">{{col.contentFieldValue}}{{col.contentFieldCompany}}</div>
            <div class="d-item-cont" v-show="item.isEdit">
              <el-input v-model="col.contentFieldValue" style="width: 90px"></el-input>
              <div class="danwei">{{col.contentFieldCompany}}</div>
            </div>
          </div>
        </div>
        <el-row>
          <el-col :span="8">
            <el-form-item label="实测时间：">
              <span>{{item.wsEnterpriseMeasuredrealquantity.createdTime||'暂无'}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="实测人：">
              <span>{{item.wsEnterpriseMeasuredrealquantity.createdName||'暂无'}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="参与人：">
              <span>{{item.wsEnterpriseMeasuredrealquantity.participant||'暂无'}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="respectiveRegionId" >
          <span>{{item.wsEnterpriseMeasuredrealquantity.comment||'暂无'}}</span>
        </el-form-item>
      </template>
    </el-form>
    <marker-dialog
      :isCreate="false"
      :dialogFlag.sync="markerMialogFlag"
      :selectTableData.sync="selectTableData"
      :dataForms.sync="mapData"
      :readOnly="true"
    />
  </el-dialog>
</template>

<script>
  import {
    addLocation,
    editLocation,
    delMeasure,
    editMeasure
  } from "@/api/quality/measure";
  import ProjectSelect from "@/components/CommonSelect/ProjectSelect";
  import MarkerDialog from "./MarkerDialog";
  export default {
    name: 'DetailDialog',
    components: {
      ProjectSelect,
      MarkerDialog
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
      isMove: {
        type: Boolean,
        default: false,
      },
      dataForms: {
        type: Object,
        default: () => {},
      },
      selectTableData: {
        type: Array,
        default: () => [],
      }
    },
    data() {
      return {
        markerMialogFlag: false,
        picBase: process.env.VUE_APP_BASE_PIC,
        dataForm: {

        },
        treeListProps: {
          label: 'name',
          value: 'id',
        },
        deviceType: [],
        dataRules: {
/*          respectiveRegionId: [
            { required: true, message: '请填写区域名称', trigger: 'blur' },
          ],
          measuredLocation: [
            { required: true, message: '请填写实测部位', trigger: 'blur' },
          ],*/
        },
      };
    },
    created() {
    },
    methods: {
      doEdit(item, i) {
        this.$set(this.dataForm.wsEnterpriseMeasuredrealquantityVOs, i, {...item, isEdit: true} )
      },
      doCancel(item, i) {
        this.$set(this.dataForm.wsEnterpriseMeasuredrealquantityVOs, i, {...item, isEdit: false} );
        this.$emit('refresh', this.dataForm.wsEnterpriseMeasuredrealquantityMeasuredLocation.id)
      },
      async doSave(item, i) {
        await editMeasure(this.dataForm.wsEnterpriseMeasuredrealquantityVOs[i]);
        this.$emit('refresh', this.dataForm.wsEnterpriseMeasuredrealquantityMeasuredLocation.id)
      },
      /* 添加 */
      handleAdd() {
        this.$refs.dataForm.validate(async (valid) => {
          if (!valid) return;
          try {
            console.log(this.dataForm)
            await addLocation(this.dataForm);
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
            await editLocation(this.dataForm);
            this.msgSuccess('编辑成功');
            this.flag = false;
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /* 删除 */
      async delSubProj(id) {
        await delMeasure(id)
        this.$emit('refresh', this.dataForm.wsEnterpriseMeasuredrealquantityMeasuredLocation.id)
      },
      /* 关闭 */
      closeDialog() {
        // this.dataForm = {};
        this.$refs.dataForm.resetFields();
      },
      openMarker() {
        this.markerMialogFlag = true
      },
    },
    computed: {
      flag: {
        get() {
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
          // this.$emit('update:dataForms', {});
          this.isMove ? this.$emit('update:isMove', false) : ''
        },
      },
      mapData() {
        return{
          ...this.dataForm.wsEnterpriseMeasuredrealquantityMeasuredLocation,
          mapUrl: this.picBase + this.dataForm.wsEnterpriseMeasuredrealquantityMeasuredLocation.planImageAddres
        }
      },
    },
    watch: {
      dataForms(val) {
        if(val.wsEnterpriseMeasuredrealquantityVOs && val.wsEnterpriseMeasuredrealquantityVOs.length>0){
          val.wsEnterpriseMeasuredrealquantityVOs.map(e => {
            e.isEdit = false
          })
        }
        this.dataForm = val;
      },
    },
  };
</script>

<style lang='scss' scoped>
  /deep/.el-button.cancel{
    border-color: #DCDFE6;
    color: #333333;
  }
.form-des{
  color: #cccccc;
  padding-left: 10px;
  }
  .d-list{
    display: flex;
    border: 1px solid #f2f2f2;
    min-height: 70px;
    .danwei{
      font-size: 12px;
      color: #999999;
      margin-top: 5px;
    }
    .d-item{
      min-width: 110px;
      border-right: 1px solid #f2f2f2;
      .d-item-head{
        background: #00b588;
        color: #f2f2f2;
        padding: 10px 0;
        text-align: center;
      }
      .d-item-cont{
        padding: 10px 0;
        text-align: center;
      }
    }
  }
</style>
