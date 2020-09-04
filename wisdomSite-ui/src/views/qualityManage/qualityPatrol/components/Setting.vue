<template>
  <section>
    <div class="title">
      质量问题分类模板
      <el-button
        type="text"
        style="margin-right:17px"
        @click="showVisible(true)"
        >新增</el-button
      >
    </div>
    <rc-table
      uniq-flag="id"
      :operate="false"
      :selected-item="selectedItem"
      :table-loading="tableLoading"
      :table-data="tableData"
      :table-config="tableConfig"
      :col-config="colConfig"
      :pagination-config="paginationParam"
      :pagination="false"
    />
    <el-dialog title="分类名称" :visible.sync="visible" width="600px">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
      >
        <!-- <el-col :span="12"> -->
        <el-form-item label="分类名称" prop="problemTypeName">
          <el-input
            v-model="ruleForm.problemTypeName"
            placeholder="请输入分类名称"
          />
        </el-form-item>
        <!-- </el-col> -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showVisible(false)">取 消</el-button>
        <el-button type="primary" @click="_submit">确 定</el-button>
      </span>
    </el-dialog>
  </section>
</template>

<script>
import pagiMixin, { wrapReq } from "@ruclouds/cloud-share/mixins/pag";
import dataFn from "./../lib/door";
import { get } from "@rucloud/cloud-share/dist/utils";
import {
  getCheckType,
  addProblemType,
  updateProblemType,
  deleteProblemType,
  handleClickToForbiddenProblemType,
  changeOt
} from "@/api/quality/checkManager";

export default {
  name: "setting",
  mixins: [pagiMixin],
  data() {
    return {
      ...dataFn.call(this, true),
      visible: false,
      ruleForm: {
        problemTypeName: ""
      },
      rules: {
        problemTypeName: [
          { required: true, message: "请输入分类名称", trigger: "blur" }
        ]
      }
    };
  },
  watch: {
    visible(val) {
      if (!val) {
        this.$refs["ruleForm"].resetFields();
      }
    }
  },
  async created() {
    this.getData();
  },
  methods: {
    showVisible(flag, rsf) {
      if (rsf) {
        this.ruleForm.problemTypeName = rsf.problemTypeName;
        this.ruleForm.id = rsf.id;
      }
      this.visible = flag;
    },
    _submit() {
      this.$refs["ruleForm"].validate(valid => {
        if (!valid) return;
        addProblemType(this.ruleForm)
          .then(response => {
            if (response.code == 200) {
              this.$message({
                message: response.msg,
                type: "success",
                duration: 1000
              });
              this.showVisible(false);
              this.getData();
            }
          })
          .catch(e => {
            console.log(e);
          });
      });
    },
    // 删除
    async deleteFn(id) {
      let show = true
      const re = await changeOt(id)
      if (re.rows.length > 0) {
        this.$message({
          message: '模板已引用，无法删除，请重新选择',type: "warning",
        });
        show = false
      } else  {
        show = true
      }
      if (show) {
        const ids = id;
        return new Promise(res => {
          this.$confirm("是否确认删除?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(async () => {
              deleteProblemType(ids).then(rs => {
                this.$message({
                  message: rs.msg,
                  type: "success",
                  duration: 1000
                });
                this.getData();
              });
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消删除"
              });
            });
        });
      }

    },
    async typeFn(item) {
      const re = await handleClickToForbiddenProblemType({
        id: item.id,
        isDelete: item.isDelete === "N" ? "Y" : "N"
      });
      if (re.code == 200) {
        this.$message({
          message: re.msg,
          type: "success",
          duration: 1000
        });
        this.getData();
      }
    },
    // 获取数据
    async getData() {
      const re = await getCheckType();
      const { total, curPage } = re; // 如果后端有做curPage字段容错,可以加上
      this.tableData = get(re, "rows", []);
      // this.setPage(curPage, parseInt(total), null);
    }
  }
};
</script>

<style lang="scss" scoped>
.er-tableown {
  width: 650px;
  ::v-deep {
    .el-table {
      border-radius: 0 0 6px 6px;
    }
    .el-table__header {
      .cell {
        font-size: 14px;
      }
    }
  }
}
::v-deep {
  .el-dialog__body {
    padding-bottom: 0;
  }
}
.title {
  width: 650px;
  height: 50px;
  line-height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid;
  background-color: #fafafb;
  border: 1px solid #e9e9e9;
  border-bottom: none;
  margin-top: 30px;
  padding-left: 10px;
}
</style>
