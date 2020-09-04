<template>
  <div class="cad-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <div class="search">
          <div class="s_title">
            <el-button icon="el-icon-plus" size='mini' class="st_button" @click='addFormLog'>角色</el-button>
            <el-select
              v-model="roleId"
              placeholder="姓名搜索"
              filterable
              clearable
              size='mini'
              @change="changeList"
            >
              <el-option
                v-for="item in projectList"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId"
              />
              <template slot="prefix">
                <i slot="prefix" class="el-input__icon el-icon-search"></i>
              </template>
              <!-- <i slot="suffix" class="el-input__icon el-icon-search"></i> -->
            </el-select>
          </div>

          <vue-scroll ref="scroll">
            <el-tree
              class="proj-list"
              :data="projectList"
              ref="vuetree"
              :props="defaultProps"
              highlight-current
              node-key="roleId"
              default-expand-all
              @node-click="clickTree"
            />
          </vue-scroll>
        </div>
      </el-col>
      <el-col :span="18" :xs="24" class="">
        <h3 class="table-tit">角色名称</h3>
        <section class="role_warp">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
            <el-form-item label="角色名称" prop="roleName">
              <el-input v-model="ruleForm.roleName"></el-input>
            </el-form-item>
            <el-form-item label="角色顺序" prop="roleSort">
              <el-input-number v-model="ruleForm.roleSort" controls-position="right" :min="0" />
            </el-form-item>
            <el-form-item label="状态">
              <el-radio-group v-model="ruleForm.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="ruleForm.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="菜单权限">
              <vue-scroll ref="scroll">
                <el-tree
                  class="proj-list"
                  :data="treeData"
                  ref="tree"
                  :props="propsRole"
                  highlight-current
                  node-key="id"
                  show-checkbox
                />
              </vue-scroll>
            </el-form-item>
            <section class="form-btn">
              <el-button type="primary" @click="modifyRoleUser">保存</el-button>
            </section>
          </el-form>

        </section>
      </el-col>
    </el-row>
    <addForm
      :state="state"
      :detailData="detailData"
      :roleIdList="roleIds"
      :visible="visible"
      @on-close="closeForm"
    />
  </div>
</template>

<script>
import {
  deleteFn,
} from "@/api/quality/cadManage";
import { getRtable, detailRole, detailTree, getRoleList, treeSelect, modifyRoleUser } from "@/api/system/enterpriseUser";
import addForm from "./modules/add-form";
export default {
  name: "enterpriseUser",
  data() {
    return {
      roleId: null,
      projectList: [],
      pojList: [],
      loading: false,
      tableData: [],
      defaultProps: {
        children: "children",
        label: "roleName"
      },
      propsRole: {
        children: "children",
        label: "label"
      },
      visible: false,
      state: null,
      detailData: {},
      total: 0,
      roleIds: [],
      treeData: [],
      ruleForm: {},
      rules: {
        roleName: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "角色顺序不能为空", trigger: "blur" }
        ]
      },
      // 状态数据字典
      statusOptions: [],
    };
  },
  components: {
    addForm
  },
  async created() {
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    await this.getSelect();
    this.changeList();
    this.detailRole();
    // this.treeSelect();
    this.detailTree()
  },
  mounted() {},
  computed: {
    param() {
      return {
        roleId: this.roleId,
        detailData: {}
      };
    }
  },
  methods: {
    async showForm(flag) {
      this.visible = flag;
    },
    async closeForm(flag) {
      if (flag) {
        this.getSelect()
        this.changeList();
        this.detailRole();
        this.detailTree()
      }
      this.showForm(false);
    },
    async addFormLog(item) {
      this.showForm(true);

      this.showForm(true);
    },
    async modifyForm(item) {
      // const re = await getDetailFn(item.id);
      // this.detailData = re.data[0];
      // await this.detailUser();
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
              // this.getTable();
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
      const re = await getRtable();
      this.projectList = re.rows;
      this.roleId = this.projectList[0].roleId;
      console.log(123,this.roleId);
    },
    async detailRole() {
      const re = await detailRole(this.roleId)
      this.ruleForm = re.data
    },
    async treeSelect() {
      const re = await treeSelect()
      this.treeData = re.data
      console.log(1,re);
    },
    async detailTree() {
      const re = await detailTree(this.roleId)
      this.treeData = re.menus
      this.$refs.tree.setCheckedKeys(re.checkedKeys);
    },
    async changeList() {
      this.$nextTick(() => {
        this.$refs["vuetree"].setCurrentKey(this.roleId);
      });
    },
    async modifyRoleUser() {
      let checkedKeys = this.$refs.tree.getHalfCheckedKeys()
      let halfCheckedKeys = this.$refs.tree.getCheckedKeys()
      let menuIds = checkedKeys.concat(halfCheckedKeys);
      const re = await modifyRoleUser({
        ...this.ruleForm,
        menuIds:menuIds
      })
      if (re.code == 200) {
        this.$message({
          message: re.msg,type: "success",
        });
      }
    },

    clickTree(data) {
      this.roleId = data.roleId;
      this.detailRole()
      this.detailTree()
    }
  }
};
</script>

<style scoped lang="scss">
::v-deep {
  .el-col {
    padding: 0 !important;
    .warp {
      margin: 16px 20px 0 30px;
    }
  }
}
.role_warp{
  padding-top: 20px;
  border-left: 1px solid #dddcdc;;
}
.cad-container {
  margin-right: 20px;
  .search {
    padding: 10px 20px;
    // border-right: 1px solid #dddcdc;
  }
  .s_title{
    @include flex(flex-start, center);
    .st_button{
      margin-right: 10px;
    }
  }
  .table-tit {
    font-size: 16px;
    font-weight: normal;
    padding: 16px 30px;
    margin: 0;
    border-bottom: 1px solid #dddcdc;
    border-left: 1px solid #dddcdc;;
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
.form-btn{
  // text-align: center;
  margin-left: 120px;
}
</style>
