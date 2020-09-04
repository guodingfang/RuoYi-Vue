<template>
  <div>
    <div class="funk-bar">
      <el-button type="primary" @click="addDevice">新增设备</el-button>
    </div>
    <Table :tableData="tableData" :tableConfig="tableConfig">
      <template v-slot:enable>
        <el-table-column
          label="启用禁用"
        >
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.enableDisable"
              :active-value="1"
              :inactive-value="2"
              @change="stateConfig(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
      </template>
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <el-button @click="openEdit(scope.row)" type="text">编辑</el-button>
            <el-button @click="handelDel(scope.row)" type="text">删除</el-button>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getUnloadList"
    />
    <add-dialog
      :isCreate="isCreate"
      :dialogFlag.sync="addDialogFlag"
      :camForms.sync="unloadInfo"
      @query="getUnloadList"
    />
  </div>
</template>

<script>
  import { getDMList, delUnload, getUnloadDetail, stateConfig } from '@/api/unload/index';
  import Table from '@/components/Table';
  import Pagination from "@/components/Pagination";
  import AddDialog from  "../components/AddDialog";
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
          { prop: "equipmentName", label: "设备名称" },
          { prop: "deviceNumber", label: "监控设备" },
          { slot: 'enable'},
        ],
        total: 0,
        tableData: [],
        queryParams: {
          // pageNum: 1,
          // pageSize: 10,
          // presence: 1,
        },
        unloadInfo: {},
      }
    },
    created() {
      this.getUnloadList()
    },
    methods: {
     async getUnloadList() {
       try {
         const { rows, total } = await getDMList(this.queryParams);
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
       const { data:detailInfo } = await getUnloadDetail(row.id);
       this.unloadInfo = detailInfo;
       this.addDialogFlag = true;
      },


      async handelDel(e) {
       try {
         if( e.enableDisable === 1) {
           this.msgError('只能删除禁用设备');
           return false;
         }
         await delUnload(e.id);
         await this.getUnloadList();
         this.msgSuccess('离场成功');
       } catch (e) {
         console.log(e)
       }
      },
      async stateConfig(e) {
        try {
          await stateConfig({
            id: e.id,
            enableDisable: e.enableDisable
          });
          await this.getUnloadList();
          this.msgSuccess('操作成功');
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
