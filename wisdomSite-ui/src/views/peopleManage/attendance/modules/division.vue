<template>
  <section class="">
    <div class="pos_title">
      <el-select
        placeholder="项目名称"
        v-model="queryParams.projectId"
        style="width: 180px; margin-right: 20px"
        class="add-select"
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
      <el-button type="primary" class="add—btn" @click="addModel">新增分区</el-button>
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
        @pagination="getZtable"
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

        <el-form-item label="区域名称" prop="name">
          <span class="label_span" v-if="state == 'detail'">{{ruleForm.name}}</span>
          <el-input
            v-else
            v-model="ruleForm.name"
            placeholder="区域名称"
            clearable
            size="small"
          />
        </el-form-item>
        <div class="form-temp">
          <span class="form_label">打卡机设置</span>
          <el-button v-if="this.state != 'detail'" size="mini" style="margin-left: auto;" type="primary" @click="showTag">添加</el-button>
        </div>
        <el-table :data="addList" >
          <el-table-column label="打卡机名称" align="left" prop="name" />
          <el-table-column label="设备号" align="left" prop="serialNumber" />
          <el-table-column label="操作" align="left" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="deleteTable(scope.row,scope.$index)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer" v-if="state != 'detail'">
        <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
      </div>
      <el-dialog
        width="600px"
        title="添加考勤机"
        :visible.sync="innerVisible"
        append-to-body>
        <h4 class="tit">考勤机</h4>
        <el-checkbox-group v-model="checkedList">
          <el-checkbox
            @change="handledataChange(item,$event)"
            v-for="item in dataList"
            :label="item.id" :key="item.id"
          >
            {{item.name}}
          </el-checkbox>
        </el-checkbox-group>
        <h4 class="tit">已选考勤机</h4>
        <el-tag
          v-for="(tag, index) in tagList"
          :disable-transitions="true"
          :key="index"
          closable
          type="success"
          @close="delTag(tag, index)"
        >
          {{ tag.name }}
        </el-tag>
        <div slot="footer" class="dialog-footer" v-if="state != 'detail'">
          <el-button type="primary" class="r_button" @click="addSubmit">保存</el-button>
        </div>
      </el-dialog>
    </el-dialog>
  </section>
</template>
<script>
import { getAtable } from "@/api/people/install";
import { getZtable, addInstall, modifyInstall, detailInstall, deleteInstall, } from "@/api/people/division";
import Table from '@/components/Table';
export default {
  name: 'division',
  components: {
    Table,
  },
  data () {
    return {
      tableConfig: [
        { slot: 'operate' },
        { prop: 'name', label: '区域名称', width: ''},
        { prop: 'machineContact', label: '授权考勤机', width: ''},
      ],
      total: 0,
      tableData: [],
      dataList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      popShow: false,
      innerVisible: false,
      ruleForm: {
        name: ""
      },
      rules: {
        name: [{ required: true, message: " ", trigger: "blur" }],
        machineType: [{ required: true, message: " ", trigger: "change" }],
      },
      state: null, //modify
      dialogTitle: null,
      addList: [],
      checkedList: [],
      tagList: [],
    }
  },
  watch: {
    popShow(val) {
      if (val) {

      } else {
        this.ruleForm.name = null;
        this.addList = []
        this.tagList = []
        this.checkedList = []
      }
    }
  },
  created() {
    this.getZtable()
    this.getAtable()
  },
  methods: {
    async getZtable() {
      const re = await getZtable(this.queryParams)
      this.tableData = re.rows
      this.total = re.total
    },
    showModel(flag) {
      if (!this.queryParams.projectId) {
        this.$message('请选择项目');
        return
      }
      this.popShow = flag
    },
    showVisible(flag) {
      this.innerVisible = flag
    },
    closePop() {
      this.showModel(false)
    },
    _submit() {
      this.$refs['ruleForm'].validate(async (valid) => {
        if (valid) {
          let re
          const data = {
            ...this.ruleForm,
            machines: this.addList,
            projectId:this.queryParams.projectId
          }
          if (this.state == 'modify') {
            re = await modifyInstall(data)
          } else {
            re = await addInstall(data)
          }
          if (re.code == 200) {
            this.$message({
              message: re.msg,type: "success",
            });
            this.showModel(false)
            this.getZtable()
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    showTag() {
      // this.addList
      this.showVisible(true)
      if (this.state == 'modify') {
        this.tagList = this.addList
        this.checkedList = this.addList.map(item => item.id)
      }

    },
    addSubmit() {
      this.addList = this.tagList
      this.showVisible(false)
    },
    handledataChange(item,event) {
      if (this.tagList.length > 0) {
          if (event) {
            this.tagList.push(item)
          } else {
            let indexTag
            this.tagList.forEach((tag,index) => {
              if (item.id === tag.id) {
                indexTag = index
              }
            })
            this.tagList.splice(indexTag,1)
          }
      } else {
        this.tagList.push(item)
      }
    },
    delTag(item, index) {
      let indexChecked = this.checkedList.indexOf(item.id)
      this.checkedList.splice(indexChecked,1)
      this.tagList.splice(index, 1);
    },
    deleteTable(item,index) {
      this.addList.splice(index,1)
      let indexChecked = this.checkedList.indexOf(item.id)
      // let indexTag = this.tagList.indexOf(item.id)
      this.checkedList.splice(indexChecked,1)
      // this.tagList.splice(indexTag, 1);
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

      const re = await detailInstall(id)
      this.ruleForm = re.data
      this.addList = re.data.machines
      this.tagList = re.data.machines
      this.checkedList = this.addList.map(item => item.id)
      if (!flag) {
        this.state = 'detail'
        this.showModel(true)
      }
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
              this.getZtable();
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
    async getAtable() {
      const re = await getAtable(this.queryParams)
      this.dataList = re.rows
      console.log(this.dataList);
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
  top:-60px
}
// .add-select{
//   position: absolute;
//   right: 100px;
//   top:-50px
// }
.form-temp{
  display: flex;
  align-items: baseline;
}
.form_label{
  display: inline-block;
  width: 88px;
  text-align: right;
  margin-bottom: 16px;
}
.el-tag {
  margin-right: 10px;
}
.tit {
  border-left: 4px solid #1ab394;
  padding-left: 10px;
  width: 100%;
  .tit_right {
    padding: 0;
    float: right;
  }
}
</style>
