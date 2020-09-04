<template>
  <el-dialog
    title="设置班组"
    :visible.sync="isShow"
    width="500px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <el-tabs v-model="activeName" class="warp_tab">
      <el-tab-pane label="已有班组" name="first">
        <el-button @click="addDomain" type="text" class="addbtn">新增班组</el-button>
        <el-form
          ref="formData"
          size="small"
          :model="formData"
          label-position="left"
          label-width="80px"
        >
          <el-form-item
            v-for="(domain, index) in formData.teams"
            label="班组名称"
            :key="index"
            :prop="'teams.' + index + '.teamName'"
            :rules="{
              required: true,
              message: '班组不能为空',
              trigger: 'blur'
            }"
          >
            <el-input v-model="domain.teamName" />
            <el-button type="text" @click.prevent="removeDomain(domain)"
              >删除</el-button
            >
          </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm()">保存</el-button>
            </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </el-dialog>
</template>
<script>
import { addTeam,delTeam } from "@/api/people/company";
export default {
  name: "team",
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
      formData: {},
      activeName: "first",
      add_obj: {}
    };
  },
  watch: {
    isShow: {
      handler(flag) {
        const { projectId, unitId } = this.detailData[0];
        this.formData = {
          teams: this.detailData,
          projectId: projectId,
          unitId: unitId
        };
        this.add_obj = { projectId, unitId, id:null }
        // console.log(this.add_obj);
      },
      immediate: true
    }
  },
  async created() {
    console.log(this.detailData);
  },
  methods: {
    handleClose() {
      this.$emit("on-close");
    },
    submitForm() {
      console.log(this.formData);
      this.$refs['formData'].validate((valid) => {
        if (valid) {
          addTeam(this.formData).then(res=>{
            if (res.code == 200) {
              this.$message({
                message: res.msg,type: "success",
              });
              this.handleClose()
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    async removeDomain(item) {
      console.log(item);
      var index = this.formData.teams.indexOf(item);
      if (index !== -1) {
        if (this.formData.teams && this.formData.teams.length > 1) {
          this.formData.teams.splice(index, 1);
        } else {
          this.$message({
            showClose: true,
            message: "最后一条不可删除"
          });
        }
      }
      // let re
      // if (item.id) {
      //   re = await delTeam({teamId:item.id})
      // } else {
      //   re = []
      // }
      // if (re.rows && re.rows.length > 0) {
      //   this.$message({
      //     showClose: true,
      //     message: "班组中存在人员，不可删除"
      //   });
      // } else {
      //   var index = this.formData.teams.indexOf(item);
      //   if (index !== -1) {
      //     if (this.formData.teams && this.formData.teams.length > 1) {
      //       this.formData.teams.splice(index, 1);
      //     } else {
      //       this.$message({
      //         showClose: true,
      //         message: "最后一条不可删除"
      //       });
      //     }
      //   }
      // }

    },
    addDomain() {
      this.formData.teams.push({
        ...this.add_obj,
        teamName: ""
      });
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep {
  .el-dialog__body{
    padding-top: 0;
  }
  .el-form{
    padding-top: 20px;
  }
  .el-form-item__content {
    display: flex;
    align-items: center;
    .el-button {
      margin-left: 10px;
    }
  }
  .addbtn{
    position: absolute;
    top: -38px;
    right: 0;
  }
}
</style>
