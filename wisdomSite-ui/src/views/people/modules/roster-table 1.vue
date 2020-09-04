<template>
  <div class="warp">
    <h3 class="table-tit">
      新增参建单位
      <i class="el-icon-close" @click="close"></i>
    </h3>
    <!-- <el-button @click='getData()'>button</el-button> -->
    <el-table
      default-expand-all
      row-key="id"
      ref="table"
      v-loading="loading"
      :data="tableData"
      @selection="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="企业名称" align="left"  prop="unitName" :show-overflow-tooltip="true" />
      <el-table-column label="社会统一代码" align="left"  prop="unitOrgcode" :show-overflow-tooltip="true" />
      <el-table-column label="企业类型" align="left"  prop="unitTypeName" :show-overflow-tooltip="true" />
      <el-table-column label="注册所在地" align="left"  prop="regAreaName" :show-overflow-tooltip="true" />
      <el-table-column label="法定代理人" align="left"  prop="legalPerson" :show-overflow-tooltip="true" />
      <el-table-column label="办公电话" align="left"  prop="officeNumber" :show-overflow-tooltip="true" />
    </el-table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getData"
    />
    <el-tag
      v-for="(tag,index) in tagList" :disable-transitions='true'
      :key="tag.unitName" closable type="success" @close='delTag(tag,index)'>
      {{tag.unitName}}
    </el-tag>
    <span slot="footer" class="dialog-footer">
      <el-button @click="close()">取 消</el-button>
      <el-button type="primary" @click="_submit">确 定</el-button>
    </span>
  </div>
</template>
<script>
import { getUtable,addUtable } from "@/api/people/company";
import cloneDeep from 'lodash/cloneDeep';
import unionBy from 'lodash/unionBy';
import findIndex from 'lodash/findIndex';
export default {
  name: "rTable",
  props: {
    detailData: {
      type: Object,
      default: () => {}
    },
  },
  data() {
    return {
      loading: false,
      tableData: [],
      tableSelection: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      tagList: []
    };
  },
  async created() {
    this.getData()
  },
  methods: {
    close() {
      this.$emit("on-close");
    },
    delTag(item,index) {
      const index_list = findIndex(this.tagList, function(o) { return o.id == item.id; })
      const index_setion = findIndex(this.tableSelection, function(o) { return o.id == item.id; })
      this.tableSelection.splice(index_setion, 1)
      this.tagList.splice(index_list, 1)
      console.log(index_list,index_setion);
      this.$nextTick(()=>{
        this.tableSelection.forEach(row => {
          this.$refs.table.toggleRowSelection(row,true);
        })
        // this.$refs.table.toggleRowSelection(...this.tableSelection)
      })
    },
    handleSelectionChange(val) {
      this.tableSelection = val
      this.tagList = unionBy(this.tagList.concat(this.tableSelection), 'id')
    },
    async _submit() {
      if (this.tableSelection && this.tableSelection.length > 0) {
        const ids = this.tableSelection.map((item)=> item.id )
        const re = await addUtable({ids:ids,projectId:this.detailData.projectId})
        if (re.code === 200) {
          this.$message({ type: 'success',message: re.msg })
          this.close()
        }
      } else {
        this.$message({ type: 'info',message: '请选择数据' })
      }
    },
    async getData(){
      console.log(this.tagList);
      // this.tagList = unionBy(this.tagList.concat(this.tableSelection), 'id')
      const re = await getUtable()
      this.total = re.total
      this.tableData = re.rows
      function get(arr1,arr2){
        let arr = []
        arr1.forEach(itemData => {
          arr2.forEach((itemArr, index) => {
            if (itemData.id === itemArr.id) {
              arr.push(itemData)
            }
          });
        });
        return arr
      }
      let arr = get(this.tableData,this.tagList)
      this.$nextTick(()=>{
        if (this.tagList && this.tagList.length > 0) {
          arr.forEach(row => {
            this.$refs.table.toggleRowSelection(row,true);
          })
        }

      })
    },
  }
};
</script>
<style lang="scss" scoped>
$--border-size: 1px solid #dddcdc;
.warp {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  background: #fff;
  z-index: $eq-pop;
  // padding: 0 40px 20px;
  overflow-y: auto;
  .table-tit {
    font-size: 16px;
    font-weight: normal;
    padding: 16px 30px;
    margin: 0;
    border-bottom: $--border-size;
    .el-icon-close {
      float: right;
    }
  }
  .el-tag {
    margin-right: 10px;
  }
}
</style>
