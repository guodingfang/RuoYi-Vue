<template>
  <div>
    <div class="top"></div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <el-button @click="openEdit(scope.row)" type="text">重新进场</el-button>
            <el-button style="color:#da4444 !important;" @click="handleDel(scope.row)" type="text">删除</el-button>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getTowerList"
    />
    <add-dialog
      :isReentry="isReentry"
      :isCreate="isCreate"
      :dialogFlag.sync="addDialogFlag"
      :camForms.sync="towerInfo"
      @query="getTowerList"
    />
  </div>
</template>

<script>
  import { towerEquipmentList, getTowerDetail, delTower  } from '@/api/smartMonitor/tower';
  import Table from '@/components/Table';
  import Pagination from "@/components/Pagination";
  import AddDialog from "./AddDialog";
  export default {
    name: "Absence",
    components: {
      Table,
      Pagination,
      AddDialog
    },
    data() {
      return {
        isReentry: true,
        isCreate: false,
        addDialogFlag: false,
        towerInfo: {},
        tableConfig: [
          { slot: 'operate'},
          { prop: "selfNumbering", label: "自编号" },
          { prop: "propertyRecordno", label: "产权备案号" },
          { prop: "equipmentName", label: "设备名称" },
          { prop: "enterData", label: "进场时间" },
          { prop: "monitoringEquipment", label: "监控设备编号" },
          { prop: "videoEquipmentNo", label: "吊钩视频编号" },
          { prop: "faceNumber", label: "司机人脸编号" },
        ],
        total: 0,
        tableData: [],
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          presence: 2,
        }
      }
    },
    created() {
      this.getTowerList()
    },
    methods: {
      async getTowerList() {
        try {
          const { rows, total } = await towerEquipmentList(this.queryParams);
          this.tableData = rows;
          this.total = total;
        } catch (e) {
          console.log(e)
        }
      },
      async openEdit(row) {
        this.isCreate = false;
        const { data:detailInfo } = await getTowerDetail(row.id);
        this.towerInfo = detailInfo;
        this.addDialogFlag = true;
      },
      async handleDel(row) {
        try {
          await this.$confirm('确定删除吗?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          });
          await delTower(row.id);
          await this.getTowerList();
          this.msgSuccess('删除成功');
        } catch (e) {
          console.log(e)
        }
      }
    }
  }
</script>

<style scoped lang="scss">
.top{
  margin-top: 40px;
}
</style>
