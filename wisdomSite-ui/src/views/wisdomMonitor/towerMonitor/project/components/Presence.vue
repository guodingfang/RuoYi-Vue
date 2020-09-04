<template>
  <div>
    <div class="funk-bar">
      <el-button type="primary" @click="addDevice">新增设备</el-button>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <el-button @click="openEdit(scope.row)" type="text">设备管理</el-button>
            <el-popconfirm
              title="确认该设备已经离场？离场设备，系统将不再统计其运行记录"
              @onConfirm="handelDel(scope.row)"
            >
              <el-button style="color:#da4444 !important;" slot="reference" type="text">离场</el-button>
<!--              <el-button slot="reference">删除</el-button>-->
            </el-popconfirm>
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
      :isCreate="isCreate"
      :dialogFlag.sync="addDialogFlag"
      :camForms.sync="towerInfo"
      @query="getTowerList"
    />
  </div>
</template>

<script>
  import { towerEquipmentList, departure, getTowerDetail } from '@/api/smartMonitor/tower';
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
          presence: 1,
        },
        towerInfo: {},
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
      addDevice() {
       this.isCreate = true;
       this.addDialogFlag = true;
      },
      async openEdit(row) {
       this.isCreate = false;
       const { data:detailInfo } = await getTowerDetail(row.id);
       this.towerInfo = detailInfo;
       this.addDialogFlag = true;
      },
      async handelDel(e) {
       try {
         await departure(e.id);
         await this.getTowerList();
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
