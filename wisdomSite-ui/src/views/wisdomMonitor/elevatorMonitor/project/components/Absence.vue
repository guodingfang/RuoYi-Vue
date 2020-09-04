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
      @pagination="getDeviceList"
    />
    <add-dialog
      :isReentry="isReentry"
      :isCreate="isCreate"
      :dialogFlag.sync="addDialogFlag"
      :dataForms.sync="detailInfo"
      @query="getDeviceList"
    />
  </div>
</template>

<script>
  import { deviceManaList, getElevatorDetail, delElevator  } from '@/api/smartMonitor/elevator';
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
        isReentry: false,
        isCreate: false,
        addDialogFlag: false,
        detailInfo: {},
        tableConfig: [
          { slot: 'operate'},
          { prop: "selfNumber", label: "自编号" },
          { prop: "recordNumber", label: "产权备案号" },
          { prop: "deviceName", label: "设备名称" },
          { prop: "inDate", label: "进场时间" },
          { prop: "outDate", label: "进场时间" },
        ],
        total: 0,
        tableData: [],
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          deviceStatus: 0,
        }
      }
    },
    created() {
      this.getDeviceList()
    },
    methods: {
      async getDeviceList() {
        try {
          const { rows, total } = await deviceManaList(this.queryParams);
          this.tableData = rows;
          this.total = total;
        } catch (e) {
          console.log(e)
        }
      },
      async openEdit(row) {
        this.isCreate = false;
        const { data:detailInfo } = await getElevatorDetail(row.id);
        this.detailInfo = detailInfo;
        this.addDialogFlag = true;
      },
      async handleDel(row) {
        try {
          await this.$confirm('确定删除吗?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          });
          await delElevator(row.id);
          await this.getDeviceList();
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
