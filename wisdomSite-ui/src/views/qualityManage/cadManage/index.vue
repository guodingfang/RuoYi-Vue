<template>
  <div class="cad-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <div class="search">
          <el-select
            v-model="projectId"
            placeholder="项目名称搜索"
            filterable
            clearable
            size="small"
            @change="changeList"
          >
            <el-option
              v-for="item in projectList"
              :key="item.id"
              :label="item.projectName"
              :value="item.id"
            />
            <template slot="prefix">
              <i slot="prefix" class="el-input__icon el-icon-search"></i>
            </template>
            <!-- <i slot="suffix" class="el-input__icon el-icon-search"></i> -->
          </el-select>
          <vue-scroll ref="scroll">
            <el-tree
              class="proj-list"
              :data="projectList"
              ref="vuetree"
              :props="defaultProps"
              highlight-current
              node-key="id"
              default-expand-all
              @node-click="clickTree"
            />
          </vue-scroll>
        </div>
      </el-col>
      <el-col :span="18" :xs="24" class="">
        <h3 class="table-tit">施工平面图（{{ total }}）</h3>
        <div class="warp">
          <el-button
            type="primary"
            style="margin-bottom: 12px;"
            @click="addFormLog()"
            >新增区域</el-button
          >
          <el-table
            default-expand-all
            row-key="id"
            v-loading="loading"
            :data="tableData"
          >
            <el-table-column
              label="区域名称"
              align="left"
              prop="regionName"
              :show-overflow-tooltip="true"
            />

            <el-table-column
              align="left"
              width="180"
              class-name="small-padding fixed-width"
            >
              <template slot="header">
                <div style="text-align: left;">操作</div>
              </template>
              <!-- v-if="scope.row.parentId == '0'" -->
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  v-if="scope.row.children"
                  @click="addFormLog(scope.row)"
                  v-hasPermi="['system:user:resetPwd']"
                  >新增下级区域</el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  @click="modifyForm(scope.row)"
                  v-hasPermi="['system:user:edit']"
                  >编辑</el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  @click="deleteData(scope.row)"
                  v-hasPermi="['system:user:edit']"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
    <addForm
      :state="state"
      :detailData="detailData"
      :visible="visible"
      @on-close="closeForm"
    />
  </div>
</template>

<script>
import {
  listProject,
  getTableFn,
  getDetailFn,
  deleteFn,
  getTableList
} from "@/api/quality/cadManage";
import addForm from "./modules/add-form";
export default {
  name: "CadManage",
  data() {
    return {
      projectId: null,
      projectList: [],
      pojList: [],
      loading: false,
      tableData: [],
      defaultProps: {
        children: "children",
        label: "projectName"
      },
      visible: false,
      state: null,
      detailData: {},
      total: 0
    };
  },
  components: {
    addForm
  },
  async created() {
    await this.getSelect();
    await this.getTable();
    this.changeList();
  },
  mounted() {},
  computed: {
    param() {
      return {
        projectId: this.projectId,
        detailData: {}
      };
    }
  },
  methods: {
    async showForm(flag) {
      this.visible = flag;
    },
    async closeForm(flag) {
      console.log(flag);
      if (flag) {
        this.getTable();
      }
      this.showForm(false);
    },
    async addFormLog(item) {
      this.state = "add";
      if (item) {
        const re = await getDetailFn(item.id);
        this.detailData = {
          projectId: this.projectId,
          parentId: re.data[0].id,
          planImageAddres: null
        };
      } else {
        this.detailData = {
          projectId: this.projectId,
          parentId: 0,
          planImageAddres: null
        };
      }

      this.showForm(true);
    },
    async modifyForm(item) {
      const re = await getDetailFn(item.id);
      this.detailData = re.data[0];
      this.state = "modify";
      this.showForm(true);
    },
    async deleteData(item) {
      return new Promise(res => {
        this.$confirm("是否确认删除?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(async () => {
            const re = await deleteFn(item.id);
            if (re.code === 200) {
              this.$message({
                message: re.msg,
                type: "success",
                duration: 1000
              });
              this.getTable();
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
    async getSelect() {
      const re = await listProject();
      this.projectList = re.rows;
      this.projectId = this.projectList[0].id;
    },
    async changeList() {
      this.$nextTick(() => {
        this.$refs["vuetree"].setCurrentKey(this.projectId);
      });
    },
    async getTable() {
      const re = await getTableFn(this.param);
      this.tableData = re.data;
      this.total = re.total;
      // const re = await getDetailFn(id);
    },
    clickTree(data) {
      this.projectId = data.id;
      this.getTable();
    }
  }
};
</script>

<style scoped lang="scss">
::v-deep {
  .el-col {
    padding: 0 !important;
    .warp {
      margin: 16px 20px 0 20px;
    }
  }
}
.cad-container {
  margin-right: 20px;
  .search {
    padding: 10px 20px;
    border-right: 1px solid #dddcdc;
  }
  .table-tit {
    font-size: 16px;
    font-weight: normal;
    padding: 16px 20px;
    margin: 0;
    border-bottom: 1px solid #dddcdc;
  }
  .proj-list {
    margin: 10px 0;
    ::v-deep {
      .el-tree-node:focus > .el-tree-node__content {
        background-color: #ecf2f2;
      }
    }
  }
}
</style>
