<template>
  <section>
    <div class="table-title">
      <el-button type="text" @click="addModel">新增</el-button>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span
              class="jl-green mr10 pointer"
              @click="modifyModel(scope.row.id)"
              >编辑</span
            >
            <span
              class="jl-green mr10 pointer"
              @click="deleteFn(scope.row.id)"
              >删除</span
            >
          </template>
        </el-table-column>
      </template>
    </Table>
    <el-dialog
      width="500px"
      title="新增作业类型"
      :visible.sync="innerVisible"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item
          label="类型名称"
          prop="operationName"
          :rules="[
            { required: true, message: '类型名称不能为空'},
          ]"
        >
          <el-input type="operationName" v-model.number="ruleForm.operationName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="r_button" @click="innerVisible = false">取消</el-button>
        <el-button type="primary" class="r_button" :loading="btnLoading" @click="_submit">保存</el-button>
      </div>
    </el-dialog>
  </section>
</template>
<script>
import Table from "@/components/Table";
import { getOtable, addJob, modifyJob, detailJob, deleteJob } from "@/api/Inspection/record";
export default {
  name: "jobtype",
  components: { Table },
  data() {
    return {
      tableData: [],
      tableConfig: [
        { slot: "operate" },
        { prop: "operationName", label: "作业类型", width: "" }
      ],
      innerVisible: false,
      ruleForm: {},
      state: null,
      btnLoading: false
    };
  },
  created() {
    this.getOtable();
  },
  watch: {
    innerVisible(val) {
      if (!val) {
        this.btnLoading = false
      }
    },
  },
  methods: {
    async getOtable() {
      const re = await getOtable();
      this.tableData = re.rows;
    },
    showModel(flag) {
      this.innerVisible = flag
    },
    addModel() {
      this.ruleForm = {},
      this.state = 'add'
      this.showModel(true)
    },
    async modifyModel(id) {
      const re = await detailJob(id)
      this.ruleForm = re.data
      this.state = 'modify'
      this.showModel(true)
    },
    _submit() {
      this.$refs["ruleForm"].validate(async valid => {
        if (!valid) return;
          this.btnLoading = true
          let re
          if (this.state === 'add') {
            re = await addJob(this.ruleForm);
          } else {
            re = await modifyJob(this.ruleForm);
          }
          if (re.code == 200) {
            this.$message({
              message: re.msg,type: "success",
            });
            this.showModel(false)
            this.getOtable()
          }
          this.btnLoading = false
      })
    },
    deleteFn(ids){
      return new Promise(res => {
        this.$confirm("是否确认删除?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(async () => {
            const re = await deleteJob(ids);
            if (re.code === 200) {
              this.$message({
                message: re.msg,
                type: "success",
              });
              this.getOtable();
            }
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除"
            });
          });
      });
    },
  }
};
</script>
<style lang="scss" scoped>
::v-deep {
  .el-table {
    width: 500px;
    border-radius: 0 0 6px 6px;
  }
  .table-title {
    margin-top: 32px;
    width: 500px;
    line-height: 40px;
    border: 1px solid #dddcdc;
    text-align: right;
    padding-right: 16px;
    background: #ecf2f2;
    border-bottom: none;
    border-radius: 6px 6px 0 0;
  }
}
</style>
