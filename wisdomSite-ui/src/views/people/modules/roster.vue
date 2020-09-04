<template>
  <section>
    <el-row>
      <el-col :span="7" :xs="24">
        <div class="tree_warp">
          <!-- <vue-scroll ref="scroll"> -->
          <h3 class="table-tit">班组</h3>
          <el-tree
            class="proj-list"
            :data="treeList"
            ref="vuetree"
            :props="defaultProps"
            highlight-current
            node-key="id"
            @node-click="clickTree"
            :expand-on-click-node="false"
          >
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <span>{{ node.label }}</span>
              <span class="tree_right">
                <el-button
                  type="text"
                  size="mini"
                  v-show="data.nodeType == 1"
                  @click="() => addForm(node, data)"
                >
                  新增
                </el-button>
                <el-button
                  type="text"
                  size="mini"
                  v-show="data.nodeType == 3"
                  @click="() => modifyTeam(node, data)"
                >
                  设置班组
                </el-button>
                <el-button
                  type="text"
                  size="mini"
                  v-show="data.nodeType == 3"
                  @click="() => modifyForm(node, data)"
                >
                  编辑
                </el-button>
                <el-button
                  type="text"
                  size="mini"
                  v-show="data.nodeType == 3"
                  @click="() => deleteForm(node, data)"
                >
                  删除
                </el-button>
              </span>
            </span>
          </el-tree>
          <!-- </vue-scroll> -->
        </div>
      </el-col>
      <el-col :span="17" :xs="24" class="">
        <div class="r-warp">
          <div class="query-box">
            {{ title }}（{{ total }}）
            <div class="r-query">
              <el-input
                placeholder="输入姓名或身份证号码搜索"
                v-model="queryParams.keyWordName"
                style="width: 217px;"
              >
                <i
                  slot="suffix"
                  @click="getData()"
                  class="el-input__icon el-icon-search"
                ></i>
              </el-input>
              <el-select
                clearable
                filterable
                @change="getData()"
                v-model="workType"
                placeholder="全部工种"
              >
                <el-option
                  v-for="item in $DStore.state.getWorkType"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                >
                </el-option>
              </el-select>
              <el-select
                clearable
                filterable
                @change="getData()"
                v-model="personType"
                placeholder="全部职务/权限"
              >
                <el-option label="建筑工人" value="0"></el-option>
                <el-option label="管理人员" value="1"></el-option>
              </el-select>
              <el-button type="primary" @click="addLeading">
                批量导入
              </el-button>
              <el-button type="primary" @click="addStaff()">
                新增人员
              </el-button>
            </div>
          </div>
          <div class="table-warp">
            <div class="table-button">
              <el-button @click="setStaff()">设置人员信息</el-button>
              <el-button @click="personStatus()">批量退场</el-button>
              <el-button @click="deleteStaff()">删除</el-button>
              <el-button @click="addBlack()">加入黑名单</el-button>
            </div>
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
                label="职务/权限"
                align="left"
                prop="personType"
                :show-overflow-tooltip="true"
              >
                <template slot-scope="scope">
                  <span v-if="scope.row.personType == 0">建筑工人</span>
                  <span v-else>管理人员</span>
                </template>
              </el-table-column>
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
                label="手机号码"
                align="left"
                prop="mobilePhone"
                :show-overflow-tooltip="true"
              />
              <el-table-column
                align="left"
                width="180"
                class-name="small-padding fixed-width"
              >
                <!-- v-if="scope.row.parentId == '0'" -->
                <template slot-scope="scope">
                  <el-button size="mini" type="text" v-if="scope.row.children"
                    >新增下级区域</el-button
                  >
                  <el-button
                    size="mini"
                    type="text"
                    @click="detailPren(scope.row.id)"
                    >详情</el-button
                  >
                  <el-button
                    size="mini"
                    type="text"
                    @click="modifyStaff(scope.row.id)"
                    >编辑</el-button
                  >
                  <el-button
                    size="mini"
                    type="text"
                    @click="personStatus(scope.row.id)"
                    >退场</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
            <Pagination
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="changPage"
            />
          </div>
        </div>
      </el-col>
    </el-row>
    <!-- <transition name="fade-transform" mode="out-in"> -->
    <component
      :is="model"
      v-if="popShow"
      :state="state"
      :detailData="detailData"
      :is-show="popShow"
      @on-close="closePop"
      @on-utable="openTable"
      @on-stable="openstaffTable"
    />
    <!-- </transition> -->
  </section>
