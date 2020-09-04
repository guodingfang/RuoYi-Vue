<template>
  <el-dialog  title="组织" :visible.sync="flag" width="800px" @close="closeDialog" :close-on-click-modal="false">
    <el-form
      ref="organForm"
      :model="organForm"
      :rules="organRules"
      size="medium"
      label-width="180px"
    >
      <el-form-item label="组织名称" prop="enterpriseName" >
        <el-input  v-model="organForm.enterpriseName"></el-input>
      </el-form-item>
      <el-form-item label="简称：" prop="abbreviation">
        <el-input  v-model="organForm.abbreviation"></el-input>
      </el-form-item>
      <el-form-item label="上级组织：" prop="parentName">
        <el-input :disabled="true" v-model="organForm.parentName"></el-input>
      </el-form-item>
      <el-form-item label="序号：" prop="orderNum">
        <el-input style="width: 200px" type="number"  v-model.number="organForm.orderNum"></el-input> <span class="form-des">数字越大排序越靠前</span>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="isCreate === false?handleUpdate():handleAdd()">保存</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import {
    addOrganize,
    updateOrg
  } from '@/api/system/organize';
  import ProjectSelect from "@/components/CommonSelect/ProjectSelect";
  export default {
    name: 'OrganDialog',
    components: {
      ProjectSelect
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
      organForms: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return {
        organForm: {
          equipmentType: '',
        },
        treeListProps: {
          label: 'name',
          value: 'id',
        },
        deviceType: [],
        organRules: {
          enterpriseName: [
            { required: true, message: '请填写组织名称', trigger: 'blur' },
          ],
          parentId: [
            { required: true, message: '无上级组织', trigger: 'blur' },
          ],
        },
      };
    },
    created() {

    },
    methods: {
      /* 添加 */
      handleAdd() {
        this.$refs.organForm.validate(async (valid) => {
          if (!valid) return;
          try {
            console.log(this.organForm)
            await addOrganize(this.organForm);
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
        this.$refs.organForm.validate(async (valid) => {
          if (!valid) return;
          try {
            await updateOrg(this.organForm);
            this.msgSuccess('编辑成功');
            this.flag = false;
            this.$emit('query');
          } catch (e) {
            console.log(e);
          }
        });
      },
      /* 关闭 */
      closeDialog() {
        this.organForm = {};
        this.$refs.organForm.resetFields();
      },
    },
    computed: {
      flag: {
        get() {
          return this.dialogFlag;
        },
        set(val) {
          this.$emit('update:dialogFlag', val);
          // this.$emit('update:organForms', {});
          this.isMove ? this.$emit('update:isMove', false) : ''
        },
      },
    },
    watch: {
      organForms(val) {
        this.organForm = Object.assign({ orderNum: 0 }, val);
        // this.organForm.officeId = Number(this.organForm.officeId);
      },
    },
  };
</script>

<style lang='scss' scoped>
.form-des{
  color: #cccccc;
  padding-left: 10px;
}
</style>
