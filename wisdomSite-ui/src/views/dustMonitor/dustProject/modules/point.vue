<template>
  <section class="dust-warp">
    <el-button type="primary" class="btn" @click="addPoint">新增监测点</el-button>
    <div class="table-tit">
      全部（{{total}}）
    </div>
    <Table
      class="table-warp"
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span  class="jl-green mr10 pointer" @click="modifyPoint(scope.row)">编辑</span>
            <span  class="jl-green mr10 pointer" @click="deletePoint(scope.row)">删除</span>
          </template>
        </el-table-column>
      </template>
      <template v-slot:statusName>
        <el-table-column label="启用禁用" align="left">
          <template slot-scope="scope">
            <el-switch
              @change="statePoint(scope.row)"
              v-model="scope.row.isDelete"
              active-value="1"
              inactive-value="2"
              active-color="#13ce66"
              inactive-color="#909399">
            </el-switch>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getPointTable"
    />

    <component
      :is="model"
      :state="state"
      :detailData='detailData'
      :is-show="popShow"
      @on-close="closePop"
    />
  </section>
</template>
<script>
import Table from '@/components/Table';
import {
  getPointTable,deletePoint,statePoint,detailPoint
 } from "@/api/dustMonitor";
 import addForm from './add-form'
export default {
  name: 'dustEnterprise',
  components: { Table, addForm },
  data () {
    return {
      tableConfig: [
        { slot: 'operate' },
        { prop: 'monitoringpointName', label: '监测点名称' },
        { prop: 'serialNumber', label: '监测设备'},
        { prop: 'videoSerialNumber', label: '监测点视频'},
        { slot: 'statusName' },
      ],
      total: 0,
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      popShow: false,
      detailData: {},
      state: null,
      model: null,
    }
  },
  async created() {
    this.getPointTable()
  },
  methods: {
    async getPointTable() {
      const re = await getPointTable(this.queryParams)
      this.tableData = re.rows
      this.total = re.total
    },
    showPop(flag) {
      this.popShow = flag
    },
    closePop(resful) {
      this.showPop(false);
      if (resful == 'setStaff') {
        this.getPointTable();
      }
      this.model = null;
    },
    detail(row) {
      this.detailData = row
      this.showPop(true)
    },
    addPoint(row) {
      this.state = 'add'
      this.model = 'addForm'
      this.detailData = {}
      this.showPop(true)
    },
    async modifyPoint(row) {
      this.state = 'modify'
      const re = await detailPoint(row.id)
      this.detailData = re.data
      this.model = 'addForm'
      this.showPop(true)
    },
    deletePoint(row) {
      if (row.isDelete != 2) {
        this.$message({
          message: '状态为禁用时才可删除',
          type: 'warning'
        });
        return
      }
      return new Promise(res => {
        this.$confirm("是否确认删除?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(async () => {
            const re = await deletePoint(row.id);
            if (re.code === 200) {
              this.$message({
                message: re.msg,
                type: "success",
              });
              this.getPointTable();
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
    async statePoint(item) {
      const re = await statePoint(item.isDelete,item.id)
      if (re.code == 200) {
        this.$message({
          message: re.msg,type: "success",
        });
      }
      this.getPointTable()
    }
  }
}
</script>
<style lang='scss' scoped>
$--border-size: 1px solid #dddcdc;
.dust-warp{
  position: relative;
  .btn{
    position: absolute;
    right: 27px;
    top: -60px;
  }
  .table-tit{
    margin: 17px 27px;
    @include flex(null, center);
  }
  .t-btn{
    margin-left: auto;
    margin-right: 27px;
    .tb-span{
      color: #999999;
      font-size: 14px;
      margin: 0 10px;
    }
  }
  .table-warp{
    margin: 0 27px
  }
}
</style>
