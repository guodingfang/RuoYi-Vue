<template>
  <section>
    <el-button type="primary" class="addBtn" @click="addChange">新增</el-button>
    <div class="query-box">
      <span>全部（{{ total }}）</span>
      <div class="r-query">
        <el-select  @change="getMtable()" v-model="queryParams.projectId" placeholder="项目名称">
          <el-option
            v-for="item in projectList"
            :key="item.id"
            :label="item.projectName"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <el-input
          placeholder="输入物品名称搜索"
          v-model="queryParams.itemName"
          style="width: 217px;"
        >
          <i slot="suffix" @click="getMtable()" class="el-input__icon el-icon-search"></i>
        </el-input>

        <el-button
          type="primary"
          icon="el-icon-search"
          @click="getMtable"
        >查询</el-button>
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="restItable"
        >重置</el-button>
      </div>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:enableStatus>
        <el-table-column label="启用禁用" align="left">
          <template slot-scope="scope">
            <el-switch
              @change="stateManage(scope.row)"
              v-model="scope.row.enabled"
              active-value="1"
              inactive-value="2"
              active-color="#13ce66"
              inactive-color="#909399">
            </el-switch>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span
              class="jl-green mr10 pointer"
              @click="modifyChange(scope.row)"
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
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getMtable"
    />
    <component
      :is="model"
      v-if="popShow"
      :state="state"
      :detailData="detailData"
      :is-show="popShow"
      @on-close="closePop"
    />
    <!-- <detailRecord :isShow="isDetail" :detailData="detailData" @on-close="close" /> -->
  </section>
</template>
<script>
import Table from "@/components/Table";
// import detailRecord from './detail-record'
import { listProject } from "@/api/people/attendance";
import { getMtable,detailManage,stateManage,deleteManage } from "@/api/people/manage";
  import addManage from './../components/add-manage';

export default {
  name: 'record',
  components: { Table,addManage },
  data () {
    return {
      tableConfig: [
        { slot: "operate" },
        { prop: 'itemName', label: '物品名称' },
        { prop: 'unit', label: '计量单位' },
        { prop: 'changePoint', label: '所需点券', width: ''},
        { prop: 'hasChangeMount', label: '已兑数量', width: ''},
        { prop: 'inventoryMount', label: '剩余库存', width: ''},
        { slot: "enableStatus" },
      ],
      total: 0,
      tableData: [],
      //项目查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      detailData: {},
      popShow: false,
      model: null,
      state: null,
      projectList: []
    }
  },
  created() {
    this.getProjectList()
  },
  methods: {
    /** 获取项目下拉查询 */
    getProjectList() {
      listProject().then(response => {
        this.projectList = response.rows;
        if (!this.queryParams.projectId) {
          this.queryParams.projectId = response.rows[0].id;
          this.getMtable();
        }
        //TODO
        // this.queryParams.projectId = response.rows[0].id
      });
    },
    restItable() {
      this.queryParams.pageNum = 1
      this.queryParams.pageSize = 10
      // this.queryParams.keyWordName = ''
      // this.queryParams.month = moment().format("yyyy-MM")
      this.getMtable()
    },
    async getMtable() {
      const re = await getMtable(this.queryParams)
      console.log(re);
      this.tableData = re.rows;
      this.total = re.total;
    },
    addChange() {
      this.state = 'add'
      this.detailData = {
        projectId: this.queryParams.projectId
      }
      this.model = 'addManage'
      this.showPop(true)
    },
    async modifyChange(row) {
      const re = await detailManage(row.id)
      this.detailData = re.data
      this.state = 'modify'
      this.model = 'addManage'
      this.showPop(true)
    },
    showPop(flag) {
      this.popShow = flag;
    },
    closePop(resful) {
      this.showPop(false);
      if (resful == "success") {
        this.getMtable();
      }
      this.model = null;
    },
    async stateManage(item) {
      // const re = await stateManage(item.enabled,item.id)
      const re = await stateManage({
        enabled:item.enabled,
        id: item.id
      })
      if (re.code == 200) {
        this.$message({
          message: re.msg,type: "success",
        });
      }
      this.getMtable()
    },
    deleteFn(ids) {
      return new Promise(res => {
        this.$confirm("是否确认删除?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(async () => {
            const re = await deleteManage(ids);
            if (re.code === 200) {
              this.$message({
                message: re.msg,
                type: "success"
              });
              this.getMtable();
            }
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除"
            });
          });
      });
    }
  }
}
</script>
<style lang='scss' scoped>
  .addBtn{
    position: absolute;
    right: 0;
    top: -42px;
    width: 97px;
  }
  .title-btn{
    position: absolute;
    top: -40px;
    right: 0;
  }
  .query-box {
    height: 60px;
    line-height: 60px;
    display: flex;
    justify-content: space-between;
    .add-btn {
      margin-top: 14px;
    }
  }
</style>
