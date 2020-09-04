<template>
  <el-dialog
    title="设置人员信息"
    :visible.sync="isShow"
    width="500px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="姓名">
        <span style="padding-left: 16px;">{{detailData.name}}</span>
      </el-form-item>
      <el-form-item label="人员类型" prop="personType">
        <el-select v-model="ruleForm.personType" placeholder="人员类型">
          <el-option label="建筑工人" value="0"></el-option>
          <el-option label="管理人员" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="建设单位" prop="unitId">
        <el-select
          @change="getTeam"
          v-model="ruleForm.unitId"
          placeholder="建设单位"
        >
          <el-option
            v-for="item in unitList"
            :key="item.id"
            :label="item.unitName"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="班组/部门" prop="teamId">
        <el-select v-model="ruleForm.teamId" placeholder="班组/部门">
          <el-option
            v-for="item in teamsList"
            :key="item.id"
            :label="item.teamName"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="工种/岗位" prop="workType" class="buttom">
        <el-select v-model="ruleForm.workType" placeholder="工种/岗位">
          <el-option
            v-for="item in $DStore.state.getWorkType"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue"
          >
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getUnit,setAllData } from "@/api/people/company";
export default {
  name: "setStaff",
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
  data() {
    return {
      ruleForm: {
        name: ""
      },
      rules: {
        name: [{ required: true, message: " ", trigger: "blur" }],
        personType: [{ required: true, message: " ", trigger: "change" }],
        unitId: [{ required: true, message: " ", trigger: "change" }],
        teamId: [{ required: true, message: " ", trigger: "change" }],
        workType: [{ required: true, message: " ", trigger: "change" }]
      },
      unitList: [],
      teamsList: []
    };
  },
  created() {
    this.getUnit();
    console.log(this.$DStore.state);
    console.log(this.detailData);
  },
  methods: {
    handleClose() {
      this.$emit("on-close");
    },
    async getUnit() {
      const re = await getUnit({ projectId: this.detailData.projectId });
      this.unitList = re.rows;
    },
    getTeam() {
      if (this.unitList && this.unitList.length > 0) {
        let arr = this.unitList.filter(item => {
          return item.id == this.ruleForm.unitId;
        });
        this.teamsList = arr[0].teams;
      }
    },
    _submit() {
      this.$refs['ruleForm'].validate(async (valid) => {
        if (valid) {
          const data = {
            ...this.ruleForm,
            personIds:this.detailData.personIds
          }
          const re = await setAllData(data)
          if (re.code == 200) {
            this.$message({
              message: re.msg,type: "success",
            });
            this.$emit("on-close",'setStaff');
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
$--border-size: 1px solid #dddcdc;
::v-deep {
  .el-form {
    // padding: 20px;
    border: $--border-size;
  }
  .el-form-item__label {
    border-right: $--border-size;
    padding-right: 0;
    text-align: center;
    background: #f9fbfc;
  }
  .el-form-item__content {
    margin-left: 12px;
  }
  .el-form-item {
    margin: 0;
    border-bottom: $--border-size;
  }
  .buttom {
    border-bottom: none;
  }
  .el-select {
    width: 100%;
  }
  .el-input__inner {
    border-color: rgba(0, 0, 0, 0);
  }
  .is-error .el-input__inner {
    border-color: #ff4949 !important;
  }
}
</style>
