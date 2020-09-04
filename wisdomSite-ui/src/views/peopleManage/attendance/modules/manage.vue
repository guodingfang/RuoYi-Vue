<template>
  <section class="">
    <div class="pos_title">
      <el-select
        placeholder="项目名称"
        v-model="queryParams.projectId"
        style="width: 180px; margin-right: 20px;"
        @change="getAtable"
        filterable
        clearable
      >
        <el-option
          v-for="item in $DStore.state.getProject"
          :key="item.id"
          :label="item.projectName"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <el-button type="primary" class="add—btn" @click="addModel">新增考勤机</el-button>
    </div>
    <div class="table-warp">
      <Table
        :tableData="tableData"
        :tableConfig="tableConfig"
      >
        <template v-slot:operate>
          <el-table-column label="操作" align="left">
            <template slot-scope="scope">
              <span  class="jl-green mr10 pointer" @click="detailFn(scope.row.id)">详情</span>
              <span  class="jl-green mr10 pointer" @click="modifyModel(scope.row.id)">编辑</span>
              <span  class="jl-green mr10 pointer" @click="deleteData(scope.row.id)">删除</span>
            </template>
          </el-table-column>
        </template>
      </Table>

      <Pagination
        :total="total"
        :page.sync="queryParams.pageNum "
        :limit.sync="queryParams.pageSize"
        @pagination="getAtable"
      />
    </div>
    <el-dialog
      :title="`考勤机`"
      :visible.sync="popShow"
      width="500px"
      :close-on-click-modal="false"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >

        <el-form-item label="打卡机名称" prop="name">
          <span class="label_span" v-if="state == 'detail'">{{ruleForm.name}}</span>
          <el-input
            v-else
            v-model="ruleForm.name"
            placeholder="打卡机名称"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item label="设备号" prop="serialNumber">
          <span class="label_span" v-if="state == 'detail'">{{ruleForm.serialNumber}}</span>
          <el-input
            v-else
            v-model="ruleForm.serialNumber"
            placeholder="设备号"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item label="方向" prop="direction">
          <span class="label_span" v-if="state == 'detail'">{{ruleForm.directionName}}</span>
          <el-select v-model="ruleForm.direction" placeholder="方向" v-else>
            <el-option label="进" value="1"></el-option>
            <el-option label="出" value="2"></el-option>
          </el-select>
        </el-form-item>
        <span></span>
        <el-form-item label="打卡机类型" prop="machineType" class="buttom">
          <span class="label_span" v-if="state == 'detail'">{{ruleForm.machineTypeName}}</span>
          <span class="label_span" v-else-if="state == 'modify'">{{ruleForm.machineTypeName}}</span>
          <el-select v-else v-model="ruleForm.machineType" placeholder="打卡机类型">
            <el-option
              v-for="item in $DStore.state.getMachineType"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue"
            >
          </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" v-if="state != 'detail'">
        <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
      </div>
    </el-dialog>
  </section>
</template>
<script>
import { getAtable, addInstall, modifyInstall, detailInstall, deleteInstall, } from "@/api/people/install";
import Table from '@/components/Table';
import {
  getProject,
 } from "@/api/common";
export default {
  name: 'manage',
  components: {
    Table,
  },
  data () {
    return {
      tableConfig: [
        { slot: 'operate' },
        { prop: 'name', label: '打卡机名称', width: ''},
        { prop: 'serialNumber', label: '设备号', width: ''},
        { prop: 'directionName', label: '方向', width: ''},
        { prop: 'online', label: '状态', width: ''},
      ],
      total: 0,
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyWordName: "",
      },
      popShow: false,
      ruleForm: {
        name: ""
      },
      rules: {
        name: [{ required: true, message: " ", trigger: "blur" }],
        machineType: [{ required: true, message: " ", trigger: "change" }],
      },
      state: null, //modify
      dialogTitle: null,
    }
  },
  async created() {
    this.getAtable()
    // const re = await getProject()
    // console.log(re);
    // console.log(123);
  },
  watch: {
    popShow(val) {
      if (val) {
      } else {
        this.$refs['ruleForm'].resetFields();
      }
    }
  },
  methods: {
    async getAtable() {
      const re = await getAtable(this.queryParams)
      this.tableData = re.rows
      console.log(this.tableData);
      this.total = re.total
    },
    showModel(flag) {
      if (!this.queryParams.projectId && this.state == 'add') {
        this.$message('请选择项目');
        return
      }
      this.popShow = flag
    },
    closePop() {
      this.showModel(false)
    },
    _submit() {
      this.$refs['ruleForm'].validate(async (valid) => {
        if (valid) {
          let re
          if (this.state == 'modify') {
            re = await modifyInstall(this.ruleForm,)
          } else {
            re = await addInstall({
              ...this.ruleForm,
              projectId:this.queryParams.projectId
            })
          }
          if (re.code == 200) {
            this.$message({
              message: re.msg,type: "success",
            });
            this.showModel(false)
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    addModel() {
      this.state = 'add'
      this.showModel(true)
    },
    modifyModel(id) {
      this.state = 'modify'
      this.detailFn(id, true)
      this.showModel(true)
    },
    async detailFn(id, flag) {
      if (!flag) {
        this.state = 'detail'
      }
      const re = await detailInstall(id)
      this.ruleForm = re.data
      this.showModel(true)
    },
    async deleteData(id) {
      return new Promise(res => {
        this.$confirm("是否确认删除?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(async () => {
            const re = await deleteInstall(id);
            if (re.code === 200) {
              this.$message({
                message: re.msg,
                type: "success",
                duration: 1000
              });
              this.getTable();
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
}
</script>
<style lang='scss' scoped>
.table-warp{
  margin-top: 20px;
}
.pos_title{
  position: absolute;
  right: 0;
  top:-60px;
  ::v-deep{
    .el-input__inner{
      border: 1px solid #DCDFE6;
    }
  }

}
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
  .label_span{
    padding-left: 16px;
  }
}
</style>