</template>
<script>
import {
  getTree,
  detailProjectUnit,
  getPtable,
  deleteProjectUnit,
  getTeam,
  detailStaff,
  setAllData,
  deleteStaff
} from "@/api/people/company";
import XdhDictTable from "@/components/xdh-dict-table";
import addRoster from "./add-roster";
import addStaff from "./add-staff";
import detailPren from "./detail-staff";
import addTeam from "./add-team";
import leading from "./leading";
import setStaff from "./set-staff";
import rosterTable from "./roster-table";
import staffTable from "./staff-table";
import addBlack from "./add-black";
export default {
  name: "roster",
  components: {
    XdhDictTable,
    addRoster,
    addStaff,
    addTeam,
    leading,
    rosterTable,
    setStaff,
    detailPren,
    staffTable,
    addBlack
  },
  data() {
    return {
      treeList: [],
      defaultProps: {
        children: "children",
        label: "label"
      },
      loading: false,
      tableData: [],
      popShow: false,
      model: null,
      state: null,
      total: 0,
      title: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        personStatus: 1
      },
      treeId: "",
      unitId: "",
      workType: "",
      personType: "",
      detailData: {},
      tableSelection: [],
      paramInit: {}
    };
  },
  computed: {
    param() {
      return {
        ...this.queryParams
      };
    }
  },
  async created() {
    const re = await getTree();
    this.initTree();
    // console.log(re);
  },
  methods: {
    async initTree() {
      const re = await getTree();
      this.treeList = re.data;
      this.treeId = this.treeList[0].id;
      this.projectId = this.treeList[0].nodeId;
      this.title = this.treeList[0].label;
      this.changeTree();
      this.paramInit = Object.assign({
        ...this.queryParams,
        projectId: this.treeList[0].nodeId
      });
      this.getData(this.paramInit);
    },
    clickTree(data) {
      this.queryParams.keyWordName = null
      this.treeId = data.id;
      this.projectId = data.projectId;
      this.title = data.label;
      this.unitId = null;
      const param = {
        ...this.queryParams
      };
      if (data.nodeType == 1) {
        let obj = Object.assign(param, { projectId: data.nodeId });
        this.paramInit = Object.assign(param, { projectId: data.nodeId });
        this.getData(obj);
      } else if (data.nodeType == 2) {
        let obj = Object.assign(param, {
          projectId: data.projectId,
          unitTypeId: data.nodeId
        });
        this.paramInit = obj;
        this.getData(obj);
      } else if (data.nodeType == 3) {
        let obj = Object.assign(param, { unitId: data.nodeId });
        this.paramInit = Object.assign(param, { unitId: data.nodeId });
        this.unitId = data.nodeId;
        this.getData(obj);
      } else if (data.nodeType == 4) {
        let obj = Object.assign(param, { teamId: data.nodeId });
        this.paramInit = Object.assign(param, { teamId: data.nodeId });
        this.getData(obj);
      }
    },
    changeTree() {
      this.$nextTick(() => {
        this.$refs["vuetree"].setCurrentKey(this.treeId);
      });
    },
    showPop(flag) {
      this.popShow = flag;
    },
    addForm(node, data) {
      this.model = "addRoster";
      this.state = "addRoster";
      this.detailData = { projectId: data.nodeId };
      this.showPop(true);
    },
    async modifyForm(node, data) {
      this.model = "addRoster";
      this.state = "modifyRoster";
      const re = await detailProjectUnit(data.nodeId);
      this.detailData = re.data;
      this.showPop(true);
    },
    async deleteForm(node, data) {
      return new Promise(res => {
        this.$confirm("是否确认删除?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(async () => {
            const re = await deleteProjectUnit(data.nodeId);
            if (re.code === 200) {
              this.$message({
                message: re.msg,
                type: "success"
              });
              this.initTree();
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
    //设置班组
    async modifyTeam(node, data) {
      this.model = "addTeam";
      this.state = null;
      const re = await getTeam({ unitId: data.nodeId });
      this.detailData = re.rows;
      this.showPop(true);
    },
    //参建单位库
    openTable() {
      this.model = "rosterTable";
      this.detailData = { projectId: this.projectId };
      this.showPop(true);
    },
    closePop(resful) {
      this.showPop(false);
      if (resful == "setStaff") {
        this.getData(this.paramInit);
      }
      this.model = null;
    },

    // 批量导入
    addLeading() {
      if (this.unitId) {
        this.model = "leading";
        this.showPop(true);
        this.detailData = this.paramInit;
      } else {
        this.$message({
          showClose: true,
          message: "请先选择参建单位"
        });
      }
    },

    //加入黑名单
    addBlack() {
      this.model = "addBlack";
      if (this.tableSelection && this.tableSelection.length > 0) {
        this.state = "addBlack";
        this.detailData = {
          id: this.tableSelection.map(item => item.id),
          name: this.tableSelection.map(item => item.name)
        };
      } else {
        this.state = "addBlack";
        this.detailData = {};
      }
      this.showPop(true);
    },
    // 设置人员
    setStaff() {
      this.model = "setStaff";
      if (this.tableSelection && this.tableSelection.length > 0) {
        this.detailData = {
          personIds: this.tableSelection.map(item => item.id),
          name: this.tableSelection.map(item => item.name).join(",")
        };
        this.showPop(true);
      } else {
        this.$message({
          message: "请先选择人员",
          type: "info"
        });
      }
    },
    async deleteStaff() {
      if (this.tableSelection && this.tableSelection.length > 0) {
        let personIds = this.tableSelection.map(item => item.id).join(",");
        return new Promise(res => {
          this.$confirm("是否确认删除?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(async () => {
              const re = await deleteStaff(personIds);
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
      } else {
        this.$message({
          message: "请先选择人员",
          type: "info"
        });
      }
    },
    // 批量退场 用的setAllData接口
    async personStatus(id) {
      if (id) {
        const data = {
          personIds: [id],
          personStatus: 0
        };
        return new Promise(res => {
          this.$confirm("是否确认退场?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(async () => {
              const re = await setAllData(data);
              if (re.code == 200) {
                this.$message({
                  message: re.msg,
                  type: "success"
                });
                this.getData(this.paramInit);
              }
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消退场"
              });
            });
        });
      } else {
        if (this.tableSelection && this.tableSelection.length > 0) {
          const data = {
            personIds: this.tableSelection.map(item => item.id),
            personStatus: 0
          };
          return new Promise(res => {
            this.$confirm("是否确认退场?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            })
              .then(async () => {
                const re = await setAllData(data);
                if (re.code == 200) {
                  this.$message({
                    message: re.msg,
                    type: "success"
                  });
                  this.getData(this.paramInit);
                }
              })
              .catch(() => {
                this.$message({
                  type: "info",
                  message: "已取消退场"
                });
              });
          });
        } else {
          this.$message({
            message: "请先选择人员",
            type: "info"
          });
        }
      }
    },
    // 新增人员
    addStaff() {
      this.model = "addStaff";
      this.state = "addStaff";
      this.detailData = { projectId: this.projectId };
      this.showPop(true);
    },
    // 编辑人员
    async modifyStaff(id) {
      this.model = "addStaff";
      this.state = "modifyStaff";
      const re = await detailStaff(id);
      this.detailData = re.data;
      this.showPop(true);
    },
    //人员详情
    async detailPren(id) {
      this.model = "detailPren";
      this.state = "modifyStaff";
      const re = await detailStaff(id);
      this.detailData = re.data;
      this.showPop(true);
    },
    //人员库
    openstaffTable() {
      this.model = "staffTable";
      this.detailData = { projectId: this.projectId };
      this.showPop(true);
    },
    handleSelection(val) {
      this.tableSelection = val;
    },
    changPage(val) {
      this.paramInit.pageNum = val.page;
      this.getData();
    },
    async getData() {
      let param = {
        ...this.paramInit,
        workType: this.workType,
        personType: this.personType,
        keyWordName: this.queryParams.keyWordName,
        projectId: this.projectId
      };
      const re = await getPtable(param);
      this.tableData = re.rows;
      this.total = re.total;
    }
  }
};
</script>
<style lang="scss" scoped>
.query-box {
  height: 60px;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
  padding-left: 20px;
  border-bottom: 1px solid #dddcdc;
}
.tree_warp {
  border-right: 1px solid #dddcdc;
  min-height: 560px;
}
.table-tit {
  font-size: 16px;
  font-weight: normal;
  padding: 16px 20px;
  margin: 0;
  border-bottom: 1px solid #dddcdc;
}
.r-warp {
  // border-left: 1px solid #dddcdc;
}
.table-warp {
  padding: 0 20px 0 20px;
  .table-button {
    margin: 10px 0;
  }
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
