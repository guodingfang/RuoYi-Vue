<template>
  <section class="tab_warp">
    <div class="pos_title">
      <el-select
        @change="getEtable"
        placeholder="项目名称"
        v-model="queryParams.projectId"
        style="width: 180px; margin-right: 20px;"
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
    </div>

    <Table
      class="table-top"
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
      <template v-slot:operate>
        <el-table-column label="操作" align="left">
          <template slot-scope="scope">
            <span  class="jl-green mr10 pointer" @click="detailModel(scope.row.monitoringEquipment)">监测记录</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum "
      :limit.sync="queryParams.pageSize"
      @pagination="getEtable"
    />
    <!-- :detailData='detailData' -->
    <component
      :is="model"
      v-if="isShow"
      :state="state"
      :is-show="isShow"
      :detailData='detailData'
      @on-close="closePop"
    />
  </section>
</template>
<script>
import { getEtable, detailModel } from "@/api/scada/electricity";
import Table from '@/components/Table';
import detailElect from './detail-elect';
export default {
  name: 'electricity',
  components: {
    Table,
    detailElect
    // detailPro,
  },
  data () {
    return {
      tableConfig: [
        { slot: 'operate' },
        { prop: 'equipmentName', label: '设备名称' },
        { prop: 'monitoringEquipment', label: '设备编号'},
        { prop: 'monitoringStatus', label: '监测状态', width: ''},
      ],
      total: 0,
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: this.$lGet(this,'$DStore.state.getProject.0.id', null)
      },
      isShow: false,
      model: null,
      state: null,
      detailData: {}
    }
  },
  created() {
    this.getEtable()
  },
  methods: {
    async getEtable() {
      const re = await getEtable(this.queryParams)
      this.tableData = re.rows
      this.total = re.total
    },
    async detailModel(monitoringEquipment) {
      const re = await detailModel({monitoringEquipment:monitoringEquipment})
      this.model = 'detailElect'
      this.detailData = {
        ...re.data,
        monitoringEquipment: monitoringEquipment
      }
      this.showPop(true)
    },
    showPop(flag) {
      this.isShow = flag;
    },
    closePop(resful) {
      this.showPop(false);
      if (resful == 'setStaff') {
        // this.getData(this.paramInit);
      }
      this.model = null;
    },
  }
}
</script>
<style lang='scss' scoped>
.table-top{
  padding: 32px 0;
}
.pos_title{
  position: absolute;
  right: 0;
  top:-42px
}
</style>
