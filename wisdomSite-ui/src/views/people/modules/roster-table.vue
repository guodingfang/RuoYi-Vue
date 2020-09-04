<template>
  <!-- <div class="warp">
    <h3 class="table-tit">
      新增参建单位
      <i class="el-icon-close" @click="close"></i>
    </h3> -->
  <el-dialog
    title="新增参建单位"
    :visible.sync="isShow"
    width="900px"
    :before-close="close"
    :close-on-click-modal="false"
  >
    <!-- <el-button @click='getData()'>button</el-button> -->
    <el-table
      style="margin-top: 20px;"
      default-expand-all
      row-key="id"
      ref="table"
      v-loading="loading"
      :data="tableData"
      @selection-change="handleSelectionChange"
      :highlight-current-row="true"
      @row-click="handleClick"
    >
      <!-- <el-table-column type="selection" width="55" /> -->
      <el-table-column
        label="企业名称"
        align="left"
        prop="unitName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="社会统一代码"
        align="left"
        prop="unitOrgcode"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="企业类型"
        align="left"
        prop="unitTypeName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="注册所在地"
        align="left"
        prop="regAreaName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="法定代理人"
        align="left"
        prop="legalPerson"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="办公电话"
        align="left"
        prop="officeNumber"
        :show-overflow-tooltip="true"
      />
    </el-table>
    <Pagination
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getData"
    />
    <el-tag
      v-for="(tag, index) in tagList"
      :disable-transitions="true"
      :key="index"
      closable
      type="success"
      @close="delTag(tag, index)"
    >
      {{ tag.unitName }}
    </el-tag>
    <span slot="footer" class="dialog-footer" >
      <el-button @click="close()">取 消</el-button>
      <el-button type="primary" @click="_submit">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { getUtable, addUtable } from "@/api/people/company";
import cloneDeep from "lodash/cloneDeep";
import unionBy from "lodash/unionBy";
export default {
  name: "rTable",
  props: {
    detailData: {
      type: Object,
      default: () => {}
    },
    isShow: {
      type: Boolean,
      default: false
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
    this.getData();
  },
  methods: {
    close() {
      this.$emit("on-close");
    },
    delTag(item, index) {
      // let arr = cloneDeep(this.tableSelection)
      this.tagList.splice(index, 1);
      // this.tableSelection.splice(index, 1)
      // this.tagList = unionBy(this.tagList.concat(this.tableSelection), 'id')
      // this.$nextTick(()=>{
      //   this.$refs.table.toggleRowSelection(...this.tableSelection)
      // })
    },
    handleSelectionChange(val) {
      this.tableSelection = val;
      this.tagList = unionBy(this.tagList.concat(this.tableSelection), "id");
    },
    handleClick(val) {
      let arr = [];
      arr.push(val);
      if (this.tagList && this.tagList.length > 0) {
        this.tagList.forEach((item, index) => {
          if (item.id == val.id) {
            this.tagList.splice(index, 1);
          } else {
            this.tagList = unionBy(this.tagList.concat(arr), "id");
          }
        });
      } else {
        this.tagList = unionBy(this.tagList.concat(arr), "id");
      }
    },
    async _submit() {
      if (this.tagList && this.tagList.length > 0) {
        const ids = this.tagList.map(item => item.id);
        const re = await addUtable({
          ids: ids,
          projectId: this.detailData.projectId
        });
        if (re.code === 200) {
          this.$message({ type: "success", message: re.msg });
          this.close();
        }
      } else {
        this.$message({ type: "info", message: "请选择数据" });
      }
    },
    async _submits() {
      if (this.tableSelection && this.tableSelection.length > 0) {
        const ids = this.tableSelection.map(item => item.id);
        const re = await addUtable({
          ids: ids,
          projectId: this.detailData.projectId
        });
        if (re.code === 200) {
          this.$message({ type: "success", message: re.msg });
          this.close();
        }
      } else {
        this.$message({ type: "info", message: "请选择数据" });
      }
    },
    async getData() {
      const re = await getUtable(this.queryParams);
      this.total = re.total;
      this.tableData = re.rows;
    }
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
