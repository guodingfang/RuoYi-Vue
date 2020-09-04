<template>
  <el-dialog
    title="人员"
    :visible.sync="isShow"
    width="900px"
    :before-close="handleClose"
    :close-on-click-modal="false"
  >
    <pTable :tableData="labelData" :tableStyle="{ width:'860px' }" style="margin-bottom: 20px;"></pTable>
      <Table
        :tableData="tableData"
        :tableConfig="tableConfig"
      >
        <template v-slot:operate>
          <el-table-column label="兑换数量" align="left">
            <template slot-scope="scope">
              <el-input-number
              v-model="scope.row.num"
              @change="handleChange(scope.row.index,scope.row.num,scope.row.changePoint)" :min="1"
              label="描述文字"></el-input-number>
            </template>
          </el-table-column>
        </template>
      </Table>
      <div class="tb-footer">
        目前兑换所需点卷
        <span class='tb-text'>{{num}}点卷</span>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button class="r_button" @click="handleClose">取消</el-button>
        <el-button type="primary" class="r_button" @click="_submit">保存</el-button>
      </div>
  </el-dialog>
</template>
<script>
import Table from '@/components/Table';
import pTable from './pTable';
import { exchange,getEtable,keepJust } from "@/api/people/security";
export default {
  props: {
    isShow: {
      type: Boolean,
      default: false
    },
    detailData: {
      type: Object,
      default: () => {}
    },
    state: {
      type: String,
      default: () => null
    }
  },
  components: {
    Table,pTable
  },
  data () {
    return {
      tableConfig: [
        { slot: "operate" },
        { prop: 'itemName', label: '物品名称', width: ''},
        { prop: 'unit', label: '计量单位' },
        { prop: 'changePoint', label: '使用点券' },
        { prop: 'inventoryMount', label: '剩余库存', width: ''},
      ],
      tableData: [
        {
          changePoint: 10,
        },
        {
          changePoint: 20,
        },
      ],
      num: 0,
      detailTable: {}
    }
  },
  watch: {
    isShow: {
      async handler(val){
        if(val) {
          // exchange,getEtable
          const res = await exchange(this.detailData.personId)
          this.detailTable = res.data
          const re = await getEtable(
            {
              projectId: this.detailData.projectId,
              enabled: 1
            }
          )
          this.tableData = re.rows
        }
      },
      immediate:true
    }
  },
  computed: {
    labelData() {
      const re = this.detailTable
      return [
        {key: '姓名', value: re.personName},
        {key: '身份证号', value: re.idNumber},
        {key: '身份类型', value: re.workTypeName},
        {key: '可兑换点券', value: re.exchangeablePoints},
      ]
    }
  },
  methods: {
    handleChange(index,num,val) {
      let arr = []
      this.tableData.map(item => {
        if (item.num) {
          arr.push(item.num * item.changePoint)
        }
      })
      this.num = eval(arr.join("+"))
    },
    handleClose() {
      this.$emit("on-close");
    },
    async _submit() {
      let exchanges = this.tableData.map(i => {
        const number = i.num ? i.num : 0
        return {
          costPoint: number * i.changePoint,
          exchangeMount: number,
          itemName: i.itemName,
        }
      })
      const { projectId,personId } = this.detailData
      let parm = {
        projectId,personId,exchanges
      }
      const re = await keepJust(parm)
      if (re.code == 200) {
        this.$message({
          message: re.msg,type: "success",
        });
        this.$emit("on-close", 'success');
      }
    }
  }
}
</script>
<style lang='scss' scoped>
.tb-footer{
  border: 1px solid #dddcdc;
  border-top: 0;
  height: 42px;
  line-height: 42px;
  text-align: right;
  .tb-text{
    font-size: 16px;
    color: red;
    margin-right: 17px;
  }
}
</style>
