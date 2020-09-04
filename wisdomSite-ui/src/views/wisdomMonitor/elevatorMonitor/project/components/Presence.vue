<template>
  <div>
    <div class="funk-bar">
      <el-button type="primary" @click="addDevice">新增设备</el-button>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:moniDevice>
        <el-table-column label="监控设备" align="left" width="240">
          <template slot-scope="scope">
            <div v-if="scope.row.deviceType==='1'">
              <div v-for="item in scope.row.monitorDevices" class="elevator-item">
                SN: {{item.serialNumber}}
              </div>
            </div>
            <div v-if="scope.row.deviceType==='2'">
              <div v-for="item in scope.row.monitorDevices" class="elevator-item">
                {{item.cageMark==='1'?'左笼':'右笼'}}：SN: {{item.serialNumber}}
              </div>
            </div>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <el-button @click="openEdit(scope.row)" type="text">设备管理</el-button>
            <el-popconfirm
              title="确认该设备已经离场？离场设备，系统将不再统计其运行记录"
              @onConfirm="handelDel(scope.row)"
            >
              <el-button style="color:#da4444 !important;" slot="reference" type="text">离场</el-button>
            </el-popconfirm>
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
      :isCreate="isCreate"
      :dialogFlag.sync="addDialogFlag"
      :dataForms.sync="elevatorInfo"
      @query="getDeviceList"
    />
  </div>
</template>

<script>
  import { deviceManaList, departure, getElevatorDetail } from '@/api/smartMonitor/elevator';
  import Table from '@/components/Table';
  import Pagination from "@/components/Pagination";
  import AddDialog from  "./AddDialog";
  export default {
    name: "Presence",
    components: {
      Table,
      Pagination,
      AddDialog
    },
    data() {
      return {
        isCreate: true,
        addDialogFlag: false,
        tableConfig: [
          { slot: 'operate'},
          { prop: "selfNumber", label: "自编号" },
          { prop: "recordNumber", label: "产权备案号" },
          { prop: "deviceName", label: "设备名称" },
          { prop: "inDate", label: "进场时间" },
          { slot: "moniDevice"},
        ],
        total: 0,
        tableData: [],
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          deviceStatus: 1,
        },
        elevatorInfo: {},
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
      addDevice() {
        this.isCreate = true;
        this.addDialogFlag = true;
      },
      async openEdit(row) {
        this.isCreate = false;
        const { data:detailInfo } = await getElevatorDetail(row.id);
        this.elevatorInfo = detailInfo;
        this.addDialogFlag = true;
      },
      async handelDel(e) {
        try {
          await departure(e.id);
          await this.getDeviceList();
          this.msgSuccess('离场成功');
        } catch (e) {
          console.log(e)
        }
      },
    }
  }
</script>

<style scoped lang="scss">
  .funk-bar{
    margin: 20px 0 20px;
  }
</style>
