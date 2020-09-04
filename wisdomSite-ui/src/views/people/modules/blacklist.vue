<template>
  <div class="r-warp">
    <div class="r-query">
      <el-input
        placeholder="输入姓名或身份证号码搜索"
        v-model="queryParams.keyWordName"
        style="width: 217px;"
      >
        <i slot="suffix" @click="getData()" class="el-input__icon el-icon-search"></i>
      </el-input>
      <el-select
        clearable
        filterable
        @change="getTeam"
        v-model="unitId"
        placeholder="全部参建单位"
      >
        <el-option
          v-for="item in $DStore.state.getUnit"
          :key="item.id"
          :label="item.unitName"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <el-select
        clearable
        filterable
        @change="getData()"
        v-model="teamId"
        placeholder="全部班组/部门"
      >
        <el-option
          v-for="item in teamList"
          :key="item.id"
          :label="item.teamName"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <el-button type="primary" @click="addForm">新增</el-button>
      <el-button type="primary" @click="deleteBlackStaff()">批量移除</el-button>
    </div>
    <div class="table-warp">
      <el-table
        default-expand-all
        row-key="id"
        v-loading="loading"
        :data="tableData"
        @selection-change="handleSelection"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="姓名"
          align="left"
          prop="name"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="参建单位"
          align="left"
          prop="participatingUnitName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="班组/部门"
          align="left"
          prop="teamName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="工种/岗位"
          align="left"
          prop="workTypeName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="身份证号"
          align="left"
          prop="idNumber"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="违规原因"
          align="left"
          prop="illegalReason"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="违规日期"
          align="left"
          prop="illegalDate"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="现场图片"
          align="left"
          width="180"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-image
              v-for="item in scope.row.fileInfo"
              :key='item.id'
              class="elImage"
              fit="cover"
              :src="getFilePath() + item.url"
              :preview-src-list="[getFilePath() + item.url]"
            >
              <div slot="error" class="image-error-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </template>
        </el-table-column>
        <el-table-column
          align="left"
          width="180"
          class-name="small-padding fixed-width"
        >
          <!-- v-if="scope.row.parentId == '0'" -->
          <template slot-scope="scope">
            <!-- scope.row -->
            <!-- <el-button size="mini" type="text" @click="detailBlack(scope.row.id)">详情</el-button> -->
            <el-button
              size="mini"
              type="text"
              @click="modifyBlack(scope.row.id)"
              >编辑</el-button
            >
            <el-button
              size="mini"
              type="text"
              @click="deleteBlackStaff(scope.row)"
              >移除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="table_footer">
        <el-button type="text" @click="handleExport">导出数据</el-button>
      </div>
      <Pagination
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getData"
      />
      <component
        :is="model"
        v-if="popShow"
        :state="state"
        :detailData="detailData"
        :is-show="popShow"
        @on-close="closePop"
      />
    </div>
  </div>
</template>
<script>
import { getTeam } from "@/api/people/company";
import {
  getBtable,
  deleteBlackStaff,
  exportJob,
  detailBlack
} from "@/api/people/blacklist";
import addBlack from "./add-black";
export default {
  name: "exit",
  data() {
    return {
      loading: false,
      tableData: [],
      popShow: false,
      model: null,
      state: null,
      total: 0,
      title: null,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        blacklist: 1
      },
      unitId: "",
      teamId: "",
      teamList: [],
      tableSelection: [],
      detailData: {}
    };
  },
  computed: {
    param() {
      return {
        ...this.queryParams,
        unitId: this.unitId,
        teamId: this.teamId
      };
    }
  },
  components: {
    addBlack
  },
  async created() {
    this.$DStore.refresh(['getUnit'], true);
    this.getData();
  },
  methods: {
    getFilePath() {
      return window.location.host
    },
    showPop(flag) {
      this.popShow = flag;
    },
    closePop(resful) {
      this.showPop(false);
      if (resful == "setStaff") {
        this.getData(this.paramInit);
      }
      this.model = null;
    },
    addForm() {
      this.model = "addBlack";
      this.state = "addBlack";
      this.detailData = {};
      this.showPop(true);
    },
    async detailBlack(id) {
      const re = await detailBlack(id);
      console.log(re);
    },
    async modifyBlack(id) {
      const re = await detailBlack(id);
      this.model = "addBlack";
      this.state = "modifyBlack";
      this.detailData = re.data;
      this.showPop(true);
    },
    async getData() {
      const re = await getBtable(this.param);
      this.tableData = re.rows;
      this.total = re.total;
    },
    async getTeam() {
      this.teamId = null;
      const re = await getTeam({ unitId: this.unitId });
      this.teamList = re.rows;
      this.getData();
    },
    handleSelection(val) {
      this.tableSelection = val;
    },
    async deleteBlackStaff(row) {
      if (row) {
        this.deleteFn(row.personId);
      } else {
        if (this.tableSelection && this.tableSelection.length > 0) {
          let ids = this.tableSelection.map(item => item.personId).join(",");
          this.deleteFn(ids);
        } else {
          this.$message({
            message: "请先选择人员",
            type: "info"
          });
        }
      }
    },

    deleteFn(ids) {
      return new Promise(res => {
        this.$confirm("是否确认删除?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(async () => {
            const re = await deleteBlackStaff(ids);
            if (re.code === 200) {
              this.$message({
                message: re.msg,
                type: "success"
              });
              this.getData();
            }
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除"
            });
          });
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.param;
      this.$confirm("是否确认导出数据！", "导出", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportJob(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    }
  }
};
</script>
<style lang="scss" scoped>
.elImage{
  width: 80px;
  height: 80px;
  .el-icon-picture-outline{
    font-size: 80px;
  }
}
::v-deep {
  .el-table {
    border-radius: 6px 6px 0 0;
  }
}
.r-query {
  position: absolute;
  top: -62px;
  right: 0;
}
.table-warp {
  margin: 20px 10px 0;
}
.table_footer {
  padding-right: 20px;
  text-align: right;
  line-height: 45px;
  border: 1px solid #e9e9e9;
  border-top: none;
  border-radius: 0 0 6px 6px;
}
</style>
