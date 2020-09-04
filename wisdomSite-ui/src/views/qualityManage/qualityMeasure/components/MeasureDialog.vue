<template>
  <el-dialog  title="实测实量" :visible.sync="flag" width="800px" @close="closeDialog" @open="openDialog" :close-on-click-modal="false">
    <el-form
      ref="dataForm"
      :model="dataForm"
      :rules="dataRules"
      size="medium"
      label-width="180px"
    >
      <el-form-item label="分项工程名称" prop="measuredrealquantitySetupId" >
        <el-select v-model="dataForm.measuredrealquantitySetupId"  class="w100">
          <el-option
            v-for="dict in subProjList"
            :key="dict.id"
            :label="dict.subitemProject"
            :value="dict.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        v-for="(item, index) in dataForm.wsEnterpriseMeasuredrealquantityContentList"
        :label="item.contentField"
        :key="item.contentField"
        :prop="'wsEnterpriseMeasuredrealquantityContentList.' + index + '.contentFieldValue'"
        :rules="{
          required: true, message: '不能为空', trigger: 'blur'
        }"
      >
        <el-input v-model="item.contentFieldValue"></el-input>
      </el-form-item>

      <el-form-item label="参与人：" prop="participant">
        <el-input v-model="dataForm.participant"></el-input>
      </el-form-item>
      <el-form-item label="备注：" prop="comment">
        <el-input v-model.number="dataForm.comment"></el-input>
      </el-form-item>
      <el-form-item label="现场照片：" prop="sceneImageAddres">
        <mul-pic-upload v-if="flag" @input="picChange"/>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="isCreate === false?handleUpdate():handleAdd()">保存</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import {
    getSubProjDetail,
    getSubProjList,
    addMeasure,
  } from "@/api/quality/measure";
  import MulPicUpload from "@/components/MulPicUpload/index";

  export default {
    name: 'MeasureDialog',
    components: {
      MulPicUpload
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
    },
    data() {
      return {
        areaId: '',
        pics: [],
        dataForm: {
          measuredrealquantityMeasuredLocationId: '',
          wsEnterpriseMeasuredrealquantityContentList: []
        },
        treeListProps: {
          label: 'name',
          value: 'id',
        },
        subProjList: [],
        dataRules: {
          measuredrealquantitySetupId: [
            { required: true, message: '请填写区域名称', trigger: 'blur' },
          ],
          participant: [
            { required: true, message: '请填写参与人', trigger: 'blur' },
          ],
        },
      };
    },
    created() {

    },
    methods: {
      picChange(e) {
        this.pics = e;
      },
      async getSubProjList() {
       const { rows } = await getSubProjList();
       this.subProjList = rows;
      },
      async getSubProjDetail() {
        const { data } = await getSubProjDetail(this.dataForm.measuredrealquantitySetupId);
        let measureList = [];
        data.wsEnterpriseMeasuredrealquantitySetupContent.map( e => {
          measureList.push({
            "measuredrealquantityMeasuredLocationId": this.areaId, //实测部位id
            "measuredrealquantityId": '', //实测实量id
            "contentField": e.measuredContent,
            contentFieldCompany: e.company,
            "contentFieldValue": '',
            "maxNum": e.maxNum,
            "minNum": e.minNum
          })
        });
        this.$set(this.dataForm, 'wsEnterpriseMeasuredrealquantityContentList', measureList)
      },
      /* 添加 */
      handleAdd() {
        this.$refs.dataForm.validate(async (valid) => {
          if (!valid) return;
          try {
            let reqData = {
              wsEnterpriseMeasuredrealquantity: {
                "measuredrealquantityMeasuredLocationId": this.areaId,
                "measuredrealquantitySetupId": this.dataForm.measuredrealquantitySetupId,//分项工程id
                "subitemProject":this.dataForm.participant,
                "participant": this.dataForm.participant,
                "comment": this.dataForm.comment,
                "sceneImageAddres": this.pics,
              },
              wsEnterpriseMeasuredrealquantityContentList: this.dataForm.wsEnterpriseMeasuredrealquantityContentList
            }
            await addMeasure(reqData);
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
      openDialog() {
        this.getSubProjList();
      },
      /* 关闭 */
      closeDialog() {
        this.dataForm = {};
        this.pics = [];
        this.flag = false;
        this.$refs.dataForm.resetFields();
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
    },
    watch: {
      dataForms(val) {
        console.log(val)
        // this.dataForm = Object.assign({ orderNum: 0 }, val);
        this.areaId = val.id;
        // this.dataForm.officeId = Number(this.dataForm.officeId);
      },
      'dataForm.measuredrealquantitySetupId': function (val) {
        if (!!val) {
          this.getSubProjDetail()
        }
      }
    },
  };
</script>

<style lang='scss' scoped>
.form-des{
  color: #cccccc;
  padding-left: 10px;
}
</style>
