<template>
  <section class="t_warp">
    <Table
      :tableData="tableData"
      :tableConfig="tableConfig"
    >
      <template v-slot:operate>
        <el-table-column label="证书附件" align="left">
          <template slot-scope="scope">
            <!-- <span  class="jl-green mr10 pointer" @click="detailWorker(scope.row.id)">详情</span> -->
            <span  class="jl-green mr10 pointer" @click="openDialog(scope.row)">查看</span>
          </template>
        </el-table-column>
      </template>
    </Table>
    <el-dialog
      title="证书详情"
      :visible.sync="dialogVisible"
      append-to-body
      width="700px">
      <el-form label-width="80px" >
        <el-form-item label="证书正面">
          <img :src="imgData.frontImage" alt="" width="178" height="178">
        </el-form-item>
        <el-form-item label="证书反面">
          <img :src="imgData.reverseImage" alt="" width="178" height="178">
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </section>
</template>
<script>
import {
  getNtable,
} from "@/api/people/workerLib";
import Table from '@/components/Table';
export default {
  name: 'qualification',
  props: {
    parmId: {
      type: [String,Number],
      default: () => null
    }
  },
  components: {
    Table,
  },
  data () {
    return {
     tableConfig: [
        { slot: 'operate' },
        { prop: 'certificateTypeName', label: '证书类型' },
        { prop: 'certificateName', label: '证书名称', width: ''},
        { prop: 'certificateNumber', label: '证书编号', width: ''},
        { prop: 'certificateLevel', label: '证书等级', width: ''},
        { prop: 'effectiveDate', label: '有效期（起）', width: ''},
        { prop: 'invalidDate', label: '有效期（止）', width: ''},
      ],
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deleted: 1,
        id: this.parmId
      },
      dialogVisible: false,
      imgData: {}
    }
  },
  created() {
    this.getNtable()
  },
  methods: {
    openDialog(row) {
      console.log(row);
      const {frontImage,reverseImage} = row
      this.imgData = {
        frontImage,reverseImage
      }
      this.dialogVisible = true
    },
    async getNtable() {
      const re = await getNtable(this.parmId)
      this.tableData = re.data
      console.log(re);
    },
  }
}
</script>
<style lang='scss' scoped>
.t_warp{
  margin-top: 24px;
}
.table_img{
  width: 178px;
  height: 178px;
  display: inline-block;
}
</style>
